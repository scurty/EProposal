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

    public void inserirProp(Proposal prop) {
        ContentValues valores = new ContentValues();
        valores.put("name", prop.getName());
        valores.put("type", prop.getType());
        valores.put("description", prop.getDescription());
        valores.put("image", prop.getImage());
        bd.insert("prop", null, valores);
    }

    public List<Proposal> buscarProposals() {
        List<Proposal> list = new ArrayList<Proposal>();
        String[] colunas = new String[]{"_id", "name", "type", "description", "image"};

        Cursor cursor = bd.query("prop", colunas, null, null, null, null, "name ASC");

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Proposal u = new Proposal();
                    u.setId(cursor.getLong(0));
                    u.setName(cursor.getString(1));
                    u.setType(cursor.getInt(2));
                    u.setDescription(cursor.getString(3));
                    u.setImage(cursor.getBlob(4));

                    list.add(u);

                } while (cursor.moveToNext());
            }
        }
        return (list);
    }

}
