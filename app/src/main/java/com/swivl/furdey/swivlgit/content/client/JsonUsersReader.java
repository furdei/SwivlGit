package com.swivl.furdey.swivlgit.content.client;

import com.google.gson.Gson;
import com.swivl.furdey.swivlgit.content.model.User;

/**
 * Parses JSON array of users
 */
public class JsonUsersReader {

    private final Gson gson;

    public JsonUsersReader(Gson gson) {
        this.gson = gson;
    }

    public User[] fromJson(String json) {
        return gson.fromJson(json, User[].class);
    }
}
