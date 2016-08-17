package hometask4;

import hometask4.multimap.ArrayListMultimap;
import hometask4.multimap.Multimap;

import java.util.*;

public class Application {
    private Map<Long, Truck> truckRegistry;
    private Multimap<Truck.Type, Truck> truckRegistryByType;


    public Application(TruckDao truckDao) {
        List<Truck> list = truckDao.list();
        truckRegistry = new HashMap<>();
        truckRegistryByType=new ArrayListMultimap<>();
        for (Truck truck : list) {
            Truck previousValue = truckRegistry.put(truck.getId(), truck);
            if (null != previousValue) {
                throw new IllegalArgumentException("two trucks with same Id");
            }
            truckRegistryByType.put(truck.getType(), truck);
        }
    }

    void viewTruckRegistry() {
        for (Long truckId : truckRegistry.keySet()) {
            System.out.println(truckId);
        }
        System.out.println(truckRegistry);
    }

    void viewTruckRegistryByType(){
        for(Truck.Type typeOfTrucks:truckRegistryByType.keySet()){
            System.out.println(""+typeOfTrucks+": "+truckRegistryByType.get(typeOfTrucks));
        }
    }

    public Truck getTruckById(long truckId) {
        Truck truck = truckRegistry.get(truckId);
        if (truck == null) {
            throw new IllegalArgumentException("truck not found with Id " + truckId);
        }
        return truck;
    }

    public Collection<Truck> getTrucksByType(Truck.Type typeOfTrucks) {
        Collection <Truck> trucks=truckRegistryByType.get(typeOfTrucks);
        if(trucks==null || trucks.isEmpty()){
            throw new IllegalArgumentException("trucks not found with Type " + typeOfTrucks);
        }
        return trucks;
    }

    public static void main(String[] args) {
        Truck.Type trucksType= Truck.Type.KAMAZ;
        TruckDao truckDao = new TruckDaoMemoryImpl();
        Application application = new Application(truckDao);
        Collection<Truck> trucks=application.getTrucksByType(trucksType);
        System.out.println(trucks);
        System.out.println();
        application.viewTruckRegistryByType();
    }
}
