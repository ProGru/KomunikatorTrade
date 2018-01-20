package com.company.serwer;

import com.company.serwer.SerwerWyslij;

import java.util.ArrayList;

public class ListaWatkow {
    public ArrayList<SerwerWyslij> listaWatkow = new ArrayList<SerwerWyslij>();

    void add(SerwerWyslij wyslij){
        listaWatkow.add(wyslij);
    }


    public SerwerWyslij get(int i){
        return listaWatkow.get(i);
    }

    public int size(){
        return listaWatkow.size();
    }
}
