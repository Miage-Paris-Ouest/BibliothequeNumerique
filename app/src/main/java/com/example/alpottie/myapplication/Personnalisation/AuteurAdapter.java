package com.example.alpottie.myapplication.Personnalisation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alpottie.myapplication.Donnees.Auteur;
import com.example.alpottie.myapplication.R;

import java.util.List;

/**
 * Created by Alice on 19/12/2015.
 */
public class AuteurAdapter extends BaseAdapter {

    private List<Auteur> listeAuteur;
    private Context contexte;

    // gestion de l'affichage
    private LayoutInflater gestionAffichage;

    public AuteurAdapter(Context contexte, List<Auteur> liste) {
        this.contexte = contexte;
        this.listeAuteur = liste;
        this.gestionAffichage = LayoutInflater.from(this.contexte);
    }

    @Override
    public int getCount() {
        return listeAuteur.size();
    }

    @Override
    public Object getItem(int position) {
        return listeAuteur.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;

        //region Création du layout en fonction du notre (layout_contact.xml)
        if (convertView == null)
        {
            layoutItem = (RelativeLayout) gestionAffichage.inflate(R.layout.layout_auteur, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }
        //endregion

        //region Recup des champs
        TextView tvNom = (TextView)layoutItem.findViewById(R.id.tvNom);
        TextView tvPrenom = (TextView)layoutItem.findViewById(R.id.tvPrenom);
        //endregion

        //region Modification des champs
        tvNom.setText(listeAuteur.get(position).getNom());
        tvPrenom.setText(listeAuteur.get(position).getPrenom());
        //endregion

        return layoutItem;
    }
}
