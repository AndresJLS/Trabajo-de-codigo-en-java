import java.util.concurrent.*;

public class BarDemo2 {
    public static void main(String args[]) {
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction2());
        System.out.println("Comenzando");

        new MyThread2(cb, "A");
        new MyThread2(cb, "B");
        new MyThread2(cb, "C");
        new MyThread2(cb, "X");
        new MyThread2(cb, "Y");
        new MyThread2(cb, "Z");
    }
}

class MyThread2 implements Runnable {
    CyclicBarrier cbar;
    String name;

    MyThread2(CyclicBarrier c, String n) {
        cbar = c;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println(name);
        try {
            cbar.await();
        } catch (BrokenBarrierException | InterruptedException exc) {
            System.out.println(exc);
        }
    }
}

class BarAction2 implements Runnable {
    public void run() {
        System.out.println("Límite Alcanzado.");
    }
}