// Una implementación de un productor y consumidor
// que utiliza semáforos para controlar la sincronización
import java.util.concurrent.Semaphore;

class Q {
    int n;
    
	
	
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get() {
        try {
            semCon.acquire();
        } catch (InterruptedException e) {
            System.out.println("Se generó una InterruptedException");
        }
        System.out.println("Obtiene : " + n);
        semProd.release();
    }

    void put(int n) {
        try {
            semProd.acquire();
        } catch (InterruptedException e) {
            System.out.println("Se generó un InterruptedException");
        }
        this.n = n;
        System.out.println("Pone : " + n);
        semCon.release();
    }
}

class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
        new Thread(this, "Productor").start();
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            q.put(i);
        }
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumidor").start();
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            q.get();
        }
    }
}

class ProdCon {
    public static void main(String args[]) {
        Q q = new Q();
        new Consumer(q);
        new Producer(q);
    }
}