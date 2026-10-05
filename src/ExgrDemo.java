// Ejemplo de Exchanger.
import java.util.concurrent.Exchanger;

class ExgrDemo {
    public static void main(String args[]) {
        Exchanger<String> exgr = new Exchanger<String>();
        new UseString(exgr);
        new MakeString(exgr);
    }
}

// Un hilo que construye una cadena.
class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;

    MakeString(Exchanger<String> c) {
        ex = c;
        str = new String();
        new Thread(this).start();
    }

    public void run() {
        char ch = 'A';
        for (int i = 0; i < 3; i++) {
            // Llena el buffer
            for (int j = 0; j < 5; j++) {
                str += ch++;
            }
            try {
                // Intercambia un buffer lleno por uno vacío
                str = ex.exchange(str);
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}

// Un hilo que utiliza una cadena
class UseString implements Runnable {
    Exchanger<String> ex;
    String str;

    UseString(Exchanger<String> c) {
        ex = c;
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                // Intercambia un buffer vacío por uno lleno
                str = ex.exchange(new String());
                System.out.println("Obtiene: " + str);
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}