package com.infosys.eproposal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
    }
