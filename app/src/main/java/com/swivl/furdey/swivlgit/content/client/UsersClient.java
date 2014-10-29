package com.swivl.furdey.swivlgit.content.client;

import com.swivl.furdey.swivlgit.content.model.User;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A client to the GitHub Users Service
 */
public class UsersClient {

    private static final String USERS_URL = "https://api.github.com/users";
    private static final String USERS_ERROR = "Error while retrieving users list";

    private final JsonUsersReader usersReader;

    public UsersClient(JsonUsersReader usersReader) {
        this.usersReader = usersReader;
    }

    public User[] getUsers() throws ClientException {
        try {
            URL url = new URL(USERS_URL);
            InputStream stream = url.openStream();
            String usersJSON = IOUtils.toString(stream, "UTF-8");
            return usersReader.fromJson(usersJSON);
        } catch (MalformedURLException e) {
            throw new ClientException(USERS_ERROR, e);
        } catch (IOException e) {
            throw new ClientException(USERS_ERROR, e);
        }
    }
}
