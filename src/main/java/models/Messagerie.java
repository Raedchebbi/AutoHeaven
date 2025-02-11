package models;

import java.time.LocalDateTime;

public class Messagerie {
    private int id_m, id_rec;
    private String sender, receiver;
    private String message;
    private LocalDateTime datemessage;

    public Messagerie() {
    }

    public Messagerie(String sender, String message, String receiver, LocalDateTime datemessage, int id_rec) {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        this.datemessage = datemessage;
        this.id_rec = id_rec;
    }

    public Messagerie(int id_m, String sender, String message, String receiver, LocalDateTime datemessage, int id_rec) {
        this.id_m = id_m;
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
        this.datemessage = datemessage;
        this.id_rec = id_rec;
    }

    public int getId_m() { return id_m; }
    public void setId_m(int id_m) { this.id_m = id_m; }

    public int getId_rec() { return id_rec; }
    public void setId_rec(int id_rec) { this.id_rec = id_rec; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public LocalDateTime getDatemessage() { return datemessage; }
    public void setDatemessage(LocalDateTime datemessage) { this.datemessage = datemessage; }

    @Override
    public String toString() {
        return "Messagerie{" +
                "id_m=" + id_m +
                ", id_rec=" + id_rec +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", receiver='" + receiver + '\'' +
                ", datemessage=" + datemessage +
                '}';
    }
}
