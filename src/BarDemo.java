// Ejemplo de CyclicBarrier
import java.util.concurrent.*;

class BarDemo {
    public static void main(String args[]) {
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
        System.out.println("Comenzando");
        new MyThread(cb, "A");
        new MyThread(cb, "B");
        new MyThread(cb, "C");
    }
}

// Un hilo de ejecución que utiliza un CyclicBarrier
class MyThread implements Runnable {
    CyclicBarrier cbar;
    String name;

    MyThread(CyclicBarrier c, String n) {
        cbar = c;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println(name);
        try {
            cbar.await();
        } catch (BrokenBarrierException exc) {
            System.out.println(exc);
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
    }
}

// Un objeto de esta clase es llamado cuando el CyclicBarrier termina.
class BarAction implements Runnable {
    public void run() {
        System.out.println("Límite Alcanzado.");
    }
}