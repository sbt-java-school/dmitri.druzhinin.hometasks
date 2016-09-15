package hometask4;

public class Truck {
    private long id;
    private int capacity;
    private Type type;

    public Truck(long id, int capacity, Type type){
        this.id = id;
        this.capacity=capacity;
        this.type=type;
    }

    public long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        return id == truck.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", type=" + type +
                '}';
    }

    public enum Type{
        MAZ, KAMAZ, SCANIA, ZIL
    }
}
