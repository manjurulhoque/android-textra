package com.manjurulhoque.textra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable {
    public long id;
    public String name;
    public String number;
    public String photoUri = "";
    public List<String> allPhoneNumber = new ArrayList<>();

    public Contact() {
    }

    public Contact(long id, String name, String number, String photoUri, List<String> allPhoneNumber) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photoUri = photoUri;
        this.allPhoneNumber = allPhoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public List<String> getAllPhoneNumber() {
        return allPhoneNumber;
    }

    public void setAllPhoneNumber(List<String> allPhoneNumber) {
        this.allPhoneNumber = allPhoneNumber;
    }
}
