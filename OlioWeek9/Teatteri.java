package com.example.viikko9;

public class Teatteri {
    private String Paikka;
    private String ID;

    public Teatteri(String paikka, String ID) {
        Paikka = paikka;
        this.ID = ID;
    }

    public String getPaikka() {
        return Paikka;
    }

    public void setPaikka(String paikka) {
        Paikka = paikka;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
