package com.alexcrijman.stsapp10.models;


public class ProgramPlayItem extends TheatrePlays {


    private String ora;

    public ProgramPlayItem(String ora) {
        super.TheatrePlays();
        this.ora = ora;
    }

    public ProgramPlayItem() {

    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

}
