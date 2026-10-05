import java.util.concurrent.*;

class SimpExec {
    public static void main(String args[]) {
        CountDownLatch cdl = new CountDownLatch(5);
        CountDownLatch cdl2 = new CountDownLatch(5);
        CountDownLatch cdl3 = new CountDownLatch(5);
        CountDownLatch cdl4 = new CountDownLatch(5);

        ExecutorService es = Executors.newFixedThreadPool(2);

        System.out.println("Comenzando");

        // Comienza los hilos usando el ejecutor
        es.execute(new MyThreadExe(cdl, "A"));
        es.execute(new MyThreadExe(cdl2, "B"));
        es.execute(new MyThreadExe(cdl3, "C"));
        es.execute(new MyThreadExe(cdl4, "D"));

        try {
            cdl.await();
            cdl2.await();
            cdl3.await();
            cdl4.await();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        es.shutdown();
        System.out.println("Hecho");
    }
}

class MyThreadExe implements Runnable {
    String name;
    CountDownLatch latch;

    MyThreadExe(CountDownLatch c, String n) {
        latch = c;
        name = n;
        // El hilo se ejecutará en el pool, así que no es necesario hacer new Thread(this)
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + i);
            latch.countDown();
        }
    }
}