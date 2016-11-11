package com.infosys.eproposal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by sidney_leite on 09/11/2016.
 */
public class AddProposal extends Activity {

    TextView txaddproposal;

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
        txaddproposal = (TextView) findViewById(R.id.editText);

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarregaPrimeiroProp();
                Intent intent = getIntent();
                intent.putExtra("addproposal", txaddproposal.getText());
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

    private void CarregaPrimeiroProp() {

        Proposal prop_insrt = new Proposal();


      /*  Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.fluxo);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        prop_insrt.setImage(bitMapData);*/

        BD bda = new BD(getApplication());
        List<Proposal> propList = bda.buscarProposals();

        prop_insrt.setName("Proposta Gerdal " + propList.size());
        prop_insrt.setDescription("Test Inicial Validate Finance " + propList.size());

        String stg2;
        if (propList.size() <= 8) {
            stg2 = "capa" + propList.size() + ".png";
        } else {
            stg2 = "capa0.png";
        }
        String stg = Memory.FindDir("/Data/", getApplication()) + stg2;

        prop_insrt.setImagepath(stg);

        BD bd = new BD(getApplication());
        bd.inserirProp(prop_insrt);
    }
}
