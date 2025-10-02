package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {

        if (s == null || s.isEmpty()){
            return true;
        }

        PilaGenerica<Character> pila  = new PilaGenerica<>(s.length());

        try {

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == '(' || c == '[' || c == '{') {
                    pila.Push(c);

                }

                else if (c == ')' || c == ']' || c == '}') {

                    if (pila.getTop() == 0){
                        return false;
                }

                    char tope = pila.Pop();

                    if(!esParValido(tope, c)) {
                        return false;
                    }

            }

        }
        return pila.getTop() == 0;

    } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean esParValido(char apertura, char cierre) {
        return (apertura == '(' && cierre == ')') ||
                (apertura == '[' && cierre == ']') ||
                (apertura == '{' && cierre == '}');

    }

        /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
        public void encontrarParesConSuma(int[] numeros, int objetivo) {
            try {
                if (numeros == null || numeros.length < 2) {
                    System.out.println("Se necesitan al menos 2 números para formar un par");
                    return;
                }

                int tamañoTabla = Math.max(numeros.length * 2, 10);
                TablasHash vistos = new TablasHash(tamañoTabla);

                TablasHash paresUnicos = new TablasHash(tamañoTabla);

                int contadorPares = 0;
                System.out.println("Buscando pares que sumen " + objetivo);

                for (int i = 0; i < numeros.length; i++) {
                    int actual = numeros[i];
                    int complemento = objetivo - actual;

                    if (vistos.search(complemento, complemento)) {
                        int menor = Math.min(actual, complemento);
                        int mayor = Math.max(actual, complemento);

                        if (!paresUnicos.search(menor, mayor)) {
                            paresUnicos.insert(menor, mayor);
                            System.out.println("Par encontrado: (" + menor + ", " + mayor + ")  "
                                    + menor + " + " + mayor + " = " + objetivo);
                            contadorPares++;
                        }
                    }

                    if (!vistos.search(actual, actual)) {
                        vistos.insert(actual, actual);
                    }
                }

                System.out.println("\n" + "=".repeat(40));
                if (contadorPares == 0) {
                    System.out.println("Ningún par encontrado que sume " + objetivo);
                } else {
                    System.out.println("Total de pares únicos encontrados: " + contadorPares);
                }
                System.out.println("=".repeat(40));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
