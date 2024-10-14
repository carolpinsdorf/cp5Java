package org.example.entities;

import java.util.ArrayList;

public class Album extends _BaseEntity{
    private String name;
    private int yearLaunched;
    private transient Artist artist;
    // transient = declara que o obejto pode entrar em referencia ciclica
    private ArrayList<Music> musics;

    public Album() {

    }

    public Album(Integer id, String name, int yearLaunched, Artist artist, ArrayList<Music> musics) {
        super(id);
        this.name = name;
        this.yearLaunched = yearLaunched;
        this.artist = artist;
        this.musics = musics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearLaunched() {
        return yearLaunched;
    }

    public void setYearLaunched(int yearLaunched) {
        this.yearLaunched = yearLaunched;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", yearLaunched=" + yearLaunched +
                ", artist=" + artist +
                ", musics=" + musics +
                "} " + super.toString();
    }
}
