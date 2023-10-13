package com.example.temanmabars.Model;

public class Tebargame {
    private String id_user;
    private String id_tebargame;
    private String username;
    private String categorygame;
    private int biaya_coin;
    private String bio;

    public Tebargame(){

    }

    public Tebargame(String id_user, String id_tebargame, String username, String categorygame, int biaya_coin, String bio) {
        this.id_user = id_user;
        this.id_tebargame = id_tebargame;
        this.username = username;
        this.categorygame = categorygame;
        this.biaya_coin = biaya_coin;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId_tebargame() {
        return id_tebargame;
    }

    public void setId_tebargame(String id_tebargame) {
        this.id_tebargame = id_tebargame;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getCategorygame() {
        return categorygame;
    }

    public void setCategorygame(String categorygame) {
        this.categorygame = categorygame;
    }

    public int getBiaya_coin() {
        return biaya_coin;
    }

    public void setBiaya_coin(int biaya_coin) {
        this.biaya_coin = biaya_coin;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
