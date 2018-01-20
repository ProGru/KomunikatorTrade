package com.company.klientTrade;

import Items.Metale;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;

public class OdbiorTrade extends Thread {
    Socket sock;
    BufferedReader sockReader;
    KlientTrade klientTrade;


    public OdbiorTrade(Socket sock,KlientTrade klientTrade) throws IOException {
        this.klientTrade = klientTrade;
        this.sock = sock;
        this.sockReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    public void run() {
        try {

            //komunikacja - czytanie danych ze strumienia
            String str = "a";

            while (!str.equals("Q")) {
                str = sockReader.readLine();
                String[] parts = str.split("&");
                if (str == null) {
                    str = "a";
                } else if (parts[1].equals("*tradeOdbior")){
                    klientTrade.tradeZakoncz(stringToMetale(parts[2]));
                }else if (parts[1].equals("*trade")){
                    klientTrade.tradeOdbieramy(stringToMetale(parts[2]),new Metale(),parts[0]);
                }else if (parts[1].equals("*accept")){
                    klientTrade.accept=1;
                }else if (parts[1].equals("*zamien")){
                    klientTrade.zamien();
                }
                else {
                    System.out.println("przyszło : "+str);
                }
            }
            //zamykanie polaczenia

        } catch (SocketException e) {
            try {
                System.out.println("<Zamykam>");
                sockReader.close();
                sock.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("<Zamykam>");
            sockReader.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Metale stringToMetale(String string){
        char[] nowy = string.toCharArray();
        ArrayList<String> listaMiejsc = new ArrayList<String>();
        String zmienna ="";
        for (int i =14; i<string.length();i++){
            if (nowy[i]=='='){
                zmienna="";
            }
            if (nowy[i]==',' || nowy[i]=='}') {
                listaMiejsc.add(zmienna);
            }else{
                if (nowy[i]!='\'' && nowy[i]!=' ' && nowy[i]!= '=') {
                    zmienna += nowy[i];
                }
            }
        }
        return new Metale(listaMiejsc.get(0),Integer.parseInt(listaMiejsc.get(1)),Integer.parseInt(listaMiejsc.get(2)),
                Integer.parseInt(listaMiejsc.get(3)),Integer.parseInt(listaMiejsc.get(4)),Integer.parseInt(listaMiejsc.get(5)),
                Integer.parseInt(listaMiejsc.get(6)),Integer.parseInt(listaMiejsc.get(7)));
    }
}

