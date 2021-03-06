package com.everis.prj_appointmentbook_app.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.everis.prj_appointmentbook_app.Adapter.ContactAdapter;
import com.everis.prj_appointmentbook_app.Model.Contact;
import com.everis.prj_appointmentbook_app.R;
import com.everis.prj_appointmentbook_app.ViewModel.ContactViewModel;

import java.util.List;

public class ListFragment extends Fragment {

    private ContactViewModel contactViewModel;
    private RecyclerView rv_list;
    private Context context;
    private ContactAdapter adapter;

    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        initializeVariable(view);
        initializeAction();
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(Contact contact);
        public Contact getContactToEdit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException();
        }
    }

    private void initializeVariable(View view) {

        context = getContext();
        rv_list = view.findViewById(R.id.list_contact);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactAdapter(getContext());

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(Contact contact) {
                listener.onItemSelected(contact);
            }
        });

        contactViewModel = ViewModelProviders.of (this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                adapter.setListContacts(contacts);
                rv_list.setAdapter(adapter);

            }
        });

        //Swiped old
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
                 int dragFlags =  ItemTouchHelper.ACTION_STATE_IDLE;
                 int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                 return makeMovementFlags(dragFlags,swipeFlags);
            }

           @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
           }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                 deleteSwipe(viewHolder);
           }
        }).attachToRecyclerView(rv_list);
    }

    private void deleteSwipe(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(context);

        //Configurar o AlertDialog
        alertDialog.setTitle("Excluir Contado");
        alertDialog.setMessage("Você tem certeza que deseja excluir este contado da sua agenda?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Contato apagado!", Toast.LENGTH_SHORT).show();
                contactViewModel.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                contactViewModel.update(adapter.getContactAt(viewHolder.getAdapterPosition()));
            }
        });

        AlertDialog alert =alertDialog.create();
        alert.show();
    }

    private void initializeAction() {
        getActivity().findViewById(R.id.btn_add).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.tv_main).setVisibility(View.VISIBLE);
    }
}
