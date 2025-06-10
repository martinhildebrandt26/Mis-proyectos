package test;

import java.util.Arrays; //necesario para poder ordenar el vector
import java.util.Random; //necesario para cargar el vector

public class BusquedaBinariaConcurrente {

    public static void main(String[] args) {

        int[] vector = generarVectorAleatorio(1000000, 1, 100000); // generamos el vector

        System.out.println("Enseñamos el vector apenas lo cargamos:");
        mostrarVector(vector);

        Arrays.sort(vector); // lo ordenamos para poder realizar la búsqueda binaria

        System.out.println("Ahora este es el vector ordenado:");
        mostrarVector(vector);

        int numeroBuscado = 50000;

        long inicio = System.nanoTime(); // ⏱️ Inicia el cronómetro

        int posicion = busquedaBinariaConcurrente(vector, numeroBuscado);

        long fin = System.nanoTime(); // ⏱️ Finaliza el cronómetro

        double tiempoMs = (fin - inicio) / 1_000_000.0;

        System.out.println("El número buscado se encuentra en la posición: " + posicion);
        System.out.println("Tiempo de ejecución (concurrente): " + tiempoMs + " ms");
    }



    public static int busquedaBinariaConcurrente(int[] vector, int numeroBuscado) {
        ResultadoCompartido resultado = new ResultadoCompartido();

        int medio = vector.length / 2;

        Thread hiloIzquierdo = new Thread(new BusquedaThread(vector, numeroBuscado, 0, medio - 1, resultado));
        Thread hiloDerecho = new Thread(new BusquedaThread(vector, numeroBuscado, medio, vector.length - 1, resultado));

        hiloIzquierdo.start();
        hiloDerecho.start();

        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultado.getPosicion();
    }

    static class BusquedaThread implements Runnable {
        private int[] vector;
        private int numeroBuscado;
        private int inicio;
        private int fin;
        private ResultadoCompartido resultado;

        public BusquedaThread(int[] vector, int numeroBuscado, int inicio, int fin, ResultadoCompartido resultado) {
            this.vector = vector;
            this.numeroBuscado = numeroBuscado;
            this.inicio = inicio;
            this.fin = fin;
            this.resultado = resultado;
        }

        @Override
        public void run() {
            int limiteInferior = inicio;
            int limiteSuperior = fin;
            int posicion = -1;

            while (limiteInferior <= limiteSuperior && resultado.getPosicion() == -1) {
                int indice = (limiteInferior + limiteSuperior) / 2;

                if (vector[indice] == numeroBuscado) {
                    System.out.println("Encontrado por " + Thread.currentThread().getName());
                    resultado.setPosicion(indice);
                    return;
                } else if (numeroBuscado > vector[indice]) {
                    limiteInferior = indice + 1;
                } else {
                    limiteSuperior = indice - 1;
                }
            }
        }
    }

    static class ResultadoCompartido {
        private volatile int posicion = -1;

        public int getPosicion() {
            return posicion;
        }

        public synchronized void setPosicion(int posicion) {
            if (this.posicion == -1) {
                this.posicion = posicion;
            }
        }
    }

    public static int[] generarVectorAleatorio(int tamaño, int minimo, int maximo) {
        int[] vector = new int[tamaño];
        Random rand = new Random();
        for (int i = 0; i < tamaño; i++) {
            vector[i] = rand.nextInt(maximo - minimo + 1) + minimo;
        }
        return vector;
    }

    public static void mostrarVector(int[] vector) {
        for (int num : vector) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

