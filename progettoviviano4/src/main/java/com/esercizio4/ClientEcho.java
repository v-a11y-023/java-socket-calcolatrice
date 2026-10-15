package com.esercizio4;

import java.net.Socket;
import java.util.Scanner; //serve al server per poter leggere quello che l'utente scrive
import java.io.BufferedReader; //x leggere il testo dal server
import java.io.InputStreamReader; //prende i dati grezzi del server e li converte in testo
import java.io.PrintWriter; //serve per inviare il testo al server   

public class ClientEcho {
    public static void main(String[] args) {
       int porta = 12345;
       String host = "localhost";

        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Benvenuto/a nella calcolatrice!");
            System.out.println("Puoi effettuare molteplici calcoli senza dover chiudere la comunicazione");
            System.out.println("Scrivi 'esci' quando inserisci il primo numero per terminare");
            
            while(true) {
                System.out.print("Inserire il primo numero: ");
                String inputUtente = scanner.next();

                if (inputUtente.equalsIgnoreCase("esci")) { //viene controllato se l'utente vuole uscire
                    System.out.println("Grazie per aver usato la calcolatrice! Addio!");
                    break;
                }

                double n1;
                try {
                    n1 = Double.parseDouble(inputUtente); //si converte la string in numero
                } catch (NumberFormatException e) {
                    System.out.println("Errore: devi inserire un numero valido o la parola 'esci'! Riprovare!");
                    continue; //con continue si può ritornare all'inizio del ciclo per richiedere i dati
                }

                System.out.print("Inserire l'operatore (+, -, *, /): ");
                String operatore = scanner.next();

                System.out.print("Inserire il secondo numero: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Errore: Il secondo valore deve essere un numero! Riprova...");
                    scanner.next(); //svuota l'input errato rimasto nello scanner
                    continue;
                }
                double n2 = scanner.nextDouble();

                String mexDaInviare = n1 + " " + operatore + " " + n2;

                try (Socket socket = new Socket(host, porta);
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        output.println(mexDaInviare);
                        String risposta = input.readLine();
                        System.out.println("Risposta: " + risposta);
                } catch (Exception e) {
                    System.out.println("Impossibile connettersi al server! Assicurarsi che sia avviato!");
                }
            }
        }
    }
}
