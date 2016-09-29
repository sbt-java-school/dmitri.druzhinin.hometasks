package ru.sbt.school.barbershop.one_barber;

public class Client implements Runnable {
    private final long timeOfCut;
    private final String name;

    public Client(long timeOfCut, String name) {
        this.timeOfCut = timeOfCut;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getTimeOfCut() {
        return timeOfCut;
    }

    @Override
    public void run() {
        BarberShop.getInstance().processClient(this);
    }
}
