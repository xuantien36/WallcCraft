package com.t3h.wallccraft.model;

public class Phone {
    private int imgePhone;
    private int imLock;


    public Phone(int imgePhone,int imLock) {
        this.imgePhone = imgePhone;
        this.imLock=imLock;

    }

    public int getImgePhone() {
        return imgePhone;
    }

    public int getImLock() {
        return imLock;
    }
}

