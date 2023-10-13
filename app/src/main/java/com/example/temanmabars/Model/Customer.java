package com.example.temanmabars.Model;

public class Customer extends User{
    // Atribute
    private int coin;

    public Customer() {

    }

    public Customer(String id_user, int coin) {
        super(id_user);
        this.coin = coin;
    }

    public Customer(String id_user, String userType, String name, String username, String email, String gender, int coin) {
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
