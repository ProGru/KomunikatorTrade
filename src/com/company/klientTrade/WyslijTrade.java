package com.company.klientTrade;

import java.io.*;
import java.net.*;

public class WyslijTrade extends Thread {
    Socket sock;
    BufferedReader sockReader;
    PrintWriter outp;
    String localAddres;
    KlientTrade klientTrade;
    String nick;


    public WyslijTrade(Socket sock, KlientTrade klientTrade,String nick) throws IOException {
        this.klientTrade = klientTrade;
        this.localAddres = Integer.toString(sock.getLocalPort()) + ":" + sock.getLocalAddress().getHostAddress() + "&";
        this.sock = sock;
        this.sockReader = new BufferedReader(new InputStreamReader(System.in));
        this.outp = new PrintWriter(sock.getOutputStream());
        this.nick = nick;
    }

    public void run() {

    }

    public void wyslij(String msg){
        outp.println(nick+"&"+msg);
        outp.flush();
    }

    public void wyslijNick(String msg){
        outp.println(localAddres+msg);
        outp.flush();
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

