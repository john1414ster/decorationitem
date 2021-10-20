package com.mercadolibre;

public class Location {

    public String address_line;
    public String zip_code;
    public String subneighborhood;
    public Neighborhood neighborhood;
    public City city;
    public State state;
    public Country country;
    public float latitude;
    public float longitude;
}

class Neighborhood {
    public String id;
    public String name;
}
