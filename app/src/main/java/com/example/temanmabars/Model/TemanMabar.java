package com.example.temanmabars.Model;

public class TemanMabar extends User{
    private int coin;

    public TemanMabar() {

    }

    public TemanMabar(String id_user, String userType, String name, String username, String email, String gender, int coin) {
        super(id_user, userType, name, username, email, gender);
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
