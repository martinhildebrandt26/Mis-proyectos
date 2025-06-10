package test;
import java.util.Arrays; //necesario para poder ordenar el vector
import java.util.Random; //necesario para cargar el vector

public class BusquedaBinaria {

    public static void main(String[] args) {

        int[] vector = generarVectorAleatorio(100000000, 1, 10000000); // generamos el vector

        System.out.println("Enseñamos el vector apenas lo cargamos:");
        mostrarVector(vector);

        Arrays.sort(vector); // lo ordenamos para poder realizar la búsqueda binaria

        System.out.println("Ahora este es el vector ordenado:");
        mostrarVector(vector);

        long inicio = System.nanoTime(); // ⏱️ Inicia el cronómetro

        int posicion = busquedaBinaria(vector, 5000000); // búsqueda secuencial

        long fin = System.nanoTime(); // ⏱️ Finaliza el cronómetro

        double tiempoMs = (fin - inicio) / 1_000_000.0;

        System.out.println("El número buscado se encuentra en la posición: " + posicion);
        System.out.println("Tiempo de ejecución (secuencial): " + tiempoMs + " ms");
    }


    
  

    public static int busquedaBinaria(int [] vector, int numeroBuscado) {
        int posicion = -1;// Esta variable determina si encontramos o no el numero, su valor sera de -1 si no es encontrado o de la posicion donde se encuentra en el vector
        int limiteInferior = 0;// el limite inferior, inicialmente, siempre va a ser la primera posicion del vector
        int limiteSuperior = vector.length - 1; //el limite superior, inicialmente, siempre va a ser la ultima posicion del vector
        int indice; //el indice nos va a servir para recorrer el vector
                     
        while (limiteInferior <= limiteSuperior && posicion == -1) { //mientras el limite inferior sea menor o igual al vector significa que todavia nos falta recorrer el vector, mientras la posicion sea -1 significa que no se encontro el numero
            indice = (limiteInferior + limiteSuperior) / 2; //calculamos el valor del indice que sera el punto medio aproximado entre los limites 
                 
            if (vector[indice] == numeroBuscado) { //en caso de que se encuentre el numero en la posicion del indice, ya mandamos el resultado para salir del ciclo while
                System.out.println("Encontrado"); //enviamos un mensaje por consola
                posicion = indice; //asignamos el valor de la posicion al indice, por lo que la condicion del while fallara y saldremos del bucle
            }else if (numeroBuscado > vector[indice]) { //en caso de que el indice no sea la posicion del numero buscado, verificamos si este es mayor al numero en el indice
                limiteInferior = indice + 1; //en caso de que lo sea podemos asumir que se encuentra en la segunda mitad del vector, por lo que subimos el limite inferior, la proxima busqueda sera en la segunda mitad
            }else if (numeroBuscado < vector[indice]) { //en el caso contrario de que el numero buscado sea menor al que se encuentra en el indice, podemos asumir que se encuentra en la primera mitad del vector
                limiteSuperior = indice - 1; //bajamos el limite superior, por lo que la proxima busqueda sera en la primera mitad 
            }
        }
             
        return posicion; //finalmente retornamos la posicion, si vale -1 quiere decir que el numero no fue encontrado, caso contrario se retorna la posicion donde se encontro
    }
    

    
    public static int[] generarVectorAleatorio(int tamaño, int minimo, int maximo) {
        int[] vector = new int[tamaño]; //creamos el vector con el tamaño enviado por parametro
        Random rand = new Random();// creamos un rand para cargar el vector

        for (int i = 0; i < tamaño; i++) { //el for recorre todas las posiciones del vector
            vector[i] = rand.nextInt(maximo - minimo + 1) + minimo; //asignamos un valor aleatorio entre el maximo y el minimo
        }

        return vector; //finalmente retornamos el vector
    }
    
    public static void mostrarVector(int[] vector) {
        for (int num : vector) {
            System.out.print(num + " ");
        }
        System.out.println(); // salto de línea al final
    }
}