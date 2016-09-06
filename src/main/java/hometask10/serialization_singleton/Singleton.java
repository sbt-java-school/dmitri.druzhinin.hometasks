package hometask10.serialization_singleton;

import java.io.Serializable;

public class Singleton implements Serializable {
    private static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){
        if(instance==null){
            instance=new Singleton();
        }
        return instance;
    }

    /**
     * Метод нужен для гарантии, что объект типа Singleton останется единственным в потоке после десериализации.
     * @return объект Singleton.
     */
    private Object readResolve(){
        return getInstance();
    }
}
