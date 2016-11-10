package com.infosys.eproposal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by scurt on 10/11/2016.
 */
public class PhotoFragment extends Fragment {

    public static PhotoFragment newInstance(int seq) {

        PhotoFragment f = new PhotoFragment();
        Bundle b = new Bundle();
        b.putInt("msg", seq);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setImageResource(getArguments().getInt("msg"));
        //  container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }


}