package com.infosys.eproposal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_PROPOSAL = 1;
    private static final String TAG = "MainActivity";
    private GridView gridView;
    private List<Proposal> propList;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CarregaProp();

        GridView gridView = (GridView) findViewById(R.id.gridView1);
        adapter = new GridAdapter(getApplication());
        gridView.setAdapter(adapter);
    }

    private void CarregaProp() {

        BD bd = new BD(getApplication());
        propList = bd.buscarProposals();

        if ( propList.isEmpty() ) {
            CarregaPrimeiroProp();
            CarregaProp();
        }
            }

    private class GridAdapter extends BaseAdapter {


        private Context mcontext;

        public GridAdapter(Context context) {
            this.mcontext = context;

        }

        @Override
        public int getCount() {
            //    Log.e(TAG,"getCount  listStation.size()=" + listStation.size()  );
            return propList.size();
        }

        @Override
        public Object getItem(int position) {
            return propList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            AbsListView listView = (AbsListView) parent;
            boolean firstVisiblePosition = listView.isItemChecked(position);

            LayoutInflater inflater = (LayoutInflater) mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_view_prop, null);

            TextView tvprop = (TextView) convertView.findViewById(R.id.tvprop);
            TextView tvprop_desc = (TextView) convertView.findViewById(R.id.tvprop_desc);
            ImageView ivprop = (ImageView) convertView.findViewById(R.id.ivprop);

            Proposal prop_item = propList.get(position);
            tvprop.setText(prop_item.getName());
            tvprop_desc.setText(prop_item.getDescription());
            ivprop.setImageBitmap(getImageFromBLOB(prop_item.getImage()));

            return convertView;
        }
    }

    public static Bitmap getImageFromBLOB(byte[] mBlob)
    {
        byte[] bb = mBlob;
        return BitmapFactory.decodeByteArray(bb, 0, bb.length);
    }

    private void CarregaPrimeiroProp() {

        Proposal prop_insrt = new Proposal();
        prop_insrt.setName("Proposta Gerdal");
        prop_insrt.setDescription("Test Inicial Validate");

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.fluxo);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        prop_insrt.setImage(bitMapData);

        BD bd = new BD(getApplication());
        bd.inserirProp(prop_insrt);
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
                String addproposal = data.getExtras()
                        .getString("addproposal");
                Log.d(TAG, "Add Porposal " + addproposal);
        }
    }
}
