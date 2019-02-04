package com.everis.prj_appointmentbook_app.DataSource;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.everis.prj_appointmentbook_app.Model.Contact;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application){
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        contactDao = contactDatabase.contactDao();
        allContacts = contactDao.getAll();
    }

    public void insert(Contact contact){

        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void update(Contact contact){

        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void delete(Contact contact){

        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {

        return allContacts;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao contactDao;

        private InsertContactAsyncTask(ContactDao contactDao){

            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        private ContactDao contactDao;

        private UpdateContactAsyncTask(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        private ContactDao contactDao;

        private DeleteContactAsyncTask(ContactDao contactDao){

            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }
    }
}
