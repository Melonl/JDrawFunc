package net.melonl;

public class Main {

    public static void main(String[] args) {

        //new MainWindow();
        //test();

        Array<String> arr = Function.splitExp("-0.5+sin(5)*6/0.5+cos(x)^50");
        print(arr.toString());
    }

    private static void print(String s) {
        System.out.println(s);
    }

/*
    private static void test() {
        Array<Integer> arr = new Array<Integer>();
        for (int i = 0; i < 10; ++i)
            arr.add(i, 0);
        print(arr.toString());
        arr.remove(0);
        for (Integer i : arr) {
            print(i + "");
        }
    }
 */

}
