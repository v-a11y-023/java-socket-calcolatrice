package com.esercizio4;

import java.net.Socket;
import java.util.Scanner; //serve al server per poter leggere quello che l'utente scrive
import java.io.BufferedReader; //x leggere il testo dal server
import java.io.InputStreamReader; //prende i dati grezzi del server e li converte in testo
import java.io.PrintWriter; //serve per inviare il testo al server   

public class ClientEcho {
    public static void main(String[] args) {
       int porta = 5000;
       String host = "localhost";

        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Benvenuto/a nella calcolatrice!");
            
            System.out.print("Inserire il primo numero: "); //legge i dati inseriti dalla tastiera dell'utente con scanner
            double num1 = scanner.nextDouble();

            System.out.print("Inserire l'operatore (+, -, *, /): ");
            String operatore = scanner.next();

            System.out.print("Inserire il secondo numero: ");
            double num2 = scanner.nextDouble();

            String mexDaInviare = num1 + " " + operatore + " " + num2; //si uniscono le tre variabili in unica stringa separata da degli spazi

            // Connessione al server
            try (Socket socket = new Socket(host, porta); //ci si connette al server tramite la classe Socket (ovviamente si passano ip e porta)
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println("Connessione al server stabilita...");

                output.println(mexDaInviare); //si inviano i dati al server

                String risposta = input.readLine(); //legge la risposta del server e la stampa
                System.out.println("Risposta dal server: " + risposta);

            } catch (Exception e) {
                System.out.println("Impossibile connettersi al server! Assicurarsi che sia avviato");
            }
        }
    }
}
