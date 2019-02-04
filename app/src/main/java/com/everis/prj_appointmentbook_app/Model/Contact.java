package com.everis.prj_appointmentbook_app.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String lastName;

    public String cpf;

    public String cell;

    public Contact(String name, String lastName, String cpf, String cell) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.cell = cell;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCell() {
        return cell;
    }
}
