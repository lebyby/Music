package com.example.music.infogroup.composition.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.music.infogroup.composition.Composition;
import com.example.music.R;
import com.example.music.infogroup.composition.adapter.CompositionViewHolder;
import com.example.music.infogroup.composition.database.CompositionDatabase;

import java.util.ArrayList;

public class CompositionAdapter extends RecyclerView.Adapter<CompositionViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Composition> listComposition;
    private ArrayList<Composition> mArrayList;

    private CompositionDatabase mDatabase;

    public CompositionAdapter(Context context, ArrayList<Composition> listComposition) {
        this.context = context;
        this.listComposition = listComposition;
        this.mArrayList = listComposition;
        mDatabase = new CompositionDatabase(context);
    }

    @Override
    public CompositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.composition_list_layout, parent, false);
        return new CompositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompositionViewHolder holder, int position) {
        final Composition composition = listComposition.get(position);

        holder.surname.setText(composition.getSurname());
        holder.forename.setText(composition.getForename());
        holder.role.setText(composition.getRole());

        holder.editComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(composition);
            }
        });

        holder.deleteComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteComposition(composition.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listComposition = mArrayList;
                } else {

                    ArrayList<Composition> filteredList = new ArrayList<>();

                    for (Composition composition : mArrayList) {

                        if (composition.getSurname().toLowerCase().contains(charString)) {

                            filteredList.add(composition);
                        }
                    }

                    listComposition = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listComposition;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listComposition = (ArrayList<Composition>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listComposition.size();
    }


    private void editTaskDialog(final Composition composition){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_composition_layout, null);

        final EditText snameField = (EditText)subView.findViewById(R.id.enter_surname);
        final EditText fnameField = (EditText)subView.findViewById(R.id.enter_forename);
        final EditText roleField = (EditText)subView.findViewById(R.id.enter_role);

        if(composition != null){
            snameField.setText(composition.getSurname());
            fnameField.setText(composition.getForename());
            roleField.setText(composition.getRole());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Редактирование музыканта");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ИЗМЕНИТЬ МУЗЫКАНТА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String sname = snameField.getText().toString();
                final String fname = fnameField.getText().toString();
                final String role = roleField.getText().toString();

                if(TextUtils.isEmpty(sname)){
                    Toast.makeText(context, "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateComposition(new Composition(composition.getId(), composition.getG_Id(), sname, fname, role));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Отменено", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
