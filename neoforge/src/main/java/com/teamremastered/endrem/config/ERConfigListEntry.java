package com.teamremastered.endrem.config;

import com.google.common.collect.Lists;
import com.teamremastered.endrem.Constants;

import java.util.ArrayList;

public class ERConfigListEntry extends ERConfigGenericEntry<String> {
    public ERConfigListEntry(String id, String comment, ArrayList<String> default_value) {
        super(id, comment, getStringFromList(default_value));
    }

    public ERConfigListEntry(String id, String comment, String default_value) {
        super(id, comment, default_value);
    }

    public ArrayList<String> getList() {
        // Gets the raw string value
        String str = this.getRaw();

        // If the string is too small or isn't formatted properly, return a default value and send an error message
        if (str.length() < 2 || str.charAt(0) != '[' || str.charAt(str.length() - 1) != ']') {
            Constants.LOGGER.error(String.format("Invalid value for list: %s", str));
            str = this.DEFAULT_VALUE;
        }
        return Lists.newArrayList(str.substring(1, str.length() - 1).split(",\\s*"));
    }

    public void set(ArrayList<String> value) {
        this.set(getStringFromList(value));
    }

    private static String getStringFromList(ArrayList<String> lst) {
        return "[" + String.join(", ", lst) + "]";
    }
}

