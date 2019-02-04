package com.everis.prj_appointmentbook_app.View;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.everis.prj_appointmentbook_app.Model.Contact;
import com.everis.prj_appointmentbook_app.R;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnItemSelectedListener{

    private FloatingActionButton btn_add;
    private Contact contactToEdit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariable();
        initializeAction();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, new com.everis.prj_appointmentbook_app.View.ListFragment());
        ft.commit();
    }

    private void initializeVariable() {

        btn_add = findViewById(R.id.btn_add);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_content, new ListFragment());
        ft.commit();

    }

    private void initializeAction() {

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactToEdit = null;

                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentById(R.id.fragment_content);
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                ft.commit();

                FragmentTransaction fragT = fm.beginTransaction();
                fragT.add(R.id.fragment_content, new EditFragment());
                fragT.commit();
            }
        });
    }

    @Override
    public void onItemSelected(Contact contact) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_content, new EditFragment());
        ft.commit();

        contactToEdit = contact;
    }

    @Override
    public Contact getContactToEdit() {
        return contactToEdit;
    }
}

