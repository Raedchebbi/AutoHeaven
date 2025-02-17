package org.example;

import models.Equipement;
import services.CommandeService;
import services.EquipementService;
import services.PanierService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        EquipementService es = new EquipementService();
        PanierService ps = new PanierService();
        CommandeService cs = new CommandeService();

        try {

            Equipement E1 = new Equipement(2,"chair" ,"chaise solide","img.png","sam-1235","sam");
            //es.create(E1,17,15);
            //System.out.println("User created");
            //es.update(E1,100,22);
            es.delete(3);
            //System.out.println(es.getAll());
            //Panier p = new Panier(6,3,4);

            // ps.create(p);
            //ps.delete(p);
            //ps.update(p);
            // System.out.println(ps.getAll(3));
            //cs.create(3);
            // System.out.println(ps.getAll(1));
            //System.out.println(es.rechercherEquipement("sam-1235"));




        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
