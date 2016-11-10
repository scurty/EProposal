package com.infosys.eproposal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

/**
 * Created by scurt on 10/11/2016.
 */
public class VideoFragment extends Fragment {

    VideoView video;

    public static VideoFragment newInstance(String text) {

        VideoFragment f = new VideoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.video_frag, container, false);

        video = (VideoView) v.findViewById(R.id.video);
        // Load and start the movie
        Uri video1 = Uri.parse("android.resource://com.infosys.infosysinfo/" + R.raw.video);
        video.setVideoURI(video1);
        //  video.start();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser)   // If we are becoming invisible, then...
            {
                video.pause();
            }

            if (isVisibleToUser) {

                video.start();
            }
        }
    }
}
