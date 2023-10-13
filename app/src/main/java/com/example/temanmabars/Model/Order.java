package com.example.temanmabars.Model;

public class Order {
    private String id_user;
    private String id_temanmabar;
    private String categorygame;
    private int total_harga;
    private int total_match;

    private String time;
    private String date;
    private String status;

    public Order(){

    }

    public Order(String id_user, String id_temanmabar, String categorygame, int total_harga, int total_match, String time, String date, String status) {
        this.id_user = id_user;
        this.id_temanmabar = id_temanmabar;
        this.categorygame = categorygame;
        this.total_harga = total_harga;
        this.total_match = total_match;
        this.time = time;
        this.date = date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_temanmabar() {
        return id_temanmabar;
    }

    public void setId_temanmabar(String id_temanmabar) {
        this.id_temanmabar = id_temanmabar;
    }

    public String getCategorygame() {
        return categorygame;
    }

    public void setCategorygame(String categorygame) {
        this.categorygame = categorygame;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public int getTotal_match() {
        return total_match;
    }

    public void setTotal_match(int total_match) {
        this.total_match = total_match;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
