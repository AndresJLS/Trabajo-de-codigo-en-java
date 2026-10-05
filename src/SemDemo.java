// Ejemplo de uso de objetos tipo Semaphore
import java.util.concurrent.*; //utilería principal de concurrencia

//ejecutar el programa con un semaforo que permite ejecutar 1 hilo a la vez
class SemDemo {
    public static void main(String args[]) {
        Semaphore sem = new Semaphore(1);
        new IncThread(sem, "A");
        new DecThread(sem, "B");

    }
}

// Simulación de un recurso compartido (contador)
class Shared {
    static int count = 0;
}

// Un hilo de ejecución que incrementa count (recurso compartido).
class IncThread implements Runnable {
    String name;
    Semaphore sem;

    IncThread(Semaphore s, String n) {
        sem = s;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Comenzando " + name);

        try {
            //Primero, espera para obtener el permiso
            System.out.println(name + " está esperando por permiso");
            sem.acquire();  //similar a wait( ), aqui esperará hasta que obtenga el permiso
            System.out.println(name + " obtiene permiso.");

            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);

                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        System.out.println(name + " libera el permiso.");
        sem.release(); //similar a notify( ), libera el permiso notificando al semaforo
    }
}

// Un hilo de ejecución que decrementa count (recurso compartido).
class DecThread implements Runnable {
    String name;
    Semaphore sem;

    DecThread(Semaphore s, String n) {
        sem = s;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Comenzando " + name);
        try {
            // Primero solicita obtener el permiso.
            System.out.println(name + " está esperando permiso.");
            sem.acquire(); //similar al wait( )
            System.out.println(name + " obtiene permiso.");

            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);

                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        System.out.println(name + " libera el permiso.");
        sem.release(); //similar al notify( )
    }
}