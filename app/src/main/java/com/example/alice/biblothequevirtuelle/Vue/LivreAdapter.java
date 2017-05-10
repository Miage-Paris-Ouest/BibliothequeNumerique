package com.example.alice.biblothequevirtuelle.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.alice.biblothequevirtuelle.Realm.Livre;

import java.util.ArrayList;

/**
 * Created by alice on 10/05/2017.
 */

public class LivreAdapter extends BaseAdapter {

    private ArrayList<Livre> listeLivre;
    private Context contexte;

    // gestion de l'affichage
    private LayoutInflater gestionAffichage;

    public LivreAdapter(Context contexte, ArrayList<Livre> liste) {
        this.contexte = contexte;
        this.listeLivre = liste;
        this.gestionAffichage = LayoutInflater.from(this.contexte);
    }

    @Override
    public int getCount() {
        return listeLivre.size();
    }

    @Override
    public Object getItem(int position) {
        return listeLivre.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem = null;

        /*//region Création du layout en fonction du notre (layout_contact.xml)
        if (convertView == null)
        {
            layoutItem = (RelativeLayout) gestionAffichage.inflate(R.layout.layout_contact, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }
        //endregion

        //region Recup des champs
        TextView tvNom = (TextView)layoutItem.findViewById(R.id.tvNom);
        TextView tvPrenom = (TextView)layoutItem.findViewById(R.id.tvPrenom);
        TextView tvTel = (TextView)layoutItem.findViewById(R.id.tvTel);
        TextView tvId = (TextView) layoutItem.findViewById(R.id.tvId);
        //endregion

        //region Modification des champs
        tvNom.setText(listeLivre.get(position).getNom());
        tvPrenom.setText(listeLivre.get(position).getPrenom());
        tvTel.setText(listeLivre.get(position).getNumero());
        tvId.setText(listeLivre.get(position).getId().toString());
        //endregion*/

        return layoutItem;
    }
}
