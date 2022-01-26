package com.example.hotelrest.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.IOException;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String name;
    private int nbBeds;
    private int price;
    private String imgPath;
    private int hotelId;

    public Room() {
    }

    public Room(Long roomId, String name, int nbBeds, int price, String imgPath, int hotelId) {
        super();
        this.roomId = roomId;
        this.name = name;
        this.nbBeds = nbBeds;
        this.price = price;
        this.imgPath = imgPath;
        this.hotelId = hotelId;
    }

    public File getImg() throws IOException {

        File fichier = new File(this.imgPath);
        if (!fichier.exists()) {
            throw new IOException("File not exist : " + fichier.getAbsolutePath());
        }
        if (!fichier.canRead()) {
            throw new IOException("File cannot be read : " + fichier.getAbsolutePath());
        }

        return fichier;
    }

}
