package com.model;

public class Doctor {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String password;

    public Doctor(int id, String name, String email, String phone, String specialization, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.password = password;
    }

    public Doctor() {
    }



    public Doctor(int id, String name, String specialization, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

