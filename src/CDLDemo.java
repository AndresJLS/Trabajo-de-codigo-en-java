// Un ejemplo de CountDownLatch
import java.util.concurrent.CountDownLatch;

class CDLDemo {
    public static void main(String args[]) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("Comenzando");
        new MyThreadCDL(cdl);
        try {
            cdl.await();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        System.out.println("Hecho");
    }
}

class MyThreadCDL implements Runnable {
    CountDownLatch latch;

    MyThreadCDL(CountDownLatch c) {
        latch = c;
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            latch.countDown(); // decrementa count
        }
    }
}