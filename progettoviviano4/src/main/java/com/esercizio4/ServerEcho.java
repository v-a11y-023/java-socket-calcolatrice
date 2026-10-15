package com.esercizio4;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {
    public static void main(String[] args) {
       int porta = 12345;

        try(ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Il server è in ascolto sulla porta " + porta);

            while (true) { 
                Socket socket = serverSocket.accept(); 
                System.out.println("Client connesso da: " + socket.getInetAddress()); 

                ClientHandler handler = new ClientHandler(socket);
                
                Thread nuovoThread = new Thread(handler);
                nuovoThread.start(); 
            }
        } catch (Exception e) {
            System.err.println("Impossibile ascoltare sulla porta " + porta + ": " + e.getMessage());
        }  
    }
}
