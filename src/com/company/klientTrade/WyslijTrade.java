package com.company.klientTrade;

import java.io.*;
import java.net.*;

public class WyslijTrade extends Thread {
    Socket sock;
    BufferedReader sockReader;
    PrintWriter outp;
    String localAddres;
    KlientTrade klientTrade;


    public WyslijTrade(Socket sock, KlientTrade klientTrade) throws IOException {
        this.klientTrade = klientTrade;
        this.localAddres = Integer.toString(sock.getLocalPort()) + ":" + sock.getLocalAddress().getHostAddress() + "&";
        this.sock = sock;
        this.sockReader = new BufferedReader(new InputStreamReader(System.in));
        this.outp = new PrintWriter(sock.getOutputStream());
    }

    public void run() {

    }

    public void wyslij(String msg){
        outp.println(localAddres+msg);
        outp.flush();
    }
}

