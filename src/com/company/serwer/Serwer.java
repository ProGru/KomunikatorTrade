package com.company.serwer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Serwer
{
    public static final int PORT=50007;


    public static void main(String args[]) throws IOException
    {
        //tworzenie gniazda serwerowego
        ServerSocket serv;
        serv=new ServerSocket(PORT);
        ListaWatkow listaWatkow = new ListaWatkow();
        Map<String,String> slownikUzytkownikow = new HashMap<String, String>();

        while (true) {
            //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
            System.out.println("Nasluchuje: " + serv);
            Socket sock;
            sock = serv.accept();
            System.out.println("Jest polaczenie: " + sock);
            SerwerOdbior odbior = new SerwerOdbior(sock,listaWatkow,slownikUzytkownikow);
            SerwerWyslij wyslij = new SerwerWyslij(sock);
            listaWatkow.add(wyslij);
            odbior.start();
            wyslij.start();
        }


    }
}
