package org.example.entities;

import java.util.ArrayList;

public class Artist extends _BaseEntity {
    private String name;
    private String genre;
    private ArrayList<Album> albums;

    public Artist() {

    }

    public Artist(Integer id, String name, String musicalGenre, ArrayList<Album> albums) {
        super(id);
        this.name = name;
        genre = musicalGenre;
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setMusicalGenre(String musicalGenre) {
        genre = genre;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", albums=" + albums +
                "} " + super.toString();
    }
}
