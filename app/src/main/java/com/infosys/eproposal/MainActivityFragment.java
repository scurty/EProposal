package com.infosys.eproposal;

import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.Map;

import static android.R.attr.pathData;
import static com.infosys.eproposal.Memory.sdCard;
import static com.infosys.eproposal.R.id.ivprop;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "MainActivityFragment";
    File sdCard;
    String pathData;
    private GridView gridView;
    private List<Proposal> propList;
    private GridAdapter adapter;

    public MainActivityFragment() {
    }

    public static Bitmap getImageFromBLOB(byte[] mBlob) {
        byte[] bb = mBlob;
        return BitmapFactory.decodeByteArray(bb, 0, bb.length);
    }

    private static void createDirIfNotExists(String path) {
        // TODO Auto-generated method stub
        boolean ret = true;

        File file = new File(path);
        if (!file.exists()) {

            file.mkdir();
            if (!file.mkdirs()) {

                ret = false;
            } else {

            }
        } else {

        }
        // return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Inicial();

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        adapter = (new GridAdapter(getActivity()));
        gridView.setAdapter(adapter);


        return rootView;
    }

    public void Inicial() {

        BD bd = new BD(getActivity());
        propList = bd.buscarProposals();

        // Map<String, File> externalLocations = ExternalStorage
        //         .getAllStorageLocations();
        //  sdCard = externalLocations.get(ExternalStorage.SD_CARD);

        Memory memory = new Memory();

        String interno = "I";
        String path_geral = memory.GetDirPrinc(interno, getContext());

        createDirIfNotExists(path_geral);
        createDirIfNotExists(path_geral + "/Data");

        pathData = memory.FindDir("/Data/", getContext());
        Log.d(TAG, pathData);
    }

    public void refreshAdapter() {
        Inicial();
        adapter.notifyDataSetChanged();
    }

    private class GridAdapter extends BaseAdapter {

        Proposal prop_item;
        private Context mcontext;
        private LayoutInflater mInflater;


        public GridAdapter(Context context) {
            this.mcontext = context;

            mInflater = LayoutInflater.from(context);
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
            Animation animation;
            animation = AnimationUtils.loadAnimation(mcontext, R.anim.animation_move);

            if (convertView == null) {
                holder = new NewHolder();

                // LayoutInflater inflater = (LayoutInflater) mcontext
                //         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.grid_view, null);

                holder.tvprop = (TextView) convertView.findViewById(R.id.tvprop);
                holder.tvprop_desc = (TextView) convertView.findViewById(R.id.tvprop_desc);
                holder.ivprop = (ImageView) convertView.findViewById(ivprop);

               /*   holder.ivprop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d(TAG,"holder.ivprop.setOnClickListener " + position);

                       Proposal prop_click = propList.get(position);

                        Intent intent;
                        Bundle b = new Bundle();
                        intent = new Intent(getActivity(), MainActivityProp.class);
                        b.putString("name", prop_click.getName());
                        b.putString("description", prop_click.getDescription());
                        b.putLong("id_prop", prop_click.getId());
                        intent.putExtras(b);

                        startActivityForResult(intent, MainActivity.DIALOG_MAIN_FRAGMENT);

                    }
                });*/

                convertView.setTag(holder);

            } else {
                holder = (NewHolder) convertView.getTag();
            }

            prop_item = propList.get(position);
            holder.tvprop.setText(prop_item.getName());
            holder.tvprop_desc.setText(prop_item.getDescription());
            holder.ivprop.setImageBitmap(prop_item.getImagebitmap());

           /* if (prop_item.getImagebitmap() == null) {
                File file;
                file = new File(prop_item.getImagepath());
                if (file.exists()) {

                    //  holder.ivprop.setImageURI(Uri.fromFile(file));
                    prop_item.setImagebitmap(decodeFile(file));
                    holder.ivprop.setImageBitmap(prop_item.getImagebitmap());
                }
            } else {
                holder.ivprop.setImageBitmap(prop_item.getImagebitmap());
            }*/

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "convertView.setOnClickListener " + position);

                    Proposal prop_click = propList.get(position);

                    Intent intent;
                    Bundle b = new Bundle();
                    intent = new Intent(getActivity(), MainActivityProp.class);
                    b.putString("name", prop_click.getName());
                    b.putString("description", prop_click.getDescription());
                    b.putLong("id_prop", prop_click.getId());
                    intent.putExtras(b);

                    startActivityForResult(intent, MainActivity.DIALOG_MAIN_FRAGMENT);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    new AlertDialog.Builder(mcontext)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    BD bd = new BD(getActivity());
                                    bd.deleteProposal(propList.get(position).getId());
                                    refreshAdapter();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }
            });
            convertView.startAnimation(animation);

            return convertView;
        }

        private class NewHolder {

            public ImageView ivprop;
            public TextView tvprop;
            public TextView tvprop_desc;
        }
    }


}
