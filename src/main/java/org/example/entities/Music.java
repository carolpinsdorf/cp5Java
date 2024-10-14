package org.example.entities;

import java.util.Optional;

public class Music extends _BaseEntity{
    private String title;
    private int duration; //in minutes
    private transient Album album;
 // transient = declara que o obejto pode entrar em referencia ciclica

    public Music(Integer musicID, String musicTitle, int duration, Optional<Album> albumForMusic) {

    }

    public Music(int id, String title, int duration, Album album) {
        super(id);
        this.title = title;
        this.duration = duration;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", album=" + album +
                "} " + super.toString();
    }
}
