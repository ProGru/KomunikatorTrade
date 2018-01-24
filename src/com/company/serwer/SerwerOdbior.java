package com.company.serwer;

import java.io.*;
import java.net.*;
import java.util.Map;

class SerwerOdbior extends Thread
{
    Socket sock;
    BufferedReader sockReader;
    ListaWatkow listaWatkow;
    String localAddres;
    Map<String,String> slownikUzytkownikow;

    public SerwerOdbior(Socket sock,ListaWatkow listaWatkow,Map<String,String> slownikUzytkownikow) throws IOException
    {
        this.localAddres = Integer.toString(sock.getPort())+":"+sock.getLocalAddress().getHostAddress()+"&";
        this.listaWatkow = listaWatkow;
        this.sock=sock;
        this.sockReader=new BufferedReader(new InputStreamReader(sock.getInputStream()));
        this.slownikUzytkownikow = slownikUzytkownikow;
    }

    public void nadajWiadomosc(String dokogo, String wiadomosc){
        for (int i=0;i<listaWatkow.size();i++) {
            if (slownikUzytkownikow.get(dokogo).equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                listaWatkow.get(i).outp.println(wiadomosc);
                listaWatkow.get(i).outp.flush();
            }

        }
    }

    public void run() {
        try {

            //komunikacja - czytanie danych ze strumienia
            String str ="a";
            while (!str.equals("Q")) {
                str = sockReader.readLine();
                String[] parts = str.split("&");

                if (str == null) {
                    str = "a";
                }else if(parts[1].equals("*trade")){
                    nadajWiadomosc(parts[2],parts[0]+"&*trade&"+parts[3]);
                }else if (parts[1].equals("*tradeOdbior")) {
                    nadajWiadomosc(parts[2],parts[0]+"&*tradeOdbior&"+parts[3]);
                }else if (parts[1].equals("*accept")){
                    nadajWiadomosc(parts[2],parts[0]+"&*accept&");
                }else if (parts[1].equals("*zamien")){
                    nadajWiadomosc(parts[2],parts[0]+"&*zamien&");
                }else if (parts[1].equals("*ustawNazwe")){
                    slownikUzytkownikow.put(parts[2],parts[0]);
                }else if (parts[1].equals("*podajNicki")){
                    nadajWiadomosc(parts[0],parts[0]+"&*nicki&"+slownikUzytkownikow.toString());
                }else if (parts[1].equals("*zajety")){
                    nadajWiadomosc(parts[2],parts[0]+"&*zajety&");
                }else if (parts[1].equals("*cancel")){
                    nadajWiadomosc(parts[2],parts[0]+"&*cancel&");
                }else {
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (!(parts[0].equals(listaWatkow.get(i).sock.getPort()+":"+listaWatkow.get(i).sock.getLocalAddress().getHostAddress()))) {
                            listaWatkow.get(i).outp.println(str);
                            listaWatkow.get(i).outp.flush();
                        }
                    }
                    System.out.println("<Nadeszlo:> " + str);
                }
            }
            //zamykanie polaczenia

        } catch (SocketException e){
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


}

