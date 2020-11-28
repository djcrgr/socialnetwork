package com.getjavajob.training.karpovn.socialnetwork.common;

public class Phone {

    private int number;
    private String type;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number=" + number +
                ", type='" + type + '\'' +
                '}';
    }
}
