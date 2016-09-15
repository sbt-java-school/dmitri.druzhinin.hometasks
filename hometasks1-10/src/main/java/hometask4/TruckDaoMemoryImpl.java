package hometask4;

import java.util.Arrays;
import java.util.List;

public class TruckDaoMemoryImpl implements TruckDao{
    @Override
    public List<Truck> list(){
        return Arrays.asList(
                new Truck(1, 10, Truck.Type.KAMAZ),
                new Truck(2, 30, Truck.Type.MAZ),
                new Truck(3, 1, Truck.Type.KAMAZ),
                new Truck(4, 50, Truck.Type.ZIL),
                new Truck(5, 5, Truck.Type.KAMAZ)
        );
    }
}
