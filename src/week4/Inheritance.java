package week4;

class Parent {
    public int a = 10;

    public void printA() {
        System.out.println(a);
    }
}

class Child extends Parent {
    public String b = "Helo";

    public void printB() {
        System.out.println(b);
    }

}

public class Inheritance {
    public static void main(String[] args) {
        Child child = new Child();  
        child.printA();
        child.printB();
    }

}   
