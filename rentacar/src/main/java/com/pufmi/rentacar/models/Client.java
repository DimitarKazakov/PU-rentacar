package com.pufmi.rentacar.models;

public class Client {
    private int id;
    private String name;
    private String address;
    private String phone;
    private int age;
    private boolean hasIncidents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getHasIncidents() {
        return hasIncidents;
    }

    public void setHasIncidents(boolean hasIncidents) {
        this.hasIncidents = hasIncidents;
    }
}
