package com.everis.prj_appointmentbook_app.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everis.prj_appointmentbook_app.Model.Contact;
import com.everis.prj_appointmentbook_app.R;
import com.everis.prj_appointmentbook_app.ViewModel.ContactViewModel;

public class EditFragment extends Fragment {

    private com.everis.prj_appointmentbook_app.View.ListFragment.OnItemSelectedListener listener;

    private EditText et_name, et_lastName, et_cpf, et_cell;
    private Button btn_add, btn_cancel;
    private ContactViewModel contactViewModel;
    private Contact contactEdit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariable(view);
        initializeAction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof com.everis.prj_appointmentbook_app.View.ListFragment.OnItemSelectedListener) {
            listener = (com.everis.prj_appointmentbook_app.View.ListFragment.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException();
        }
    }

    private void initializeVariable(View view) {
        et_name = view.findViewById(R.id.et_name);
        et_lastName = view.findViewById(R.id.et_lastName);
        et_cpf = view.findViewById(R.id.et_cpf);
        et_cell = view.findViewById(R.id.et_cell);
        btn_add = view.findViewById(R.id.btn_register);
        contactEdit = listener.getContactToEdit();
        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
    }

    private void initializeAction() {
        getActivity().findViewById(R.id.btn_add).setVisibility(View.GONE);
        getActivity().findViewById(R.id.tv_main).setVisibility(View.GONE);

        if (contactEdit != null) {
            et_name.setText(contactEdit.getName());
            et_lastName.setText(contactEdit.getLastName());
            et_cpf.setText(contactEdit.getCpf());
            et_cell.setText(contactEdit.getCell());
        }

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String lastName = et_lastName.getText().toString().trim();
                String cpf = et_cpf.getText().toString().trim();
                String cell = et_cell.getText().toString().trim();

                if(name.isEmpty() || lastName.isEmpty() || cpf.isEmpty() || cell.isEmpty()){
                    Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                Contact contact = new Contact(name,lastName,cpf,cell);

                if (contactEdit != null) {
                    contact.setId(contactEdit.getId());
                    contactViewModel.update(contact);
                    Toast.makeText(getContext(), "Contato editado com sucesso!", Toast.LENGTH_SHORT).show();

                } else {
                    contactViewModel.insert(contact);
                    Toast.makeText(getContext(), "Contato adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, new com.everis.prj_appointmentbook_app.View.ListFragment());
                ft.commit();
            }

        });

    }
}
