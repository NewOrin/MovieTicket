package com.etc.movieticket.entity;

public class Cinema {
    private Integer c_id;
    private String c_name;
    private String c_address;
    private String c_phone;
    private String c_webid;
    private String c_placechar;
    private String c_price;


    public final Integer getC_id() {
        return c_id;
    }

    public final void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public final String getC_name() {
        return c_name;
    }

    public final void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public final String getC_address() {
        return c_address;
    }

    public final void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public final String getC_phone() {
        return c_phone;
    }

    public final void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public final String getC_webid() {
        return c_webid;
    }

    public final void setC_webid(String c_webid) {
        this.c_webid = c_webid;
    }

    public final String getC_placechar() {
        return c_placechar;
    }

    public final void setC_placechar(String c_placechar) {
        this.c_placechar = c_placechar;
    }

    public String getC_price() {
        return c_price;
    }

    public void setC_price(String c_price) {
        this.c_price = c_price;
    }

    public Cinema() {
        super();
    }

    public Cinema(String c_name, String c_address, String c_webid, String c_placechar) {
        super();
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_webid = c_webid;
        this.c_placechar = c_placechar;
    }

    public Cinema(String c_name, String c_address, String c_webid, String c_placechar, String c_price) {
        super();
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_webid = c_webid;
        this.c_placechar = c_placechar;
        this.c_price = c_price;
    }
}
