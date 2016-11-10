package com.infosys.eproposal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridView;
    private List<Proposal> propList;
    private GridAdapter adapter;

    public MainActivityFragment() {
    }

    public static Bitmap getImageFromBLOB(byte[] mBlob) {
        byte[] bb = mBlob;
        return BitmapFactory.decodeByteArray(bb, 0, bb.length);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        CarregaProp();

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        adapter = (new GridAdapter(getActivity()));
        gridView.setAdapter(adapter);


        return rootView;
    }

    private void CarregaProp() {

        BD bd = new BD(getActivity());
        propList = bd.buscarProposals();

        if (propList.size() <= 20) {
            CarregaPrimeiroProp();
            CarregaProp();
        }
    }

    private void CarregaPrimeiroProp() {

        Proposal prop_insrt = new Proposal();
        prop_insrt.setName("Proposta Gerdal");
        prop_insrt.setDescription("Test Inicial Validate");

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.fluxo);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        prop_insrt.setImage(bitMapData);

        BD bd = new BD(getActivity());
        bd.inserirProp(prop_insrt);
    }

    private class GridAdapter extends BaseAdapter {

        Proposal prop_item;
        private Context mcontext;

        private int viewWidth;
        private int viewHeight;

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

            NewHolder holder = null;
            ImageView imageView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mcontext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_view, null);

                holder = new NewHolder();

                holder.tvprop = (TextView) convertView.findViewById(R.id.tvprop);
                holder.tvprop_desc = (TextView) convertView.findViewById(R.id.tvprop_desc);
                holder.ivprop = (ImageView) convertView.findViewById(R.id.ivprop);

                convertView.setTag(holder);
 
            } else {
                holder = (NewHolder) convertView.getTag();
            }

            prop_item = propList.get(position);
            holder.tvprop.setText(prop_item.getName());
            holder.tvprop_desc.setText(prop_item.getDescription());

            String path = Environment.getExternalStorageDirectory() + "/Image/Structure.jpg";
// "/storage/emulated/0/Image/Structure.jpg"
            File file;
            file = new File(path);
            if (file.exists()) {
                // ImageView myImage = new ImageView(this);
                //  myImage.setImageURI(Uri.fromFile(file));
                // Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.ivprop.setImageURI(Uri.fromFile(file));
            }

            // holder.ivprop.setImageDrawable(getResources().getDrawable(R.drawable.sumario));
            // ivprop.setImageBitmap(getImageFromBLOB(prop_item.getImage()));

            // viewWidth = 1000; //convertView.getMeasuredWidth();
            // viewHeight = 400; //convertView.getMeasuredHeight();
            // holder.ivprop.setImageBitmap(ImageResizer.decodeSampledBitmapFromResource(getResources(),
            //        R.drawable.sumario, viewWidth, viewHeight, false));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent;
                    Bundle b = new Bundle();
                    intent = new Intent(getActivity(), MainActivityProp.class);
                    b.putString("name", prop_item.getName());
                    b.putString("description", prop_item.getDescription());
                    b.putLong("id", prop_item.getId());
                    intent.putExtras(b);

                    startActivityForResult(intent, MainActivity.DIALOG_MAIN_FRAGMENT);

                }
            });
            return convertView;
        }

        private class NewHolder {

            public ImageView ivprop;
            public TextView tvprop;
            public TextView tvprop_desc;
        }


    }
}
