import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                Thread hiloCliente = new Thread(new ManejadorCliente(clienteSocket));
                hiloCliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ManejadorCliente implements Runnable {
        private final Socket clienteSocket;

        public ManejadorCliente(Socket clienteSocket) {
            this.clienteSocket = clienteSocket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream());

                Llista lista = (Llista) in.readObject();
                System.out.println("Lista recibida del cliente: " + lista.getNom() + " - " + lista.getNumberList());

                List<Integer> listaOrdenada = new ArrayList<>(new TreeSet<>(lista.getNumberList()));

                out.writeObject(new Llista(lista.getNom(), listaOrdenada));
                out.flush();

                clienteSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
