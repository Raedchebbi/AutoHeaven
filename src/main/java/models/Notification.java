package models;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private String message ;
    private LocalDateTime date_notif;
    private int ide ;
   private  String etat ;
    public Notification() {}

    public Notification(int id, String message, LocalDateTime date_notif, int ide , String etat) {
        this.id = id;
        this.message = message;
        this.date_notif = date_notif;
        this.ide = ide;
        this.etat = etat;

    }
    public Notification(String message, LocalDateTime date_notif, int ide , String etat) {

        this.message = message;
        this.date_notif = date_notif;
        this.ide = ide;
        this.etat = etat;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDate_notif() {
        return date_notif;
    }
    public void setDate_notif(LocalDateTime date_notif) {
        this.date_notif = date_notif;
    }
    public int getIde() {
        return ide;
    }
    public void setIde(int ide) {
        this.ide = ide;
    }
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date_notif=" + date_notif +
                ", ide=" + ide +
                ", etat='" + etat + '\'' +
                '}';
    }
}
