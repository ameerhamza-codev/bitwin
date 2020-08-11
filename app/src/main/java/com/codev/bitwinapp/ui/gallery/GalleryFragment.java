package com.codev.bitwinapp.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.codev.bitwinapp.MainActivity;
import com.codev.bitwinapp.R;
import com.codev.bitwinapp.databinding.FragmentGalleryBinding;
import com.codev.bitwinapp.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucasurbas.listitemview.ListItemView;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    FloatingActionButton btnEditContact, btnEditName;
    ListItemView name, emgContact, disContact, fireContact, medContact, polContact, autoContact;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyContact" ;
    public static final String eContact = "Emergency";
    public static final String dContact = "Disaster";
    public static final String fContact = "Fire";
    public static final String mContact = "Medical";
    public static final String pContact = "Police";
    public static final String fwdContact = "Forward";
    public static final String nameSP = "Name";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bindVariables(root);
        fetchData();

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditContactDialogue(view);
            }
        });

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditNameDialogue(view);
            }
        });


        return root;
    }

    private void showEditNameDialogue(View view) {
        LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
        View view1 = inflater1.inflate(R.layout.edit_username_layout,binding.getRoot());

        AppCompatEditText editText = (AppCompatEditText) view1.findViewById(R.id.editname);
        Button btnUpdate = (Button) view1.findViewById(R.id.btnUpdateName);
        Button btnClose = (Button) view1.findViewById(R.id.btnCloseName);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().length() > 0) {
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Name", editText.getText().toString());
                    editor.apply();

                    Toast.makeText(view.getContext(), "Name Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);

                }else {
                    Toast.makeText(view.getContext(), "Please Enter Name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view1.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void showEditContactDialogue(View view) {

        LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
        View view1 = inflater1.inflate(R.layout.edit_contact_layout,binding.getRoot());

        Spinner spinner = (Spinner)view1.findViewById(R.id.dropdown);
        AppCompatEditText editText = (AppCompatEditText) view1.findViewById(R.id.editContact);
        Button btnUpdate = (Button) view1.findViewById(R.id.btnUpdate);
        Button btnClose = (Button) view1.findViewById(R.id.btnClose);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().length() > 0) {
                    sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(String.valueOf(spinner.getSelectedItem()), editText.getText().toString());
                    editor.apply();

                    Toast.makeText(view.getContext(), "Contact Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view1.getContext(), MainActivity.class);
                    startActivity(i);

                }else {
                    Toast.makeText(view.getContext(), "Please Enter Contact Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view1.getContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }

    private void fetchData() {
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        emgContact.setSubtitle(sharedpreferences.getString(eContact+"","00000000000"));
        disContact.setSubtitle(sharedpreferences.getString(dContact+"","00000000000"));
        fireContact.setSubtitle(sharedpreferences.getString(fContact+"","00000000000"));
        medContact.setSubtitle(sharedpreferences.getString(mContact+"","00000000000"));
        polContact.setSubtitle(sharedpreferences.getString(pContact+"","00000000000"));
        autoContact.setSubtitle(sharedpreferences.getString(fwdContact+"","00000000000"));
        name.setSubtitle(sharedpreferences.getString(nameSP+"","John Doe"));

    }


    private void bindVariables(View root) {
        emgContact= (ListItemView) root.findViewById(R.id.emergency_contact);
        fireContact= (ListItemView) root.findViewById(R.id.fire_contact);
        disContact= (ListItemView) root.findViewById(R.id.disaster_contact);
        medContact=(ListItemView) root.findViewById(R.id.medical_contact);
        polContact=(ListItemView) root.findViewById(R.id.police_contact);
        autoContact=(ListItemView) root.findViewById(R.id.forward_contact);
        name=(ListItemView) root.findViewById(R.id.username);


        btnEditContact=(FloatingActionButton) root.findViewById(R.id.floating_action_button_edit);
        btnEditName=(FloatingActionButton) root.findViewById(R.id.floating_action_button_edit_name);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}