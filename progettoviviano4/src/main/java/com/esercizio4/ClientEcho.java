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

       Scanner tastiera = null;
       PrintWriter out = null;
       BufferedReader in = null;

        try(Socket clientSocket = new Socket(host, porta)) {

            tastiera = new Scanner(System.in); //collegamento alla tastiera
            out = new PrintWriter(clientSocket.getOutputStream(), true); //collega il canale di output al server. True serve per l'invio immediato
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //prende i dati dal server, li converte in testo e li organizza in modo da essere letti una riga per volta

            System.out.println("Connessione stabilita! Connesso al server " + host + ":" + porta);

            System.out.println("Digitare una frase da inviare al server: ");
            String testoUtente = tastiera.nextLine(); //il programma si ferma e aspetta che l'utente scriva qualcosa e prema invio
            out.println(testoUtente); //invia la frase scritta al server
            String rispostaServer = in.readLine(); //il programma sta in attesa fino a che il server non risponde
            System.out.println("Risposta ricevuta dal server: " + rispostaServer);

            out.close();
            tastiera.close();
            in.close();

        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        } 
    }
}
