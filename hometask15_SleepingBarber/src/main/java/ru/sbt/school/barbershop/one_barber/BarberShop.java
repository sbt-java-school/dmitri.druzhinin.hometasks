package ru.sbt.school.barbershop.one_barber;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class BarberShop {
    private final int chairNumber = 5;//число стульев в очереди клиентов.
    /**
     * Эти два семафора работают когда клиент будит парикмахера.
     */
    private final Semaphore clientSemaphore = new Semaphore(0);
    private final Semaphore barberClientSemaphore = new Semaphore(0);

    /**
     * Эти два семафора работают когда парикмахер обслуживает очередь, не засыпая.
     */
    private final Semaphore chairsSemaphore = new Semaphore(chairNumber);
    private final Semaphore barberChairsSemaphore = new Semaphore(0);

    private final BlockingQueue<Client> clients = new LinkedBlockingQueue<>();//очередь клиентов.
    private final Barber barber = new Barber(500, 1);
    private final Thread barberThread;
    private static BarberShop instance;

    private BarberShop() {
        barberThread = new Thread(barber, "Barber1 Thread");
        barberThread.start();
    }

    public static BarberShop getInstance() {
        if (instance == null) {
            synchronized (BarberShop.class) {
                if (instance == null) {
                    instance = new BarberShop();
                }
            }
        }
        return instance;
    }

    public void processBarber() throws InterruptedException {
        if (barberChairsSemaphore.tryAcquire()) {//барбер пытается взять клиента из приемной.
            Client client = clients.poll();//вытаскивает клиента из очереди.
            barber.setClient(client);//берет к себе.
            chairsSemaphore.release();//освобождает место в очереди
            barber.cutHair();//стрижет клиента.
        } else {//если в приемной клиентов нет.
            chairsSemaphore.release();//освобождает место в приемной(потому что первый пройдет через приемную-chairsSemaphore.tryAcquire()).
            clientSemaphore.release();//разрешает заходить к себе без очереди.
            barberClientSemaphore.acquire();//засыпает.
            barber.cutHair();//пока его не разбудит вновь пришедший клиент.
        }
    }

    /**
     * Вход для клиента.
     */
    public void processClient(Client client) {

        if (chairsSemaphore.tryAcquire()) {//клиент пытается войти в приемную.
            if (clientSemaphore.tryAcquire()) {//тут же пытается стать текущим клиентом у парикмахера.
                barber.setClient(client);
                barberClientSemaphore.release();//будит парикмахера.
            } else {//если сразу к парикмахеру попасть не удалось, то идет в очередь.
                clients.add(client);//после добавления в очередь специально засыпать нет смысла, т.к. поток завершается.
                barberChairsSemaphore.release();//при обращении к очереди парикмахер возьмет клиента.
            }
        } else {
            System.out.println(client.getName() + " Busy!)))");//если в приемной все занято, то клиент больше не клиент.
        }
    }

    /**
     * Закрытие салона.
     */
    public void closeShop() {
        barberThread.interrupt();
    }
}
