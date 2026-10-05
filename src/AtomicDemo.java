// Un ejemplo simple de Atomic
import java.util.concurrent.atomic.*;

class AtomicDemo {
    public static void main(String args[]) {
        new AtomThread("A");
        new AtomThread("B");
        new AtomThread("C");
    }
}

class SharedAtomic {
    static AtomicInteger ai = new AtomicInteger(0);
}

// Un hilo de ejecución
class AtomThread implements Runnable {
    String name;

    AtomThread(String n) {
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Comenzando " + name);
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " obtiene: " + SharedAtomic.ai.getAndSet(i));
        }
    }
}