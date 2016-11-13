package com.infosys.eproposal;

/**
 * Created by sidney_leite on 09/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BD {

    private final Context mcontext;
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
        mcontext = context;
    }

    public long inserirProp(Proposal prop) {
        ContentValues valores = new ContentValues();
        valores.put("name", prop.getName());
        valores.put("type", prop.getType());
        valores.put("description", prop.getDescription());
        valores.put("imagepath", prop.getImagepath());
        // Retorna o ID do registro inserido
        return bd.insert("prop", null, valores);
    }

    public List<Proposal> buscarProposals() {
        List<Proposal> list = new ArrayList<Proposal>();
        String[] colunas = new String[]{"_id", "name", "type", "description", "imagepath"};

        Cursor cursor = bd.query("prop", colunas, null, null, null, null, "_id ASC");

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Proposal u = new Proposal();
                    u.setId(cursor.getLong(0));
                    u.setName(cursor.getString(1));
                    u.setType(cursor.getInt(2));
                    u.setDescription(cursor.getString(3));
                    u.setImagepath(cursor.getString(4));

                    list.add(u);

                } while (cursor.moveToNext());
            }
        }
        return (list);
    }

    public void inserirPropItem(ProposalItem propitem) {
        ContentValues valores = new ContentValues();
        valores.put("id_prop", propitem.getId_prop());
        valores.put("seq", propitem.getSeq());
        valores.put("menu", propitem.getMenu());
        valores.put("name", propitem.getName());
        valores.put("type", propitem.getType());
        valores.put("path", propitem.getPath());
        bd.insert("prop_item", null, valores);
    }

    public List<ProposalItem> buscarProposalItens(long id_prop) {
        List<ProposalItem> list = new ArrayList<ProposalItem>();
        String[] colunas = new String[]{"_id", "id_prop", "seq", "menu", "name", "type", "path"};
        String whereClause = "id_prop = ?";
        String[] whereArgs = new String[]{
                String.valueOf(id_prop)};
        Cursor cursor = bd.query("prop_item", colunas, whereClause, whereArgs, null, null, "seq ASC");

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProposalItem u = new ProposalItem();
                    u.setId(cursor.getLong(0));
                    u.setId_prop(cursor.getLong(1));
                    u.setSeq(cursor.getInt(2));
                    u.setMenu(cursor.getString(3));
                    u.setName(cursor.getString(4));
                    u.setType(cursor.getInt(5));
                    u.setPath(cursor.getString(6));

                    list.add(u);

                } while (cursor.moveToNext());
            }
        }
        return (list);
    }


    public void deleteProposal(long id) {
        bd.delete("prop", "_id = " + id, null);
    }
}
