package com.example.mike.week4daily1.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Person {

    private String name;
    private String gender;
    private String nationality;
    private String photoURL;
    public static HashMap<String, String> NATIONALITIES;

    static {
        NATIONALITIES = new HashMap<>();
        NATIONALITIES.put("-- all --", null);
        NATIONALITIES.put("australian", "AU");
        NATIONALITIES.put("brazilian", "BR");
        NATIONALITIES.put("canadian", "CA");
        NATIONALITIES.put("chinese", "CH");
        NATIONALITIES.put("germany", "DE");
        NATIONALITIES.put("denmark", "DK");
        NATIONALITIES.put("spain", "ES");
        NATIONALITIES.put("finland", "FI");
        NATIONALITIES.put("france", "FR");
        NATIONALITIES.put("great brittain", "GB");
        NATIONALITIES.put("ireland", "IE");
        NATIONALITIES.put("iran", "IR");
        NATIONALITIES.put("norway", "NO");
        NATIONALITIES.put("netherlands", "NL");
        NATIONALITIES.put("new zealand", "NZ");
        NATIONALITIES.put("turkey", "TR");
        NATIONALITIES.put("united states", "US");
    }

    public Person(String name, String gender, String nationality, String photoURL) {
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }

    public static String encodeNat( String nationality ){
        return NATIONALITIES.get(nationality.toLowerCase());
    }

    public static String decodeNat( String nat ){
        for (Map.Entry<String, String> entry : NATIONALITIES.entrySet()) {
            if (nat.toUpperCase().equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return "null";
    }

}
