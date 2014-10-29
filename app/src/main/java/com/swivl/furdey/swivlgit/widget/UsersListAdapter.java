package com.swivl.furdey.swivlgit.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.swivl.furdey.swivlgit.ApplicationController;
import com.swivl.furdey.swivlgit.R;
import com.swivl.furdey.swivlgit.content.UsersFacade;
import com.swivl.furdey.swivlgit.content.model.User;

/**
 * Adapter for populating users list
 */
public class UsersListAdapter extends CursorAdapter {

    private static final String TAG = UsersListAdapter.class.getCanonicalName();

    static class ViewHolder {
        public NetworkImageView icon;
        public TextView login;
        public TextView htmlUrl;
    }

    private static final String USER_ID = "userId";

    private ImageLoader imageLoader;

    public UsersListAdapter(Context context) {
        super(context, null, 0);
        imageLoader = ((ApplicationController)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.icon = (NetworkImageView) view.findViewById(R.id.usersListItemIcon);
        viewHolder.login = (TextView) view.findViewById(R.id.usersListItemLogin);
        viewHolder.htmlUrl = (TextView) view.findViewById(R.id.usersListItemHtmlUrl);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        User user = UsersFacade.fromCursor(cursor);

        viewHolder.login.setText(user.getLogin());
        viewHolder.htmlUrl.setText(user.getHtmlUrl());

        Bundle extras = new Bundle();
        extras.putLong(USER_ID, user.getLocalId());
        viewHolder.icon.setTag(user.getLocalId());
        viewHolder.icon.setDefaultImageResId(R.drawable.unknown_user);
        viewHolder.icon.setImageUrl(user.getAvatarUrl(100), imageLoader);
    }
}
