package ru.sbt.school.dao;

import org.slf4j.Logger;
import ru.sbt.school.entities.Invoke;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class InvokeDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private static Logger logger = getLogger(InvokeDao.class);

    /**
     * @param delegate Замещаемый объект, метод которого реально вызывается, если результата нет в базе.
     * @param method   Метод, который был вызван.
     * @param args     Аргументы метода.
     */
    public Optional<Invoke> findByDelegateAndMethodWithArgs(Object delegate, Method method, Object[] args) {
        return jdbcTemplate.execute(connection -> {
            logger.info("invokeDao.findByDelegateWithMethodAndArgs()");
            try (PreparedStatement statement = connection.prepareStatement("select delegate, method, args, result from cache.invokes where delegate=? and method=? and args=?")) {
                statement.setBlob(1, objectToInputStream(delegate));
                statement.setString(2, method.getName());
                statement.setBlob(3, objectToInputStream(args));
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Invoke(delegate, method, args, inputStreamToObject(resultSet.getBinaryStream(4))));
            }
        });
    }

    /**
     * @param invoke Объект, который будет отображен в базу данных.
     * @return {@code true} если в результате вызова данного метода база данных была изменена, иначе {@code false}.
     */
    public boolean create(Invoke invoke) {
        return jdbcTemplate.execute(connection -> {
            logger.info("invokeDao.create()");
            try (PreparedStatement statement = connection.prepareStatement("insert into cache.invokes (delegate, method, args, result) values(?, ?, ?, ?)")) {
                statement.setBlob(1, objectToInputStream(invoke.getDelegate()));

                statement.setString(2, invoke.getMethod().getName());
                statement.setBlob(3, objectToInputStream(invoke.getArgs()));
                statement.setBlob(4, objectToInputStream(invoke.getResult()));
                return statement.executeUpdate() == 1;
            }
        });
    }

    private InputStream objectToInputStream(Object object) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out=new ObjectOutputStream(byteArrayOutputStream) ) {
            out.writeObject(object);
            out.flush();
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Serialization error" + e);
        }
    }

    private Object inputStreamToObject(InputStream inputStream) {
        try(ObjectInputStream in=new ObjectInputStream(inputStream)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Serialization error" + e);
        }
    }
}
