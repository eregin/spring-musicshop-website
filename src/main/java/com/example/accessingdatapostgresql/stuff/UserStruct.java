package com.example.accessingdatapostgresql.stuff;

import org.springframework.stereotype.Component;

@Component
public class UserStruct {

    private String name;

    private String password;

    public UserStruct() {
    }

    public UserStruct(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
