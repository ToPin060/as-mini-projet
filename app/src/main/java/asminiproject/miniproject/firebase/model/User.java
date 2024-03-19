package asminiproject.miniproject.firebase.model;

import androidx.annotation.NonNull;

import asminiproject.miniproject.dc.Address;

public class User {
    private String pseudo;
    private String name ;
    private String firstName;
    private String photoAddr;
    private Address address;

    public User() { }

    public User(String _pseudo, String _name, String _firstName, String _photoAddr, Address _address) {
        this.pseudo = _pseudo;
        this.name = _name;
        this.firstName = _firstName;
        this.photoAddr = _photoAddr;
        this.address = _address;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhotoAddr() {
        return photoAddr;
    }

    public void setPhotoAddr(String photoAddr) {
        this.photoAddr = photoAddr;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @NonNull
    @Override
    // Pas d'adresse ni de lien vers la photo de profil.
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("************ User ************\n");
        sb.append("Username : ");
        sb.append(this.getPseudo());
        sb.append("\nName and firstname : ");
        sb.append(this.getName());
        sb.append(" ");
        sb.append(this.getFirstName());
        sb.append("*******************************\n");

        return sb.toString();
    }
}
