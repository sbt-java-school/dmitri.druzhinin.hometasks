package hometask6;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Deque;
import java.util.LinkedList;

public class ClassInfo {
    /**
     *
     * @param args args[0] is a full path to class
     * @throws ClassNotFoundException
     */
    public static void main(String[] args){
        try {
//            Class<?> clazz = Class.forName("hometask6.Test");
            Class<?> clazz=Class.forName(args[0]);
            printInfo(clazz);
        } catch (ClassNotFoundException e) {
            System.out.println("The class does not exist");
        }
    }

    public static void printInfo(Class<?> clazz) {
        System.out.println("Information on class "+clazz.getSimpleName());
        printHierarchy(clazz);
        printConstructors(clazz);
        printAllMethods(clazz);
        printDeclaredFields(clazz);
        printGetters(clazz);
    }

    private static void printHierarchy(Class<?> clazz) {
        System.out.println("\nHierarchy: ");
        Deque<Class> deque=new LinkedList();
        while(clazz!=null){
            deque.offerFirst(clazz);
            clazz=clazz.getSuperclass();
        }
        while(deque.size()!=0){
            System.out.println(deque.removeFirst());
        }
    }

    private static void printAllMethods(Class<?> clazz) {//and private inherited.
        System.out.println("\nMethods: ");
        while(clazz!=null){
            for(Method method:clazz.getDeclaredMethods()){
                System.out.println(method);
            }
            clazz=clazz.getSuperclass();
        }
    }
    private static void printGetters(Class<?> clazz){
        System.out.println("\nGetters: ");
        for(Method method:clazz.getDeclaredMethods()){
            if(method.getName().startsWith("get") && method.getParameters().length==0){
                System.out.println(method);
            }
        }
    }
    private static void printConstructors(Class<?> clazz) {
        System.out.println("\nConstructors: ");
        for(Constructor constructor:clazz.getConstructors()){
            System.out.println(constructor);
        }
    }

    private static void printDeclaredFields(Class<?> clazz){
        System.out.println("\nFields: ");
        for(Field field:clazz.getDeclaredFields()){
            int modifiers = Modifier.fieldModifiers();
            try {
                if(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)
                        && field.getType()==String.class && !field.getName().equals(field.get(clazz))){
                    System.out.println(field+" ,this field must be a value "+field.getName());
                }else{
                    System.out.println(field);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("unavailable", e);
            }
        }
    }
}
