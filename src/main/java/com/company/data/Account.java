package com.company.data;

public class Account {
    private String name;
    private String email;
    private String password;
    private int acessLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAcessLevel() {
        return acessLevel;
    }

    public void setAcessLevel(int acessLevel) {
        this.acessLevel = acessLevel;
    }
}
