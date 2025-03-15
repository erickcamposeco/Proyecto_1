
package proyecto_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
/**
 *
 * @author Erick Camposeco
 */
public class ArbolExpresion {
    
    Nodo raiz;
    Map<Character, Double> variables;

    static class Nodo {
    String dato;
    Nodo izquierdo, derecho;

        Nodo(String dato) {
            this.dato = dato;
            izquierdo = derecho = null;
        }
    }

    public ArbolExpresion() {
        raiz = null;
        variables = new HashMap<>();
    }
    
     // Construir el árbol a partir de una expresión
    public void construirArbol(String expresion) {
        String postfija = convertirPostfija(expresion);
        Stack<Nodo> pila = new Stack<>();

        for (int i = 0; i < postfija.length(); i++) {
            char caracter = postfija.charAt(i);
            if (esOperador(caracter)) {
                Nodo nodo = new Nodo(String.valueOf(caracter));
                if (caracter == '-' && (i == 0 || esOperador(postfija.charAt(i - 1)) || (i > 0 && postfija.charAt(i - 1) == '('))) {
                    // Negación unaria
                    nodo.izquierdo = pila.pop();
                } else {
                    // Operador binario
                    nodo.derecho = pila.pop();
                    nodo.izquierdo = pila.pop();
                }
                pila.push(nodo);
            } else if (Character.isLetter(caracter)) {
                // Variable
                if (!variables.containsKey(caracter)) {
                    System.out.println("Ingrese el valor para '" + caracter + "':");
                    variables.put(caracter, new Scanner(System.in).nextDouble());
                }
                pila.push(new Nodo(String.valueOf(caracter)));
            } else if (Character.isDigit(caracter) || caracter == '.') {
                // Número flotante
                StringBuilder numero = new StringBuilder();
                while (i < postfija.length() && (Character.isDigit(postfija.charAt(i)) || postfija.charAt(i) == '.')) {
                    numero.append(postfija.charAt(i++));
                }
                pila.push(new Nodo(numero.toString()));
                i--;
            }
        }

        raiz = pila.pop();
    }

    

    
}
