package hometask10.serialization_proxy;

import java.io.Serializable;

public class Truck implements Serializable{
    public static final String MAZ="MAZ";
    public static final String KAMAZ="KAMAZ";
    private int capacity;
    private long id;
    private String type;

    public Truck(int capacity, long id, String type) {
        this.capacity = capacity;
        this.id = id;
        this.type = type;
    }

    public Truck(int capacity, long id) {
        this(capacity, id, null);
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Вместо объекта Truck будет сериализован объект, возвращаемый эти методом.
     * @return объект для сериализации.
     */
    private Object writeReplace(){
        return new TruckProxy(this);
    }
    @Override
    public String toString() {
        return "Truck{" +
                "capacity=" + capacity +
                ", id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * Объект этого класса будет сериализоваться. При обратном чтении, в методе readResolve() формируется объект Truck,
     * который будет возвращен вызывающему коду.
     */
    static class TruckProxy implements Serializable{
        String data;
        public TruckProxy(Truck original) {
            data=""+original.capacity+", "+ original.id;
            data = (original.type != null) ? (data + ", " + original.type) : data;
        }
        private Object readResolve(){
            String[] pieces=data.split(", ");
            Truck result=new Truck(Integer.parseInt(pieces[0]), Long.parseLong(pieces[1]));
            if(pieces.length==3){
                result.setType(pieces[2]);
            }
            return result;
        }

    }
}
