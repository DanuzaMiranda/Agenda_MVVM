package com.everis.prj_appointmentbook_app.DataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.everis.prj_appointmentbook_app.Model.Contact;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    public abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,
                    "contact_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
