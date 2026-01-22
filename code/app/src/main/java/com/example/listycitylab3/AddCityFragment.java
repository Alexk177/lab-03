package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
//diagolfRagment used for both adding and editing cities

public class AddCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city);   //new for editing
    }

    private AddCityDialogListener listener;

    //if city is null add node
    //if city is not null and city is provided edit node
    public static AddCityFragment newInstance(@Nullable City city) {
        AddCityFragment fragment = new AddCityFragment();
        if (city != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_CITY, city);
            fragment.setArguments(args);
        }
        return fragment;
    }
//called when fragment is attached to activity
    //checks if activity implements AddCityDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        City cityToEdit = null; //check if edit mode
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable(ARG_CITY);
        }

        boolean isEditMode = (cityToEdit != null);

        // pre fill fields if editing
        if (isEditMode) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        City finalCityToEdit = cityToEdit;
        return builder
                .setView(view)
                .setTitle(isEditMode ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEditMode ? "Save" : "Add", (dialog, which) -> {

                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();

                    if (isEditMode) {
                        //update existing object
                        finalCityToEdit.setName(cityName);
                        finalCityToEdit.setProvince(provinceName);
                        listener.editCity(finalCityToEdit);
                    } else {
                        listener.addCity(new City(cityName, provinceName)); //creat and add new city
                    }
                })
                .create();
    }
}
