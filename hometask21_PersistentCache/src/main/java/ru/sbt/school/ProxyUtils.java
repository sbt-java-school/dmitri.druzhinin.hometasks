package ru.sbt.school;

import org.slf4j.Logger;
import ru.sbt.school.cache.Cache;
import ru.sbt.school.dao.InvokeDao;
import ru.sbt.school.entities.Invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class ProxyUtils {
    private static Logger logger = getLogger(ProxyUtils.class);

    /**
     * @param object Замещаемый объект.
     * @param <T>    Тип замещаемого объекта.
     * @return Объект-прокси.
     */
    public static <T> T makeCached(T object) {
        Object proxy = Proxy.newProxyInstance(ProxyUtils.class.getClassLoader(), object.getClass().getInterfaces(),
                new InvocationHandler() {
                    private InvokeDao invokeDao = new InvokeDao();
                    private T delegate = object;

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result;
                        Cache cacheAnnotation = method.getAnnotation(Cache.class);
                        if (cacheAnnotation == null) {
                            cacheAnnotation = delegate.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).getAnnotation(Cache.class);
                        }
                        if (cacheAnnotation == null) {
                            logger.info("Non cacheable method " + method.getName());
                            result = method.invoke(delegate, args);
                        } else {
                            logger.info("Cacheable method " + method.getName());
                            Optional<Invoke> optional = invokeDao.findByDelegateAndMethodWithArgs(delegate, method, args);
                            if (optional.isPresent()) {
                                logger.info("Result from cache " + method.getName());
                                result = optional.get().getResult();
                            } else {
                                logger.info("Real invoke " + method.getName());
                                result = method.invoke(delegate, args);
                                invokeDao.create(new Invoke(delegate, method, args, result));
                                return result;
                            }
                        }
                        return result;
                    }
                });
        return (T) proxy;
    }
}
