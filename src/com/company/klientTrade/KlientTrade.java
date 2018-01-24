package com.company.klientTrade;

import Items.Metale;
import User.Player;

import java.io.IOException;
import java.net.Socket;

public class KlientTrade {
    public static final int PORT=50007;
    public static final String HOST = "127.0.0.1";


    Player player;
    Socket sock;
    String localAddres;
    public WyslijTrade wyslijTrade;
    OdbiorTrade odbiorTrade;
    String zkim;

    Object metalOdebrany;
    Object metalWyslany;
    int istrade=0;
    int accept =0;
    String nick;

    public KlientTrade(Player player) throws IOException {
        this.player = player;
        Socket sock;
        sock=new Socket(HOST,PORT);
        System.out.println("Nawiazalem polaczenie: "+sock);
        wyslijTrade = new WyslijTrade(sock,this,nick);
        odbiorTrade = new OdbiorTrade(sock,this);
        wyslijTrade.start();
        odbiorTrade.start();
    }

    public void tradeWysylamy(Object metale, String userName) throws IOException {
        istrade=1;
        metalWyslany = metale;
        System.out.println("wysylam taki metal:"+metale.toString());
        wyslijTrade.wyslij("*trade&"+userName+"&"+metale.toString());
    }

    public void setMetalOdebrany(Object metalOdbior,String gracz){
        metalOdebrany = metalOdbior;
        zkim = gracz;
    }

    public void tradeOdbieramy(Object metalWyslij,String userName) throws IOException {
        istrade=1;
        metalWyslany = metalWyslij;
        System.out.println("Odebralem taki metal:"+metalOdebrany.toString());
        System.out.println("wysylam taki metal:"+metalWyslij.toString());
        wyslijTrade.wyslij("*tradeOdbior&"+userName+"&"+metalWyslij.toString());
    }

    public void tradeZakoncz(Object metale){
        System.out.println("Z zakonczenia odebralem taki:"+metale.toString());
        metalOdebrany = metale;
    }
    public void akcept(String userName){
        if (accept==1){
            wyslijTrade.wyslij("*zamien&"+userName);
            zamien();
        }else {
            wyslijTrade.wyslij("*accept&" + userName);
        }
    }
    public void cancel(String userName){
        if (istrade==1) {
            wyslijTrade.wyslij("*cancel&" + userName);
        }
        accept=0;
        istrade=0;
    }

    public void zamien(){
        System.out.println("zamieniam:"+metalOdebrany.toString() +"z :"+metalWyslany.toString());
        player.dodaj(metalOdebrany);
        player.usun(metalWyslany);
        accept=0;
        istrade=0;
    }

    public void zajety(String userName){
        wyslijTrade.wyslij("*zajety&"+userName);
    }

    public void ustawNick(String nickName){
        nick = nickName;
        wyslijTrade.setNick(nick);
        wyslijTrade.wyslijNick("*ustawNazwe&"+nickName);
    }

    public void sprawdzNicki(){
        wyslijTrade.wyslij("*podajNicki&");
    }

}
