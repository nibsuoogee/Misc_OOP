package com.example.bottledispenserapp;

import android.widget.TextView;


import java.util.ArrayList;

public class BottleDispenser {

    private int bottles;
    private ArrayList<Bottle> BottleArray = new ArrayList<>();
    private double money;
    private static BottleDispenser machine = null;
    private Bottle lastBottle;

    private BottleDispenser() {
        bottles = 60;
        double money = 0.0;

        for(int i = 0;i<bottles;i++) {
            Bottle temp = new Bottle();
            if(i<5) {
                temp.setName("Pepsi Max");
                temp.setSize(0.5);
                temp.setPrice(1.8);
            } else if(i<20) {
                temp.setName("Pepsi Max");
                temp.setSize(1.5);
                temp.setPrice(2.2);
            } else if(i<30) {
                temp.setName("Coca-Cola Zero");
                temp.setSize(0.5);
                temp.setPrice(2.0);
            } else if(i<40) {
                temp.setName("Coca-Cola Zero");
                temp.setSize(1.5);
                temp.setPrice(2.5);
            } else {
                temp.setName("Fanta Zero");
                temp.setSize(0.5);
                temp.setPrice(1.95);
            }
            BottleArray.add(temp);
        }

    }

    public static BottleDispenser getInstance(){
        if (machine == null) {
            machine = new BottleDispenser();
        }
        return machine;
    }

    public void addMoney(TextView tulosteet, int progress) {
        String progress1 = String.format("%,.2f",progress*0.05);
        money += Double.parseDouble(progress1);
        //money += Float.parseFloat(String.format("%,.2f€", progress*0.05));
        tulosteet.setText("Klink! Added more money!\n" + tulosteet.getText().toString());
    }

    private int index = 0;
    private int choice = 0;

    public int buyBottle(Bottle bottle) {
        if(money >= bottle.getPrice()) {
            money -= BottleArray.get(index).getPrice();
            return 1;
            //System.out.println("KACHUNK! " + BottleArray.get(index).getName() + " came out of the dispenser!");
        } else {
            //System.out.println("Add money first!");
            return 0;
        }
    }

    public int getBottle(String n, Double s) {
        for (i = 0; i<bottles; i++) {
            if ((BottleArray.get(i).getName().equals(n)) && (Double.compare(BottleArray.get(i).getSize(),s)) == 1) {
                if (buyBottle(BottleArray.get(i)) == 1) {
                    lastBottle = BottleArray.get(i);
                    for (int j = i; j<BottleArray.size()-2; j++) {
                        BottleArray.remove(j);
                        BottleArray.add(j, BottleArray.get(j+1));
                    }
                    BottleArray.subList(BottleArray.size()-1,BottleArray.size()).clear();
                    bottles -= 1;
                    return 1;
                } else { return 2; }
            }
        }
        return 0;
    }

    public double getMoney() {
        return money;
    }

    public String lastPurchase() {
        String tuloste = "Merkki: " + lastBottle.getName() + "\nHinta: " + lastBottle.getPrice() + "\nKoko: " + lastBottle.getSize() + "\n\nKiitos ostoksesta!";
        return tuloste;
    }

    public void returnMoney(TextView tulosteet) {
        tulosteet.setText(String.format("Klink klink. Money came out! You got %,.2f€ back",money) + tulosteet.getText().toString());
        money = 0;
    }

    int i = 0;
    int x = 0;
    public void listBottles(TextView tulosteet) {

        for(i=1;i<6;i++) {
            index = 10*i-10;
            tulosteet.setText(i + ". Name: " + BottleArray.get(index).getName() + "\n" + tulosteet.getText().toString());
            tulosteet.setText("\tSize: " + BottleArray.get(index).getSize() + "\tPrice: " + BottleArray.get(index).getPrice() + "\n" + tulosteet.getText().toString());
        }
    }
}
