package models;

public class ResMecanicien {
    private int id_res_m;
    private int id_res;
    private int id_mec;
    private String note;

    public ResMecanicien() {}

    public ResMecanicien(int id_res_m, int id_res, int id_mec, String note) {
        this.id_res_m = id_res_m;
        this.id_res = id_res;
        this.id_mec = id_mec;
        this.note = note;
    }

    public int getId_res_m() { return id_res_m; }
    public void setId_res_m(int id_res_m) { this.id_res_m = id_res_m; }

    public int getId_res() { return id_res; }
    public void setId_res(int id_res) { this.id_res = id_res; }

    public int getId_mec() { return id_mec; }
    public void setId_mec(int id_mec) { this.id_mec = id_mec; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "ResMecanicien{id_res_m=" + id_res_m + ", id_res=" + id_res + ", id_mec=" + id_mec + ", note='" + note + "'}";
    }
}