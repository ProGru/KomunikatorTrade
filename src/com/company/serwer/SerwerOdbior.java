package com.company.serwer;

import com.company.serwer.ListaWatkow;

import java.io.*;
import java.net.*;

class SerwerOdbior extends Thread
{
    Socket sock;
    BufferedReader sockReader;
    ListaWatkow listaWatkow;
    String localAddres;
    //ArrayList<NazwaUsera> listaUsserow;


    public SerwerOdbior(Socket sock,ListaWatkow listaWatkow) throws IOException
    {
        this.localAddres = Integer.toString(sock.getPort())+":"+sock.getLocalAddress().getHostAddress()+"&";
        this.listaWatkow = listaWatkow;
        this.sock=sock;
        this.sockReader=new BufferedReader(new InputStreamReader(sock.getInputStream()));
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
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*trade&"+parts[3]);
                            listaWatkow.get(i).outp.flush();
                        }

                    }
                }else if (parts[1].equals("*tradeOdbior")) {
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*tradeOdbior&"+parts[3]);
                            listaWatkow.get(i).outp.flush();
                        }

                    }
                }else if (parts[1].equals("*accept")){
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*accept&");
                            listaWatkow.get(i).outp.flush();
                        }

                    }
                }else if (parts[1].equals("*zamien")){
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*zamien&");
                            listaWatkow.get(i).outp.flush();
                        }

                    }
                }else if (parts[1].equals("*ustawNazwe")){

                }else if (parts[1].equals("*zajety")){
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*zajety&");
                            listaWatkow.get(i).outp.flush();
                        }

                    }
                }else if (parts[1].equals("*cancel")){
                    for (int i=0;i<listaWatkow.size();i++) {
                        if (parts[2].equals(listaWatkow.get(i).sock.getPort() + ":" + listaWatkow.get(i).sock.getLocalAddress().getHostAddress())){
                            listaWatkow.get(i).outp.println(parts[0]+"&*cancel&");
                            listaWatkow.get(i).outp.flush();
                        }
                    }
                }
                else {
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

