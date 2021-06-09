package com.example.authorization;

public class Doctor extends Person{
    private String name;
    private String proffession;
    private int price;
    private int staj;
    private String phone;

    public Doctor(String name, String proffession, int price, int staj, String phone){
        this.name = name;
        this.proffession = proffession;
        this.price= price;
        this.staj = staj;
        this.phone = phone;
    }

    public String getProffession(){
        return proffession;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public int getStaj() {
        return staj;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStaj(int staj) {
        this.staj = staj;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}