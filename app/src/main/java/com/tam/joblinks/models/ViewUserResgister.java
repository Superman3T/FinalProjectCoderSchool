package com.tam.joblinks.models;

/**
 * Created by toan on 4/14/2016.
 */
public class ViewUserResgister {
    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY = "last_name";
    public String email;
    public String firstName;
    public String lastName;
    public String password;

    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }
}
