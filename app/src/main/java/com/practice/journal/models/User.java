/**
 * Class for representing a User used for app login.
 * @author Aaron Alba
 */

package com.practice.journal.models;

public class User {
    private String mName;
    private String mPin;

    public User(String n, String p) {
        mName = n;
        mPin = p;
    }

    public String getName() {
        return mName;
    }

    public String getPin() {
        return mPin;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPin(String pin) {
        mPin = pin;
    }
}
