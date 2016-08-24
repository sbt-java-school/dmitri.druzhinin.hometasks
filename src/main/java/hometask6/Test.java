package hometask6;

public class Test extends ParentTest{
    public static final String MAY="MAY";
    public static final String APRIL="April";//this field must be a value APRIL
    private static final double defaultValue=5.0;
    double value;

    public Test(){
        this(defaultValue);
    }
    public Test(double value){
        this.value=value;
    }

    public double getValue(){
        return value;
    }

}
class ParentTest extends GrandParentTest{
    private int x;
    private void doSomething(){
        System.out.println("doSomething");
    }
}
class GrandParentTest{
    static double multiple(double a, double b){
        return a*b;
    }
}
