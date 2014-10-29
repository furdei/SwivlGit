package com.swivl.furdey.swivlgit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Activity with users list and large user icon
 */
public class UsersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UsersListFragment())
                    .commit();
        }
    }

}
