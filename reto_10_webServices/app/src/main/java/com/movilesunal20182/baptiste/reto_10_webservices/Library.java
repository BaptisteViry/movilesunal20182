package com.movilesunal20182.baptiste.reto_10_webservices;

public class Library {

    private String code,department,city,type,name,address,district,phone,web,geo;

    public Library(String code, String department, String city, String type, String name, String address, String district, String phone, String web, String geo) {
        this.code = code;
        this.department = department;
        this.city = city;
        this.type = type;
        this.name = name;
        this.address = address;
        this.district = district;
        this.phone = phone;
        this.web = web;
        this.geo = geo;
    }

    public Library() {
    }

    public String getCode() {
        return code;
    }

    public String getDepartment() {
        return department;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeb() {
        return web;
    }

    public String getGeo() {
        return geo;
    }
}
