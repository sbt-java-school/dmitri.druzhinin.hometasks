package ru.sbt.school.chat.client;

import ru.sbt.school.chat.Commands;
import ru.sbt.school.chat.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Программа-клиент для чата. Клиент двухпоточный. Поток Main ответственен за работу с пользователем (с консолью) и ничего
 * не знает о сервере. Он просто обрабатывает сообщения от пользователя и сохраняет их в очередь. Второй поток нужен для общения
 * с сервером. Он постоянно просит входящие сообщения от сервера, а если обнаруживает что на клиенте в очереди появились исходящие,
 * то отправляет их.
 */
public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private static final BlockingQueue<Message> messagesToServer = new LinkedBlockingQueue<>();
    private static volatile String login;
    private static AtomicBoolean exit = new AtomicBoolean();

    public static void main(String[] args) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(System.in);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            System.out.print("Login: ");
            login = reader.readLine();
            if (login.isEmpty() || login == null) {
                System.out.println("Некорректный login.");
                return;
            }

            start();

            System.out.println("Вы в чате. Для выхода введите logout");
            System.out.println("Для отправки сообщений применяйте шаблон кому>>message");
            System.out.println("\n");

            while (true) {
                String message = reader.readLine();
                if (message.toLowerCase().equals("logout")) {
                    exit.set(true);
                    break;
                }
                sendMessage(message);
            }
        }
    }

    /**
     * Разбирает сообщение, введенное пользователем и просто помещает его в поле очереди сообщений.
     *
     * @param message сообщение от пользователя.
     */
    private static void sendMessage(String message) {
        String[] parties = message.split(">>");
        if (parties.length < 2) {
            System.out.println("Применяйте шаблон кому>>message!");
        } else {
            String loginTo = parties[0];
            String data = "";
            for (int i = 1; i < parties.length; i++) {
                data += parties[i];
            }
            messagesToServer.add(new Message(login, loginTo, data));
        }
    }

    /**
     * Запуск потока, ответственного за работу с сервером.
     */
    private static void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(HOST, PORT);
                     InputStream inputStream = socket.getInputStream();
                     OutputStream outputStream = socket.getOutputStream();
                     ObjectInputStream in = new ObjectInputStream(inputStream);
                     ObjectOutputStream out = new ObjectOutputStream(outputStream)) {

                    Commands command = (Commands) in.readObject();
                    if (command == Commands.BUSY) {
                        System.out.println("Сервер перегружен. Попробуйте зайти позже.");
                        return;
                    }
                    if (command == Commands.LOGIN) {
                        out.writeUTF(login);
                        out.flush();
                    }
                    if (in.readObject() != Commands.OK) {
                        System.out.println("Проблемы на сервере. Попробуйте зайти позже.");
                        return;
                    }
                    while (true) {
                        Message message;
                        if (null == (message = messagesToServer.poll())) {
                            out.writeObject(Commands.GET_ALL);
                            out.flush();
                            Queue<Message> messagesFromServer = (Queue<Message>) in.readObject();
                            printMessages(messagesFromServer);
                        } else {
                            out.writeObject(Commands.SEND_MESSAGE);
                            out.writeObject(message);
                            out.flush();
                            if (in.readObject() == Commands.ERROR) {
                                System.out.println(in.readUTF());
                            }
                        }

                        if (exit.get() == true) {
                            out.writeObject(Commands.LOGOUT);
                            out.flush();
                            break;
                        }
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            private void printMessages(Queue<Message> messages) {
                messages.forEach((message) -> System.out.println(message.getLoginFrom() + ">>" + message.getData()));
            }
        }).start();
    }
}