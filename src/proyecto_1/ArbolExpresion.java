
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
    

    // Construir el arbol a partir de una expresion

     // Construir el arbol a partir de una expresion

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
    
    // Convertir expresion a notacion postfija
    private String convertirPostfija(String expresion) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            // Detectar multiplicación implícita (a(b+c) o ab)
            if (i > 0 && (Character.isLetterOrDigit(expresion.charAt(i - 1)) || expresion.charAt(i - 1) == ')') &&
                (Character.isLetterOrDigit(caracter) || caracter == '(')) {
                while (!pila.isEmpty() && precedencia('*') <= precedencia(pila.peek())) {
                    resultado.append(pila.pop());
                }
                pila.push('*');
            }

            if (Character.isLetterOrDigit(caracter) || caracter == '.') {
                resultado.append(caracter);
            } else if (caracter == '(') {
                pila.push(caracter);
            } else if (caracter == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    resultado.append(pila.pop());
                }
                pila.pop(); // Eliminar '('
            } else if (esOperador(caracter)) {
                while (!pila.isEmpty() && precedencia(caracter) <= precedencia(pila.peek())) {
                    resultado.append(pila.pop());
                }
                pila.push(caracter);
            }
        }

        while (!pila.isEmpty()) {
            resultado.append(pila.pop());
        }

        return resultado.toString();
    }


// Evaluar la expresion
    public double evaluarExpresion() {
        return evaluarExpresion(raiz);
    }

 private double evaluarExpresion(Nodo nodo) {
        if (nodo == null) return 0.0;

        if (Character.isLetter(nodo.dato.charAt(0))) {
            return variables.get(nodo.dato.charAt(0));
        }

        if (nodo.dato.equals("-") && nodo.izquierdo == null) {
            return -evaluarExpresion(nodo.derecho);
        }

        double izquierdo = evaluarExpresion(nodo.izquierdo);
        double derecho = evaluarExpresion(nodo.derecho);

        switch (nodo.dato) {
            case "+": return izquierdo + derecho;
            case "-": return izquierdo - derecho;
            case "*": return izquierdo * derecho;
            case "/": return izquierdo / derecho;
            case "^": return Math.pow(izquierdo, derecho);
            default: return Double.parseDouble(nodo.dato);
        }
    }
// Recorridos del arbol
    public void recorridoPreOrden() { recorridoPreOrden(raiz); }
    public void recorridoInOrden() { recorridoInOrden(raiz); }
    public void recorridoPosOrden() { recorridoPosOrden(raiz); }

    private void recorridoPreOrden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            recorridoPreOrden(nodo.izquierdo);
            recorridoPreOrden(nodo.derecho);
        }
    }

    private void recorridoInOrden(Nodo nodo) {
        if (nodo != null) {
            recorridoInOrden(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            recorridoInOrden(nodo.derecho);
        }
    }

    private void recorridoPosOrden(Nodo nodo) {
        if (nodo != null) {
            recorridoPosOrden(nodo.izquierdo);
            recorridoPosOrden(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }

    // Imprimir arbol grafico
    public void imprimirArbolGrafico() {
        imprimirArbolGrafico(raiz, 0);
    }

    private void imprimirArbolGrafico(Nodo nodo, int nivel) {
        if (nodo != null) {
            imprimirArbolGrafico(nodo.derecho, nivel + 1);
            System.out.println("   ".repeat(nivel) + nodo.dato);
            imprimirArbolGrafico(nodo.izquierdo, nivel + 1);
        }
    }

    // Precedencia de operadores
    private int precedencia(char operador) {
        return switch (operador) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    // Verificar si un caracter es un operador
    private boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/' || caracter == '^';
    }

    
    
}
