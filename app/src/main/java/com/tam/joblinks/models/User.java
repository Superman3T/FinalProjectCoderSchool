package com.tam.joblinks.models;

import com.backendless.BackendlessUser;

/**
 * Created by toan on 4/16/2016.
 */
public class User extends BackendlessUser {

    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY = "last_name";
    public static final String MAC_ADDRESS_KEY = "mac_address";

    public String getFirstName() {
        return (String) super.getProperty(FIRST_NAME_KEY);
    }

    public void setFirstName(String firstName) {
        super.setProperty(FIRST_NAME_KEY, firstName);
    }

    public String getLastName() {
        return (String) super.getProperty(LAST_NAME_KEY);
    }

    public void setLastName(String lastName) {
        super.setProperty(LAST_NAME_KEY, lastName);
    }

    public String getName() {
        return (String) super.getProperty("name");
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }

    public void setMacAddress(String macAddress) {
        super.setProperty(MAC_ADDRESS_KEY, macAddress);
    }
}
