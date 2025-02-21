package models;

public class ResTestDrive {
    private int id_res_td;
    private int id_res;
    private int id_v;

    public ResTestDrive() {}

    public ResTestDrive(int id_res_td, int id_res, int id_v) {
        this.id_res_td = id_res_td;
        this.id_res = id_res;
        this.id_v = id_v;
    }

    public int getId_res_td() { return id_res_td; }
    public void setId_res_td(int id_res_td) { this.id_res_td = id_res_td; }

    public int getId_res() { return id_res; }
    public void setId_res(int id_res) { this.id_res = id_res; }

    public int getId_v() { return id_v; }
    public void setId_v(int id_v) { this.id_v = id_v; }

    @Override
    public String toString() {
        return "ResTestDrive{id_res_td=" + id_res_td + ", id_res=" + id_res + ", id_v=" + id_v + "}";
    }
}