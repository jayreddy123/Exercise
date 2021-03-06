package com.jayfaunadb.Exercise;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Address {
    static final Field<Address> ADDRESS_FIELD = Field.at("data").to(Address.class);

    @FaunaField private String street;
    @FaunaField private String city;
    @FaunaField private String region;
    @FaunaField private String postalCode;
    @FaunaField private String country;
    @FaunaField private String phone;

    @FaunaConstructor
    public Address(@FaunaField("street") String street,
                    @FaunaField("city") String city,
                    @FaunaField("region") String region,
                    @FaunaField("postalCode") String postalCode,
                    @FaunaField("country") String country,
                    @FaunaField("phone") String phone) {
        this.street = street;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.phone  = phone;
    }

    public Address () {
        this.street = "";
        this.city = "";
        this.region = "";
        this.postalCode = "";
        this.country = "";
        this.phone  = "";
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}