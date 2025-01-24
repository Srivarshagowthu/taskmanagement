package com.product.borg.model.remote;

public class AsgardUserPropertyMap {

    private int id;

    private City city;

    private Facility facility;

    private byte speakEnabled;

    private LocalityCluster localityCluster;

    private byte deleted;


    public AsgardUserPropertyMap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public byte getSpeakEnabled() {
        return speakEnabled;
    }

    public void setSpeakEnabled(byte speakEnabled) {
        this.speakEnabled = speakEnabled;
    }

    public LocalityCluster getLocalityCluster() {
        return localityCluster;
    }

    public void setLocalityCluster(LocalityCluster localityCluster) {
        this.localityCluster = localityCluster;
    }

}