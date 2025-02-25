package models;

public class ResTestDrive {
    private int id_td;  // Auto-incremented in the database
    private int id_u;
    private int id_v;
    private String date;
    private String status;

    public ResTestDrive() {}

    public ResTestDrive(int id_td, int id_u, int id_v, String date, String status) {
        this.id_td = id_td;
        this.id_u = id_u;
        this.id_v = id_v;
        this.date = date;
        this.status = status;
    }

    public int getId_td() { return id_td; }

    public int getId_u() { return id_u; }
    public void setId_u(int id_u) { this.id_u = id_u; }

    public int getId_v() { return id_v; }
    public void setId_v(int id_v) { this.id_v = id_v; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ResTestDrive{ID TestDrive = " + id_td + ", ID User =" + id_u + ", ID Voiture = " + id_v +
                ", Date = '" + date + "', Status = '" + status + "'}";
    }
}
