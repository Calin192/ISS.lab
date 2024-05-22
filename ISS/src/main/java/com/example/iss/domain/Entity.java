package com.example.iss.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    protected ID id;

    public Entity(){

    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}

