package models;

import java.time.LocalDateTime;

public class Messagerie {
    private int id_m, id_rec, id_user;
    private String sender, receiver;
    private String message;
    private LocalDateTime datemessage;

    // Constructeur par défaut
    public Messagerie() {}

    // Constructeur avec tous les attributs sauf id_m
    public Messagerie(String sender, String message, String receiver, LocalDateTime datemessage, int id_rec, int id_user) {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        this.datemessage = datemessage;
        this.id_rec = id_rec;
        this.id_user = id_user;
    }

    // Constructeur avec tous les attributs
    public Messagerie(int id_m, String sender, String message, String receiver, LocalDateTime datemessage, int id_rec, int id_user) {
        this.id_m = id_m;
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        this.datemessage = datemessage;
        this.id_rec = id_rec;
        this.id_user = id_user;
    }

    public Messagerie(int idM, String sender, String message, String receiver, LocalDateTime datemessage, int idRec) {
    }

    // Getters et setters
    public int getId_m() { return id_m; }
    public void setId_m(int id_m) { this.id_m = id_m; }

    public int getId_rec() { return id_rec; }
    public void setId_rec(int id_rec) { this.id_rec = id_rec; }

    public int getId_user() { return id_user; }
    public void setId_user(int id_user) { this.id_user = id_user; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public LocalDateTime getDatemessage() { return datemessage; }
    public void setDatemessage(LocalDateTime datemessage) { this.datemessage = datemessage; }

    // Méthode toString() améliorée
    @Override
    public String toString() {
        return String.format(
                "Messagerie {id_m=%d, id_rec=%d, sender='%s', message='%s', receiver='%s', datemessage=%s, id_user=%d}",
                id_m, id_rec, sender, message, receiver, datemessage, id_user);
    }
}
