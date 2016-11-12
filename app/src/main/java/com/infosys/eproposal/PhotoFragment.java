package com.infosys.eproposal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by scurt on 10/11/2016.
 */
public class PhotoFragment extends Fragment {

    public static PhotoFragment newInstance(String seq) {

        PhotoFragment f = new PhotoFragment();
        Bundle b = new Bundle();
        b.putString("msg", seq);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        PhotoView photoView = new PhotoView(container.getContext());
        File imgFile = new File(getArguments().getString("msg"));

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            photoView.setImageBitmap(myBitmap);

        }

        //  container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }


}