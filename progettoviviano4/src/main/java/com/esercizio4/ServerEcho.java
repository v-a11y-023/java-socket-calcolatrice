package com.esercizio4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {
    public static void main(String[] args) {
       int porta = 12345;

        try(ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Il server è in ascolto sulla porta " + porta);

            while (true) { //usiamo while(true) in modo tale da far rimanere il server in attività, così da poter accettare più client
                Socket socket = serverSocket.accept(); //mette il server in pausa fino a che un client non tenta di connettersi
                System.out.println("Client connesso: " + socket.getInetAddress()); //quando si connette restituisce un oggetto Socket per comunicare e stampa il suo indirizzo IP

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); //in questo modo si creano i canali di comunicazione col client
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //input serve per leggere ciò che il client invia, mentre output serve per rispondergli. Mettendo true come parametro fa in modo che i dati vengono inviati subito

                String richiesta = input.readLine(); //legge la stringa inviata dal client
                if (richiesta != null) {
                    System.out.println("Ricevuto: " + richiesta); //se essa non è vuota viene stampata
                    
                    String risposta = calcolami(richiesta); //qui invece viene richiamata la funzione per svolgere i calcoli
                    
                    output.println(risposta); //infine il risultato viene inviato al client
                }
                socket.close(); //si chiude la connessione con il client corrente
                System.out.println("Connessione chiusa con il client.\n"); //e come ultima cosa torna all'inizio del ciclo while in attesa del prossimo client
            }
        } catch (Exception e) {
            System.err.println("Impossibile ascoltare sulla porta " + porta + ": " + e.getMessage());
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
                    return "Errore: Operatore non riconosciuto ('" + operatore + "'). Usa +, -, *, /";
            }
            return "Risultato: " + ris; //infine viene restituita la stringa con il risultato
        } catch (NumberFormatException e) { //intercetta gli errori (nel caso in cui l'utente scriva per sbaglio lettere al posto dei numeri, anche se teoricamente è difficile che avvenga)
            return "Errore: I numeri inseriti non sono validi.";
        }
    } 
}
