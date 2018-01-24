package com.company.klientTrade;

import Items.Metale;
import Items.Uzdatniacze;

import java.io.*;
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
                    klientTrade.tradeZakoncz(stringToObject(parts[2]));
                }else if (parts[1].equals("*trade")){
                    if (klientTrade.istrade==0) {
                        klientTrade.setMetalOdebrany(stringToObject(parts[2]),parts[0]);
                    }else {
                        klientTrade.zajety(parts[0]);
                    }
                }else if (parts[1].equals("*accept")){
                    klientTrade.accept=1;
                }else if (parts[1].equals("*zamien")){
                    klientTrade.zamien();
                }else if (parts[1].equals("*zajety")){
                    klientTrade.istrade=0;
                    System.out.println("Gracz aktualnie handluje");
                }else if (parts[1].equals("*nicki")){
                    System.out.println(parts[2]);
                }else if (parts[1].equals("*cancel")){
                    klientTrade.cancel(parts[0]);
                    System.out.println("Cancelled");
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

    public Object stringToObject(String string){
        char[] nowy = string.toCharArray();
        ArrayList<String> listaMiejsc = new ArrayList<String>();
        String zmienna ="";

        for (int i = 14; i < string.length(); i++) {
            if (nowy[i] == '=') {
                zmienna = "";
            }
            if (nowy[i] == ',' || nowy[i] == '}') {
                listaMiejsc.add(zmienna);
            } else {
                if (nowy[i] != '\'' && nowy[i] != ' ' && nowy[i] != '=') {
                    zmienna += nowy[i];
                }
            }
        }
        if (string.substring(0,6).equals("Metale")) {
            return new Metale(listaMiejsc.get(0),Integer.parseInt(listaMiejsc.get(1)),Integer.parseInt(listaMiejsc.get(2)),
                    Integer.parseInt(listaMiejsc.get(3)),Integer.parseInt(listaMiejsc.get(4)),Integer.parseInt(listaMiejsc.get(5)),
                    Integer.parseInt(listaMiejsc.get(6)),Integer.parseInt(listaMiejsc.get(7)));
        }else if (string.substring(0,11).equals("Uzdatniacze")){
            return new Uzdatniacze(listaMiejsc.get(0),Double.parseDouble(listaMiejsc.get(1)),Double.parseDouble(listaMiejsc.get(2)),
                    Double.parseDouble(listaMiejsc.get(3)),Double.parseDouble(listaMiejsc.get(4)),Double.parseDouble(listaMiejsc.get(5)),
                    Double.parseDouble(listaMiejsc.get(6)),Double.parseDouble(listaMiejsc.get(7)));
        }
        return new Metale(listaMiejsc.get(0),Integer.parseInt(listaMiejsc.get(1)),Integer.parseInt(listaMiejsc.get(2)),
                Integer.parseInt(listaMiejsc.get(3)),Integer.parseInt(listaMiejsc.get(4)),Integer.parseInt(listaMiejsc.get(5)),
                Integer.parseInt(listaMiejsc.get(6)),Integer.parseInt(listaMiejsc.get(7)));
    }
}

