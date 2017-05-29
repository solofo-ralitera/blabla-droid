package com.blabla.project.blabla_droid.Follower;

/**
 * Created by popolos on 22/05/2017.
 */

public class ListViewItem {
    private String avatar;
    private String username;
    private String name;

    public ListViewItem(String avatar, String username, String name) {
        this.avatar = avatar;
        this.username = username;
        this.name = name;
    }

    public String getAvatar() { return avatar; }

    public String getUsername() {
        return username;
    }

    public String getName() { return name; }



}
