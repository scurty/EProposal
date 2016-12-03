package com.infosys.eproposal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final int DIALOG_MAIN_FRAGMENT = 2;
    private static final String TAG = "MainActivity";
    private static final int ADD_PROPOSAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
            return true;
            case R.id.add_proposal:
                Intent intent = new Intent(this, AddProposal.class);
                startActivityForResult(intent, MainActivity.ADD_PROPOSAL);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case ADD_PROPOSAL:
                if (resultCode == RESULT_OK) {
                    String addproposal = data.getExtras()
                            .getString("addproposal");
                    Log.d(TAG, "Add Porposal " + addproposal);

                    MainActivityFragment f = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                    if (f != null) {
                        f.refreshAdapter();
                    }
                }
        }
    }
}
