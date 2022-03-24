package com.example.viikko9;

import java.util.ArrayList;

public class Teatterit {

    private static Teatterit teatterit = null;
    public ArrayList<Teatteri> teatteriArray = new ArrayList<>();

    private Teatterit() {

    }

    public String getID(String name) {
        for (Teatteri teatteri: teatteriArray) {
            if (teatteri.getPaikka().toString().equals(name)) {
                return teatteri.getID();
            }
        }
        return null;
    }

    public static Teatterit getInstance() {
        if (teatterit == null) {
            teatterit = new Teatterit();
        }
        return teatterit;
    }

    public void lisaaTeatteri(String paikka, String ID) {
        teatteriArray.add(new Teatteri(paikka, ID));
    }
}
