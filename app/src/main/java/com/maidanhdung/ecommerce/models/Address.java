package com.maidanhdung.ecommerce.models;

public class Address {
    String Name, Province, SubDistrict, District, StreetAddress;
    int Phone;
    public Address(){}

    public Address(String name, String province, String subDistrict, String district, String streetAddress, int phone) {
        Name = name;
        Province = province;
        SubDistrict = subDistrict;
        District = district;
        StreetAddress = streetAddress;
        Phone = phone;
    }

    public String getSubDistrict() {
        return SubDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        SubDistrict = subDistrict;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }


    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }
}
