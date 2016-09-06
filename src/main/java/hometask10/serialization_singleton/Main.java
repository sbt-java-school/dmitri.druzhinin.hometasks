package hometask10.serialization_singleton;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton singleton=Singleton.getInstance();
        String fileName="out.txt";
        try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(singleton);
            out.flush();
        }

        try(ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName))){
            singleton= (Singleton) in.readObject();//если не определить метод readResolve() внутри класса Singleton
            //то in.readObject() создаст новый объект этого типа. Получится два объекта Singleton, что недопустимо.
        }

        if(singleton==Singleton.getInstance()){
            System.out.println("Ok");
        }else{
            System.out.println("Существует два объекта типа Singleton!");
        }
    }
}
