/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuerzabruta;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Algorimo {

    private ArrayList<ArrayList<Integer>> parejas = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> solucion = new ArrayList<>();
    private ArrayList<Integer> trio = new ArrayList<>();
    private ArrayList<Integer> pareja = new ArrayList<>();
    private int contSoluciones = 0;

    public Algorimo() {
        /**
         * Se generan las posibles parejas de musas
         * y se llama a la función recorrer.
         */
        generarParejas();
        recorrer(0, 1, 0);
        System.out.println("Existen:" + contSoluciones + " soluciones distintas.");
    }

    /**
     * Método que genera las parejas de musas y luego agrega estas parejas
     * al ArrayList parejas.
     * 
     * Parejas es un ArrayList que contienen otros ArrayList en su interior.
     */
    private void generarParejas() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i != j && j > i) {
                    pareja.add(i + 1);
                    pareja.add(j + 1);
                    parejas.add((ArrayList<Integer>) pareja.clone());
                    pareja.clear();
                }
            }
        }
//        System.out.println(parejas);
    }
    
    /**
     * Método recursivo que llenando el ArrayList solución, con las parejas que
     * sean aceptadas.
     * @param pos posición de la pareja de musas que entra a verificar.
     * @param nivel nivel en el que se encuentra la solución.
     * @param posSol variable útilizada para guardar la última posición, en 
     * caso de avanzar de nivel y se tenga que volver atrás en el recorrido.
     */
    private void recorrer(int pos, int nivel, int posSol) {
        int nivelAnt = 0;
        int nivelDes = 0;

        if (pos < parejas.size()) {

            if (verificar(pos)) {
                nivel = verificarNivel();
                nivelAnt = nivel;
                /**
                 * Se agrega un trio a la solución.
                 */
                trio.add(nivel);
                trio.add(parejas.get(pos).get(0));
                trio.add(parejas.get(pos).get(1));
                solucion.add((ArrayList<Integer>) trio.clone());
                trio.clear();
                
                /**
                 * Imprimir solución en caso de llegar a 12 tríos validos.
                 */
                if (solucion.size() == 12) {
                    System.out.println(solucion);
                    contSoluciones++;
                }
                nivelDes = verificarNivel();
                
                /**
                 * Una vez se avanza de nivel, se reinicia la variable pos a 0,
                 * para recorrer nuevameten todas las parejas.
                 * En este caso se ve pos = -1, pero se debe a que cuando se
                 * llama la función nuevamente se incremeta en 1 esta variable.
                 */
                if (nivelAnt < nivelDes) {
                    posSol = pos;
                    pos = -1;
                }

                recorrer(pos + 1, nivel, posSol + 1);

                solucion.remove(solucion.size() - 1);
                
                /**
                 * En caso de tener que volver en la solución y que la variable 
                 * pos haya sido modificada por un cambio de nivel, se hace uso
                 * de la variable posSol, que almacena la posición antes del 
                 * cambio de nivel.
                 */
                if (pos == -1) {
                    recorrer(posSol + 1, nivel, posSol);
                } else {
                    recorrer(pos + 1, nivel, posSol);
                }

            } else {
                /**
                 * En caso de que la solución no sea valida, se pasa a la siguiente
                 * pareja en el ArrayList.
                 */
                recorrer(pos + 1, nivel, posSol);

            }

        }

    }
    
    /**
     * Método que verifica el nivel en el que se encuentra la solución,
     * cuando el nivel es 1, significa que está buscando parejas para la musa 1,
     * cuando es 2 significa que se encuentra en el nivel 2 y se encuentra
     * buscando parejas para la musa 2.
     * @return nivel
     */
    private int verificarNivel() {
        int nivelTemp = 0;
        if (solucion.size() < 4) {
            nivelTemp = 1;
        } else if (solucion.size() < 7) {
            nivelTemp = 2;
        } else if (solucion.size() < 10) {
            nivelTemp = 3;
        } else if (solucion.size() < 11) {
            nivelTemp = 4;
        } else if (solucion.size() < 12) {
            nivelTemp = 5;
        }
        return nivelTemp;
    }
    
    /**
     * Método que recibe la posición de la pareja que se encuentra verificando,
     * en este método esta pareja, con cada uno de los trios ya formados en la
     * solución.
     * @param pos posición de la pareja de musas que entra a verificar.
     * @return booleando de la verificación.
     */
    private boolean verificar(int pos) {
        int nivel = verificarNivel();

        if (parejas.get(pos).get(0) == nivel || parejas.get(pos).get(1) == nivel || nivel > 5) {
            return false;
        }
        if (!solucion.isEmpty()) {
            for (ArrayList<Integer> parejaTemp : solucion) {
                if (parejaTemp.contains(parejas.get(pos).get(0)) && parejaTemp.contains(parejas.get(pos).get(1))
                        || parejaTemp.contains(parejas.get(pos).get(0)) && parejaTemp.contains(nivel)
                        || parejaTemp.contains(parejas.get(pos).get(1)) && parejaTemp.contains(nivel)) {
                    return false;
                }

            }
        }

        return true;
    }

}
