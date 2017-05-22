package com.blabla.project.blabla_droid.PLaylist;

/**
 * Created by popolos on 22/05/2017.
 */

public class ListViewItem {
    private int image;
    private String track;
    private String file;

    public ListViewItem(int image, String track, String file) {
        this.image = image;
        this.track = track;
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public String getTrack() {

        return track;
    }

    public int getImage() {

        return image;
    }


}
