package com.example;


/**
 * Created by sidney_leite on 09/11/2016.
 */
public class Proposal {

    private long id;
    private String name;
    private String senha;
    private int type;
    private String description;
    // private byte[] image;
    private String imagepath;
    // private Bitmap imagebitmap;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

/*    public Bitmap getImagebitmap() {
        return imagebitmap;
    }

    public void setImagebitmap(Bitmap imagebitmap) {
        this.imagebitmap = imagebitmap;
    }*/

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
