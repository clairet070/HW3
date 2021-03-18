package com.example.hw_1;

import java.util.ArrayList;

public class Burger {


    private ArrayList<String> elements;
    private int num;

    public Burger(){
        num = 1;
        elements = new ArrayList<String>();
    }

    public void addElement(String str){
        if (!elements.contains(str))
            elements.add(str);
    }

    public void removeElement(String str){
        if (elements.contains(str))
            elements.remove(str);
    }

    public void setNum(int n){
        num = n;
    }

    public ArrayList<String> getElements(){
        return elements;
    }

    public int getNum(){
        return num;
    }
}
