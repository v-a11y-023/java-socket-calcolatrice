package com.esercizio4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            System.out.println("Gestione client avviata sul thread: " + Thread.currentThread().getName());

            String richiesta = in.readLine();
            if (richiesta != null) {
                System.out.println("[" + Thread.currentThread().getName() + "] Ricevuto: " + richiesta);
                String risposta = calcolami(richiesta);
                out.println(risposta);
            }
        } catch (Exception e) {
            System.err.println("Errore nella gestione del client sul thread " + Thread.currentThread().getName() + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Connessione chiusa sul thread: " + Thread.currentThread().getName() + "\n");
            } catch (Exception e) {
                System.err.println("Errore nella chiusura del socket: " + e.getMessage());
            }
        }
    }
    

    private static String calcolami(String richiesta) {
        try {
            String[] parti = richiesta.split(" "); //divide la stringa ricevuta in pezzi separati da dagli spazi usando grazie a split
            if (parti.length != 3) { //se i pezzi non sono esattamente 3 restituisce un messaggio d'errore
                return "Errore: il formato non è valido! Devi scrivere in ordine il primo numero, l'operatore e il secondo numero!";
            }
            double n1 = Double.parseDouble(parti[0]); //converte il testo in un numero decimale*
            String operatore = parti[1]; //l'operatore centrale viene salvato in una stringa
            double n2 = Double.parseDouble(parti[2]); //*
            double ris;

            switch (operatore) { //switch che controlla l'operatore scelto
                case "+":
                    ris = n1 + n2;
                    break;
                case "-":
                    ris = n1 - n2;
                    break;
                case "*":
                    ris = n1 * n2;
                    break;
                case "/":
                    if (n2 == 0) { //è incluso un controllo di sicurezza per impedire la divisione per zero
                        return "Errore: Impossibile dividere per zero!";
                    }
                    ris = n1 / n2;
                    break;
                default: //vengono gestiti gli operatori non validi
                    return "Errore: Operatore non riconosciuto ('" + operatore + "'). Devi usare +, -, *, /";
            }
            return "Risultato: " + ris; //infine viene restituita la stringa con il risultato
        } catch (NumberFormatException e) { //intercetta gli errori (nel caso in cui l'utente scriva per sbaglio lettere al posto dei numeri, anche se teoricamente è difficile che avvenga)
            return "Errore: I numeri inseriti non sono validi.";
        }
    } 
}
