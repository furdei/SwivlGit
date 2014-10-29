package com.swivl.furdey.swivlgit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.swivl.furdey.swivlgit.content.model.User;
import com.swivl.furdey.swivlgit.imagecache.BitmapLruCache;

/**
 * Fragment for displaying large avatar
 */
public class UserAvatarFragment extends Fragment {

    public interface UserAvatarListener {
        void onLargeAvatarClick();
    }

    public static UserAvatarFragment createInstance(User user) {
        UserAvatarFragment userAvatarFragment = new UserAvatarFragment();
        Bundle args = new Bundle(1);
        args.putSerializable(USER_PARAM, user);
        userAvatarFragment.setArguments(args);
        return userAvatarFragment;
    }

    private final static String USER_PARAM = "user";

    private NetworkImageView avatar;
    private ImageLoader imageLoader;
    private BitmapLruCache bitmapLruCache;
    private UserAvatarListener listener;

    public UserAvatarFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        imageLoader = ((ApplicationController)activity.getApplicationContext()).getImageLoader();
        bitmapLruCache = ((ApplicationController)activity.getApplicationContext()).getBitmapLruCache();
        listener = (UserAvatarListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_avatar, container, false);
        avatar = (NetworkImageView) rootView.findViewById(R.id.largeAvatar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onLargeAvatarClick();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = (User) getArguments().getSerializable(USER_PARAM);
        Bitmap cached = bitmapLruCache.getBitmap(user.getAvatarUrl(User.SMALL_SIZE));
        if (cached != null) {
            System.out.println("UserAvatarFragment.onResume CACHED");
            avatar.setImageBitmap(cached);
        } else {
            System.out.println("UserAvatarFragment.onResume UNKNOWN");
            avatar.setDefaultImageResId(R.drawable.unknown_user);
        }
        avatar.setImageUrl(user.getAvatarUrl(User.LARGE_SIZE), imageLoader);
    }
}
