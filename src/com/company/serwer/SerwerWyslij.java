package com.company.serwer;

import com.company.serwer.ListaWatkow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class SerwerWyslij extends Thread
{
    Socket sock;
    BufferedReader sockReader;
    PrintWriter outp;
    String localAddres;
    ListaWatkow listaWatkow;


    public SerwerWyslij(Socket sock) throws IOException
    {
        this.localAddres = Integer.toString(sock.getLocalPort())+":"+sock.getLocalAddress().getHostAddress()+"&";
        this.sock=sock;
        this.sockReader=new BufferedReader(new InputStreamReader(System.in));
        this.outp = new PrintWriter(sock.getOutputStream());
        this.listaWatkow = listaWatkow;
    }

    public void run() {

    }

}
