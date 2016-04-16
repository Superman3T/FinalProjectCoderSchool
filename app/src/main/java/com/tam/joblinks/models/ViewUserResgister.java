package com.tam.joblinks.models;

/**
 * Created by toan on 4/14/2016.
 */
public class ViewUserResgister {
    public String email;
    public String firstName;
    public String lastName;
    public String password;

    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }
}
