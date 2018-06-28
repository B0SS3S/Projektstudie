package fhwedel.projektstudie;

import java.io.Serializable;

public class Note implements Serializable {

    private int noteId;
    private String noteRestaurant;
    private String noteMenu;
    private double noteLatitude;
    private double noteLongitude;

    public Note() {

    }

    public Note(String noteRestaurant, String noteMenu, double noteLatitude, double noteLongitude) {
        this.noteRestaurant = noteRestaurant;
        this.noteMenu = noteMenu;
        this.noteLatitude = noteLatitude;
        this.noteLongitude = noteLongitude;
    }

    public Note(int noteId, String noteRestaurant, String noteMenu, double noteLatitude, double noteLongitude) {
        this.noteId = noteId;
        this.noteRestaurant = noteRestaurant;
        this.noteMenu = noteMenu;
        this.noteLatitude = noteLatitude;
        this.noteLongitude = noteLongitude;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteRestaurant() {
        return noteRestaurant;
    }

    public void setNoteRestaurant(String noteRestaurant) {
        this.noteRestaurant = noteRestaurant;
    }


    public String getNoteMenu() {
        return noteMenu;
    }

    public void setNoteMenu(String noteMenu) {
        this.noteMenu = noteMenu;
    }

    public double getNoteLatitude() {
        return noteLatitude;
    }

    public void setNoteLatitude(double noteLatitude) {
        this.noteLatitude = noteLatitude;
    }

    public double getNoteLongitude() {
        return noteLongitude;
    }

    public void setNoteLongitude(double noteLongitude) {
        this.noteLongitude = noteLongitude;
    }

    @Override
    public String toString() {
        return this.noteRestaurant;
    }

}