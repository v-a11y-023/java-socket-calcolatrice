package com.esercizio4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {
    public static void main(String[] args) {
       int porta = 12345;

       BufferedReader in = null;
       PrintWriter out = null;

        try(ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Il server è in ascolto sulla porta " + porta);

            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket + " connesso");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //inizializza gli stream x comunicare col client
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String messaggioRic = in.readLine(); //legge il messaggio inviato dal client
            System.out.println("Messaggio ricevuto dal client: " + messaggioRic);

            if(messaggioRic != null) {
                String mexInMaiusc = messaggioRic.toUpperCase(); //converte il mex ricevuto in maiuscolo
                out.println(mexInMaiusc); //invia la risposta al client
                System.out.println("Risposta inviata al client: " + mexInMaiusc);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            System.err.println("Impossibile ascoltare sulla porta " + porta + ": " + e.getMessage());
        }
    }
    
}
