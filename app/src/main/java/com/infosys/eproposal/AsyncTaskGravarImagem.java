package com.infosys.eproposal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

/**
 * Created by scurt on 02/12/2016.
 */
public class AsyncTaskGravarImagem extends AsyncTask<Object, Void, String> {

    Context mContext;

    public AsyncTaskGravarImagem(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Object... objects) {

        String newfile = null;
        String extension = "";
        int i;

        String type = (String) objects[0];
        switch (type) {
            case "prop":
                Proposal prop = (Proposal) objects[1];
                i = prop.getImagepath().lastIndexOf('.');
                if (i >= 0) {
                    extension = prop.getImagepath().substring(i + 1);
                }
                newfile = prop.getId() + "." + extension;
                break;
            case "propitem":
                ProposalItem proposalItem = (ProposalItem) objects[1];
                i = proposalItem.getImagepath().lastIndexOf('.');
                if (i >= 0) {
                    extension = proposalItem.getImagepath().substring(i + 1);
                }
                newfile = proposalItem.getId_prop() + "_" + proposalItem.getSeq() + "." + extension;
                break;
        }

        try {
            CloudStorage.Context(mContext);
            String dirdest = Environment.getExternalStorageDirectory().getPath() + "/EProposal/Data";
            CloudStorage.downloadFile("eproposal-150911.appspot.com", newfile, dirdest); //"39_6.png"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String execute(ProposalItem... propaitem) {
        String extension = "";
        int i = propaitem[0].getImagepath().lastIndexOf('.');
        if (i >= 0) {
            extension = propaitem[0].getImagepath().substring(i + 1);
        }
        String newfile = propaitem[0].getId() + "_" + propaitem[0].getSeq() + "." + extension;

        try {
            CloudStorage.Context(mContext);
            String dirdest = Environment.getExternalStorageDirectory().getPath() + "/EProposal/Data";
            CloudStorage.downloadFile("eproposal-150911.appspot.com", newfile, dirdest); //"39_6.png"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
