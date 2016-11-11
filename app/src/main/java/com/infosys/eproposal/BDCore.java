package com.infosys.eproposal;

/**
 * Created by sidney_leite on 09/11/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "proposal";
    private static final int VERSAO_BD = 1;

    public BDCore(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL("create table prop(_id integer primary key autoincrement, name text not null, type int , description text not null, imagepath text);");
        bd.execSQL("create table prop_item   (_id integer primary key autoincrement, id_prop integer not null, seq int not null, imagepath text);");

    //    bd.execSQL("create table athlete     (_id integer primary key autoincrement, id_inst integer not null, id_station integer not null, name text not null, age int not null);");
     //   bd.execSQL("create table aula     (_id integer primary key,  nome text not null, tp_instalacao integer not null, tp_spin integer not null, tp_aula integer not null, nivel integer not null, criador text not null, duracao integer not null, pathmusic text );");
     //   bd.execSQL("create table aula_seq (_id integer primary key,  id_aula integer not null, momento integer not null, valor decimal(10,2) , texto text ,fala text  );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table IF EXISTS prop ;");
         bd.execSQL("drop table IF EXISTS prop_item;");

        //     bd.execSQL("drop table IF EXISTS athlete;");
        //    bd.execSQL("drop table IF EXISTS aula;");
    //    bd.execSQL("drop table IF EXISTS aula_seq;");
        onCreate(bd);
    }

}
