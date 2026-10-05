// Un ejemplo simple de candado
import java.util.concurrent.locks.*;

class LockDemo {
    public static void main(String args[]) {
        ReentrantLock lock = new ReentrantLock();
        new LockThread(lock, "A");
        new LockThread(lock, "B");
    }
}

// Un recurso compartido.
class SharedLock {
    static int count = 0;
}

// Un hilo de ejecución que incrementa count.
class LockThread implements Runnable {
    String name;
    ReentrantLock lock;

    LockThread(ReentrantLock lk, String n) {
        lock = lk;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Comenzando " + name);

        try {

            System.out.println(name + " está esperando por el candado");
            lock.lock();
            System.out.println(name + " tiene el candado.");

            SharedLock.count++;
            System.out.println(name + ": " + SharedLock.count);


            System.out.println(name + " está durmiendo");
            Thread.sleep(1000);
        } catch (InterruptedException exc) {
            System.out.println(exc);
        } finally {

            System.out.println(name + " está desbloqueando count.");
            lock.unlock();
        }
    }
}