package com.esercizio4;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerEcho {
    public static void main(String[] args) {
       int porta = 12345;

       ExecutorService pool = Executors.newFixedThreadPool(10);
        try(ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Il server è in ascolto sulla porta " + porta);

            while (true) { 
                Socket socket = serverSocket.accept(); 
                System.out.println("Client connesso da: " + socket.getInetAddress()); 

                ClientHandler handler = new ClientHandler(socket);
                
                pool.execute(handler);
            }
        } catch (Exception e) {
            System.err.println("Errore! L'errore è il seguente: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
