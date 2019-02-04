package com.everis.prj_appointmentbook_app.DataSource;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.everis.prj_appointmentbook_app.Model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts WHERE id IN(:userId)")
    List<Contact> getById(int[] userId);

    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getAll();

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);
}