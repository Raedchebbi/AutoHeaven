package models;

import java.time.LocalDateTime;

public class Messagerie {
    private int id_m; // id_m
    private String sender;
    private String message;
    private int id_rec; // id_rec
    private LocalDateTime dateMessage; // datemessage
    private String receiver;
    private int id_user; // id_user

    // Constructeur avec vérification de la date
    public Messagerie(int id_m, String sender, String message, int id_rec, LocalDateTime dateMessage, String receiver, int id_user) {
        this.id_m = id_m;
        this.sender = sender;
        this.message = message;
        this.id_rec = id_rec;
        this.dateMessage = (dateMessage != null) ? dateMessage : LocalDateTime.now(); // Vérification de nullité
        this.receiver = receiver;
        this.id_user = id_user;
    }

    // Getters et Setters
    public int getId_m() {
        return id_m;
    }

    public void setId_m(int id_m) {
        this.id_m = id_m;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public LocalDateTime getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(LocalDateTime dateMessage) {
        this.dateMessage = (dateMessage != null) ? dateMessage : LocalDateTime.now(); // Vérification de nullité
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}