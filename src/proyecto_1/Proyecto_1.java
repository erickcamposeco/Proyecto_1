
package proyecto_1;
import java.util.Scanner;

/**
 *
 * @author Erick Camposeco
 */
public class Proyecto_1 {
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ArbolExpresion arbol = new ArbolExpresion();
    String expresion;

    // Bucle para validar la expresión
    while (true) {
        System.out.print("Ingrese la expresión matemática: ");
        expresion = scanner.nextLine();

        if (esExpresionValida(expresion)) {
            break; // Salir del bucle si la expresión es válida
        } else {
            System.out.println("Error: La expresión ingresada no es válida. Inténtelo de nuevo.\n");
        }
    }
        System.out.println("La expresión ingresada es válida. Puedes continuar.");
        arbol.construirArbol(expresion);

        int opcion;
        do {
            System.out.println("\n___________Menú_________:");
            System.out.println("1. Imprimir árbol de expresión");
            System.out.println("2. Imprimir árbol de expresión gráfico");
            System.out.println("3. Recorrido PreOrden");
            System.out.println("4. Recorrido InOrden");
            System.out.println("5. Recorrido PosOrden");
            System.out.println("6. Modificar valores de variables");
            System.out.println("7. Evaluar expresión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> {
                    System.out.println("\nÁrbol de expresión:");
                    arbol.recorridoInOrden();
                }
                case 2 -> {
                    System.out.println("\nÁrbol de expresión gráfico:");
                    arbol.imprimirArbolGrafico();
                }
                case 3 -> {
                    System.out.println("\nRecorrido PreOrden:");
                    arbol.recorridoPreOrden();
                }
                case 4 -> {
                    System.out.println("\nRecorrido InOrden:");
                    arbol.recorridoInOrden();
                }
                case 5 -> {
                    System.out.println("\nRecorrido PosOrden:");
                    arbol.recorridoPosOrden();
                }
                case 6 -> modificarVariables(arbol, scanner);
                case 7 -> {
                    System.out.println("\nResultado de la evaluación:");
                    try {
                        System.out.println("El resultado de la expresión es: " + arbol.evaluarExpresion());
                    } catch (ArithmeticException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void modificarVariables(ArbolExpresion arbol, Scanner scanner) {
        System.out.println("Ingrese la variable que desea modificar:");
        char variable = scanner.next().charAt(0);

        if (arbol.variables.containsKey(variable)) {
            System.out.println("El valor actual de '" + variable + "' es: " + arbol.variables.get(variable));
            System.out.println("Ingrese el nuevo valor para '" + variable + "':");
            arbol.variables.put(variable, scanner.nextDouble());
            System.out.println("Se ha modificado el valor de '" + variable + "' correctamente.");
        } else {
            System.out.println("La variable '" + variable + "' no existe en la expresión.");
        }
    }

    public static boolean esExpresionValida(String expresion) {
        if (expresion == null || expresion.isEmpty()) return false;

        int parentesis = 0;
        for (char c : expresion.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '+' && c != '-' && c != '*' && c != '/' && c != '^' && c != '(' && c != ')' && c != '.') {
                return false;
            }
            if (c == '(') parentesis++;
            if (c == ')') parentesis--;
            if (parentesis < 0) return false;
        }

        return parentesis == 0;
    }
}
# Documentación del Proyecto: Árbol de Expresiones Matemáticas

## Descripción General
Este programa en Java permite construir y manipular un árbol de expresión binario a partir de una expresión matemática ingresada por el usuario. Soporta operadores básicos (`+`, `-`, `*`, `/`, `^`), variables, números decimales y manejo de paréntesis. Incluye funcionalidades como evaluación de expresiones, recorridos del árbol y modificación de variables.

---

## Estructura del Código

### Clases Principales
1. **`ArbolExpresion`**  
   - Contiene la lógica para construir el árbol, evaluar expresiones y realizar recorridos.
   - **Métodos clave**:
     - `construirArbol(String expresion)`: Convierte la expresión a postfija y construye el árbol.
     - `evaluarExpresion()`: Calcula el resultado de la expresión.
     - `recorridoPreOrden()`, `recorridoInOrden()`, `recorridoPosOrden()`: Recorridos del árbol.
     - `imprimirArbolGrafico()`: Muestra el árbol en formato visual.

2. **`Nodo` (Clase Interna)**  
   - Representa un nodo del árbol con datos y referencias a hijos izquierdo/derecho.

3. **`PROYECTO_1` (Clase Principal)**  
   - Maneja la interacción con el usuario mediante un menú interactivo.

---

## Funcionalidades

### 1. Validación de Expresión
- **Reglas**:  
  - Solo permite letras, dígitos, operadores (`+`, `-`, `*`, `/`, `^`) y paréntesis.
  - Verifica balance de paréntesis.
- **Ejemplo válido**: `a*(b+3.5)-c^2`  
- **Ejemplo inválido**: `a+*b` (operadores consecutivos).

### 2. Construcción del Árbol
- **Pasos**:
  1. Convierte la expresión infija a postfija (ej: `a+b*c` → `abc*+`).
  2. Construye el árbol usando una pila, manejando operadores unarios (ej: `-5`).

### 3. Menú Interactivo
```plaintext
1. Imprimir árbol de expresión (InOrden)
2. Imprimir árbol gráfico
3. Recorrido PreOrden
4. Recorrido InOrden
5. Recorrido PosOrden
6. Modificar valores de variables
7. Evaluar expresión
0. Salir