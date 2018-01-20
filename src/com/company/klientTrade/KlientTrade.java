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

    Metale metalOdebrany;
    Metale metalWyslany;
    int accept =0;

    public KlientTrade(Player player) throws IOException {
        this.player = player;
        Socket sock;
        sock=new Socket(HOST,PORT);
        System.out.println("Nawiazalem polaczenie: "+sock);
        wyslijTrade = new WyslijTrade(sock,this);
        odbiorTrade = new OdbiorTrade(sock,this);
        wyslijTrade.start();
        odbiorTrade.start();
    }

    public void tradeWysylamy(Metale metale, String userName) throws IOException {
        metalWyslany = metale;
        System.out.println("wysylam taki metal:"+metale.toString());
        wyslijTrade.wyslij("*trade&"+userName+"&"+metale.toString());
    }


    public void tradeOdbieramy(Metale metalOdbior,Metale metalWyslij,String userName) throws IOException {
        metalOdebrany = metalOdbior;
        metalWyslany = metalWyslij;
        System.out.println("Odebralem taki metal:"+metalOdbior.toString());
        System.out.println("wysylam taki metal:"+metalWyslij.toString());
        wyslijTrade.wyslij("*tradeOdbior&"+userName+"&"+metalWyslij.toString());
    }

    public void tradeZakoncz(Metale metale){
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

    public void zamien(){
        System.out.println("zamieniam:"+metalOdebrany.toString() +"z :"+metalWyslany.toString());
        player.dodaj(metalOdebrany);
        player.usun(metalWyslany);
        accept=0;
    }

}
