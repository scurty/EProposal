package com.infosys.eproposal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            Bitmap myBitmap = null;

            // myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            // Bitmap myBitmap = BD.decodeFile(imgFile, 300);

          /* final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);*/

            try {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                FileInputStream fis = null;
                fis = new FileInputStream(imgFile.getAbsolutePath());
                BitmapFactory.decodeStream(fis, null, o);
                fis.close();
                final int REQUIRED_SIZE = 1000;
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                        break;
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
                BitmapFactory.Options op = new BitmapFactory.Options();
                op.inSampleSize = scale;
                fis = new FileInputStream(imgFile.getAbsolutePath());
                myBitmap = BitmapFactory.decodeStream(fis, null, op);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            photoView.setImageBitmap(myBitmap);
        }
        //  container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }
}