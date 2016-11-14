package com.infosys.eproposal;

import android.graphics.Bitmap;

/**
 * Created by sidney_leite on 11/11/2016.
 */
public class ProposalItem {

    private long id;
    private long id_prop;
    private int seq;
    private String menu;
    private String name;
    private int type; // 1 - Image  2 - video
    private String imagepath;
    private Bitmap imagebitmap;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_prop() {
        return id_prop;
    }

    public void setId_prop(long id_prop) {
        this.id_prop = id_prop;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Bitmap getImagebitmap() {
        return imagebitmap;
    }

    public void setImagebitmap(Bitmap imagebitmap) {
        this.imagebitmap = imagebitmap;
    }
}
