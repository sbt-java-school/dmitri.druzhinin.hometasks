package ru.sbt.school.barbershop.one_barber;

public class Barber implements Runnable {
    private final long timeOfCut;
    private final long id;
    private Client client;

    public Barber(long timeOfCut, long id) {
        this.timeOfCut = timeOfCut;
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void cutHair() throws InterruptedException {
        System.out.println("Barber" + id + " cut " + client.getName());
        Thread.sleep(timeOfCut + client.getTimeOfCut());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                BarberShop.getInstance().processBarber();
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Конец рабочего дня!)))");
    }
}
