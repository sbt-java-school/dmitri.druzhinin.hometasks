package ru.sbt.school.chat.server;

import ru.sbt.school.chat.Commands;
import ru.sbt.school.chat.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Программа-сервер для чата.
 */
public class Server {
    private static final ConcurrentHashMap<String, Queue<Message>> messagesByLogin = new ConcurrentHashMap<>();
    private static final int maxClients = 3;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(maxClients);
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            if (messagesByLogin.size() < maxClients) {
                executorService.submit(new ClientHandler(socket));
            } else {
                busy(socket);
            }
        }
    }

    /**
     * Если клиентов слишком много, им отказывается в участии.
     */
    private static void busy(Socket socket) {
        try (Socket clientSocket = socket;
             OutputStream outputStream = clientSocket.getOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {

            out.writeObject(Commands.BUSY);
        } catch (IOException e) {
        }
    }

    /**
     * Nested-класс для обработки одного клиента.
     */
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (Socket socket = clientSocket;
                 InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(outputStream);
                 ObjectInputStream in = new ObjectInputStream(inputStream)) {

                out.writeObject(Commands.LOGIN);//запрос сервером логина.
                out.flush();
                String login = in.readUTF();
                if (login.isEmpty() || login == null) {
                    out.writeObject(Commands.ERROR);
                    out.flush();
                    return;
                }
                messagesByLogin.putIfAbsent(login, new LinkedList<>());
                out.writeObject(Commands.OK);
                out.flush();
                notifyAllAboutNewClient(login);
                while (true) {
                    Commands command = (Commands) in.readObject();
                    if (command == Commands.SEND_MESSAGE) {
                        Message newMessage = (Message) in.readObject();
                        String loginTo = newMessage.getLoginTo();
                        try {
                            messagesByLogin.compute(loginTo, (key, queue) -> {
                                queue.add(newMessage);
                                return queue;
                            });
                            out.writeObject(Commands.OK);
                        } catch (NullPointerException e) {
                            out.writeObject(Commands.ERROR);
                            out.writeUTF("Пользователь с логином " + loginTo + " не зарегистрирован!");
                        }
                        out.flush();
                    } else if (command == Commands.GET_ALL) {
                        Queue<Message> messages = messagesByLogin.replace(login, new LinkedList<>());
                        out.writeObject(messages);
                        out.flush();
                    } else if (command == Commands.LOGOUT) {
                        messagesByLogin.remove(login);//историю чатов хранить не нужно.
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
            }
        }

        private void notifyAllAboutNewClient(String login) {
            messagesByLogin.forEach((key, value) -> value.add(new Message("System", key, "Добавлен новый участник " + login)));
        }
    }
}