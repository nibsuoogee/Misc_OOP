package com.example.bottledispenserapp;

public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private double size;
    private double price;

    public Bottle(){
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        total_energy = 0.3;
        size = 0.5;
        price = 1.80;
    }

    public Bottle(String name, String manuf, float totE, float size, float price){}
    public String getName() {return name;}
    public String getManufacturer() {return manufacturer;}
    public double getEnergy() {return total_energy;}
    public double getSize() {return size;}
    public double getPrice() {return price;}

    public void setName(String n) {
        name = n;
    }
    public void setSize(double d) {
        size = d;
    }
    public void setPrice(double d) {
        price = d;
    }
}
