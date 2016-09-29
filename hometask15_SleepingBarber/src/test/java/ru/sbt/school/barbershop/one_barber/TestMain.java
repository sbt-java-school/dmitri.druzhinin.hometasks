package ru.sbt.school.barbershop.one_barber;

import java.util.Random;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        Random random=new Random();
        for(int i=0; i<10; i++){
            Thread client=new Thread(new Client(100+random.nextInt(300), "Client"+i), "Thread"+i);
            client.start();
//            Thread.sleep(20);//для упорядочивания.
        }

        Thread.sleep(10000);
        System.out.println("Долгое отсутствие новых клиентов.");
        for(int i=10; i<20; i++){
            Thread client=new Thread(new Client(100+random.nextInt(300), "Client"+i), "Thread"+i);
            client.start();
//            Thread.sleep(20);
        }

        Thread.sleep(8000);
        System.out.println("Долгое отсутствие новых клиентов.");
        for(int i=20; i<30; i++){
            Thread client=new Thread(new Client(100+random.nextInt(300), "Client"+i), "Thread"+i);
            client.start();
//            Thread.sleep(20);
        }

        Thread.sleep(8000);
        System.out.println("Долгое отсутствие новых клиентов.");
        for(int i=30; i<38; i++){
            Thread client=new Thread(new Client(100+random.nextInt(300), "Client"+i), "Thread"+i);
            client.start();
//            Thread.sleep(20);
        }
    }
}
