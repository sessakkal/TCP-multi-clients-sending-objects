import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteTCP {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el nombre de la lista: ");
            String nombreLista = scanner.nextLine();
            System.out.print("Ingrese los números separados por espacios: ");
            String numerosInput = scanner.nextLine();

            List<Integer> numeros = parsearNumeros(numerosInput);

            Llista lista = new Llista(nombreLista, numeros);
            out.writeObject(lista);
            out.flush();
            System.out.println("Lista enviada al servidor: " + lista.getNom() + " - " + lista.getNumberList());

            Llista listaOrdenada = (Llista) in.readObject();
            System.out.println("Lista ordenada recibida del servidor: " + listaOrdenada.getNom() + " - " + listaOrdenada.getNumberList());

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> parsearNumeros(String input) {
        List<Integer> numeros = new ArrayList<>();
        String[] partes = input.split(" ");
        for (String parte : partes) {
            try {
                int numero = Integer.parseInt(parte);
                numeros.add(numero);
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear el número: " + parte);
            }
        }
        return numeros;
    }
}

