package ru.sbt.school;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Программа-клиент для игры "Гадалка".
 */
public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.submit(Client::play);
    }

    private static void play() {
        try (Socket socket = new Socket(HOST, PORT);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             DataInputStream dataInputStream = new DataInputStream(in);
             DataOutputStream dataOutputStream = new DataOutputStream(out)) {

            System.out.println(dataInputStream.readUTF());
            while (true) {
                int number = enterNumberFromUser();
                dataOutputStream.writeInt(number);
                boolean winner = dataInputStream.readBoolean();
                System.out.println(dataInputStream.readUTF());
                if (winner) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int enterNumberFromUser() throws IOException {
//        try(InputStreamReader reader=new InputStreamReader(System.in);
//            BufferedReader bufferedReader=new BufferedReader(reader)) {
//            String s=bufferedReader.readLine();
//            return Integer.parseInt(s);
//        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(reader.readLine());
    }
}
