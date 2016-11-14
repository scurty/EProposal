package com.infosys.eproposal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static android.R.attr.text;
import static com.infosys.eproposal.R.id.editText1;
import static java.security.AccessController.getContext;

/**
 * Created by sidney_leite on 09/11/2016.
 */
public class AddProposal extends Activity {

    TextView txtproposal;
    TextView txtsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window

        setContentView(R.layout.addproposal);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Button btok = (Button) findViewById(R.id.ok);
        Button btcancel = (Button) findViewById(R.id.cancel);
        txtproposal = (TextView) findViewById(R.id.editText);
        txtsenha = (TextView) findViewById(R.id.editText1);

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameProposal;
                if (txtproposal.getText().toString().isEmpty()) {
                    nameProposal = "Proposta";

                } else {
                    nameProposal = txtproposal.getText().toString();
                }

                //        Cloud();
                CarregaPrimeiroProp(nameProposal);

                Intent intent = getIntent();
                intent.putExtra("addproposal", txtproposal.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }


    private void CarregaPrimeiroProp(String text) {

        Proposal prop_insrt = new Proposal();

        BD bda = new BD(getApplication());
        List<Proposal> propList = bda.buscarProposals();

        if (text.equals("Proposta")) {
            prop_insrt.setName(text + " " + propList.size());
            prop_insrt.setDescription("Descrição " + text + " " + propList.size());

        } else {
            prop_insrt.setName(text);
            prop_insrt.setDescription("Descrição " + text);

        }

        String stg2;
        if (propList.size() <= 8) {
            stg2 = "capa" + propList.size() + ".png";
        } else {
            stg2 = "capa0.png";
        }
        String stg = Memory.FindDir("/Data/", getApplication()) + stg2;

        prop_insrt.setImagepath(stg);

        BD bd = new BD(getApplication());
        long id_prop = bd.inserirProp(prop_insrt);
        int seq = -1;

        ProposalItem propitem_insrt = new ProposalItem();
        propitem_insrt.setType(1);
        propitem_insrt.setId_prop(id_prop);

        if (propList.size() == 0) {

            // Itens
            propitem_insrt.setMenu(getResources().getString(R.string.menu1));

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu11));

            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "sumario.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu12));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "conteudo.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu13));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "sumario_executivo.png");
            bd.inserirPropItem(propitem_insrt);

            // Perfil Corporativo
            propitem_insrt.setMenu(getResources().getString(R.string.menu2));

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu21));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "produtos_e_servicos_capa.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu22));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "produtos_e_servicos.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu23));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "presenca_global.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu24));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "perfil_corporativo_global.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu25));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "perfil_corporativo_brasil.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu26));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "clientes.png");
            bd.inserirPropItem(propitem_insrt);

            // Nossa Experiência
            propitem_insrt.setMenu(getResources().getString(R.string.menu3));

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu301));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "experiencia.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu302));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "alliage.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu303));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "hersheys.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu304));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "infosys.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu305));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "johnson_controls.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu306));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "skf.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu307));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "toyota.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu308));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "zurich.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu309));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "the_linde_group.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu310));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "bg_group.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu311));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "cerradinho.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu312));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "syngenta.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu313));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "globonet.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu314));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "arcelor.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu315));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "nindera.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu316));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "ref_ams_brasil_suporte.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu317));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "ref_ams_brasil_suporte_oracle.png");
            bd.inserirPropItem(propitem_insrt);

            // Nossos Produtos
            propitem_insrt.setMenu(getResources().getString(R.string.menu4));

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu41));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "alguns_nossos_produtos.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu42));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "infosys_fiscal_ingine.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu43));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "infosys_fiscal_ingine2.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu44));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "information_plataform.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu45));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "panaya.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu46));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "rpa.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu47));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "skava.png");
            bd.inserirPropItem(propitem_insrt);


            // Encerramento
            propitem_insrt.setMenu(getResources().getString(R.string.menu5));

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu51));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "agradecimento.png");
            bd.inserirPropItem(propitem_insrt);


            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName(getResources().getString(R.string.menu52));
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "fim.png");
            bd.inserirPropItem(propitem_insrt);


        } else {
            // Itens
            propitem_insrt.setMenu("Primeiro Nível");

            propitem_insrt.setSeq(seq++);
            propitem_insrt.setName("Hello");
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "capa0.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setId_prop(id_prop);
            propitem_insrt.setSeq(seq++);
            propitem_insrt.setMenu("Primeiro Nível");
            propitem_insrt.setName("Hello 2");
            propitem_insrt.setType(1);
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "capa1.png");
            bd.inserirPropItem(propitem_insrt);

            propitem_insrt.setId_prop(id_prop);
            propitem_insrt.setSeq(seq++);
            propitem_insrt.setMenu("Segundo Nível");
            propitem_insrt.setName("Fim 1");
            propitem_insrt.setType(1);
            propitem_insrt.setImagepath(Memory.FindDir("/Data/", getApplication()) + "capa2.png");
            bd.inserirPropItem(propitem_insrt);
        }

    }
}
