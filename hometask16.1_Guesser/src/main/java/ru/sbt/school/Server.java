package ru.sbt.school;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Программа-сервер для игры "Гадалка".
 */
public class Server {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final int PORT = 1234;
    private static final int BOUNDARY_OF_NUMBERS = 5;
    private static final String helloString = "Я загадал число от 1 до 5, отгадай!";
    private static final String tryAgainString = "Не отгадал, попытайся еще!";
    private static final String winFormatString = "Отлично! Ты отгадал с %d попытки!";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> {
                    try (Socket socket = clientSocket;
                         InputStream in = clientSocket.getInputStream();
                         OutputStream out = clientSocket.getOutputStream();
                         DataInputStream dataInputStream = new DataInputStream(in);
                         DataOutputStream dataOutputStream = new DataOutputStream(out)) {

                        int secretNumber = new Random().nextInt(BOUNDARY_OF_NUMBERS) + 1;
                        int countOfAttempts = 0;
                        dataOutputStream.writeUTF(helloString);
                        dataOutputStream.flush();
                        while (true) {
                            countOfAttempts++;
                            int answerNumber = dataInputStream.readInt();
                            boolean winner = answerNumber == secretNumber;
                            dataOutputStream.writeBoolean(winner);
                            dataOutputStream.flush();
                            if (winner) {
                                break;
                            }
                            dataOutputStream.writeUTF(tryAgainString);
                            dataOutputStream.flush();
                        }
                        dataOutputStream.writeUTF(new Formatter().format(winFormatString, countOfAttempts).toString());
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
