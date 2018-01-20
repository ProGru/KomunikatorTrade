package com.company;

import Items.Metale;
import User.Player;
import com.company.klientTrade.KlientTrade;

import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String args[]) throws IOException{
        Player player = new Player();
        Scanner odczyt = new Scanner(System.in);
        player.wyslij("adam");
        //String user = odczyt.nextLine();
        //player.trade(new Metale("aaa",1,1,1,1,1,1,1),user);
        String user = odczyt.nextLine();
        player.cancel(user);

    }
}
