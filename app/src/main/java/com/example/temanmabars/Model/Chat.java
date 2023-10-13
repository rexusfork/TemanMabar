package com.example.temanmabars.Model;

public class Chat {
    private String id_sender;
    private String id_receiver;
    private String message;
    private String time;
    private String date;

    public Chat(){

    }

    public Chat(String id_sender, String id_receiver, String message, String time, String date) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.message = message;
        this.time = time;
        this.date = date;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(String id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
