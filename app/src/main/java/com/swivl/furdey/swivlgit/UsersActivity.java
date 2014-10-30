package com.swivl.furdey.swivlgit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.swivl.furdey.swivlgit.content.model.User;

/**
 * Activity with users list and large user icon
 */
public class UsersActivity extends ActionBarActivity implements
        UsersListFragment.UsersListListener, UserAvatarFragment.UserAvatarListener {

    private static final String AVATAR_FRAGMENT_TAG = "avatar";

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

    @Override
    public void onSmallAvatarClick(User user) {
        if (!removeLargeAvatar()) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.show_avatar, R.anim.hide_avatar,
                            R.anim.show_avatar, R.anim.hide_avatar)
                    .add(R.id.container, UserAvatarFragment.createInstance(user), AVATAR_FRAGMENT_TAG)
                    .addToBackStack(AVATAR_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onLargeAvatarClick() {
        removeLargeAvatar();
    }

    private boolean removeLargeAvatar() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(AVATAR_FRAGMENT_TAG);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(AVATAR_FRAGMENT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        }

        return false;
    }
}
