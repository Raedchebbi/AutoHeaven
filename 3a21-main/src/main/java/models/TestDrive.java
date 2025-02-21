package models;

import java.util.Date;

public class TestDrive extends Reservation {
    private int id_v;

    public TestDrive(int id_res, int id, Date date_res, String type, int id_v) {
        super(id_res, id, date_res,"testdrive");
        this.id_v = id_v;
    }

    public TestDrive(int id, Date date_res, String type, int id_v) {
        super(id, date_res, "testdrive");
        this.id_v = id_v;
    }

    public int getId_v() {
        return id_v;
    }
    public void setId_v(int id_v) {
        this.id_v = id_v;
    }

    @Override
    public String toString() {
        return "TestDrive{" +
                "ID Voiture = " + id_v + '\'' +
                "} " + super.toString();
    }
}
