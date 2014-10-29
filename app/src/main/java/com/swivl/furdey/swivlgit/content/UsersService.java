package com.swivl.furdey.swivlgit.content;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.swivl.furdey.swivlgit.content.client.ClientException;
import com.swivl.furdey.swivlgit.content.client.JsonUsersReader;
import com.swivl.furdey.swivlgit.content.client.UsersClient;
import com.swivl.furdey.swivlgit.content.model.User;

/**
 * Service responsible for background user data download
 */
public class UsersService extends IntentService {

    public static Intent getUsersIntent(Context context) {
        Intent intent = new Intent(context, UsersService.class);
        intent.putExtra(ACTION, GET_USERS);
        return intent;
    }

    private static final String ACTION = "action";
    private static final int GET_USERS = 0;

    public UsersService() {
        super(UsersService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            switch (intent.getExtras().getInt(ACTION)) {
                case GET_USERS:
                    getUsers();
                break;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            // TODO: send exception to a bug tracker in a real application
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getUsers() throws ClientException {
        UsersClient usersClient = new UsersClient(new JsonUsersReader(new Gson()));
        User[] users = usersClient.getUsers();
        UsersFacade.deleteAllUsers(getApplicationContext());
        UsersFacade.insertUsers(getApplicationContext(), users);
    }
}
