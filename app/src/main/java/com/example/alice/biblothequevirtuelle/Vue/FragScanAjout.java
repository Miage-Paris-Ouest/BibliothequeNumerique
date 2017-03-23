package com.example.alice.biblothequevirtuelle.Vue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alice.biblothequevirtuelle.R;

// Cette classe correspond au fragment qui s'affiche au moment où l'utilisateur vérifie si il a déjà un livre ou non
public class FragScanAjout extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }
}
