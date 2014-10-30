package com.swivl.furdey.swivlgit;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.swivl.furdey.swivlgit.content.UsersFacade;
import com.swivl.furdey.swivlgit.content.model.User;
import com.swivl.furdey.swivlgit.widget.UsersListAdapter;

/**
 * Users List Fragment
 */
public class UsersListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public interface UsersListListener {
        void onSmallAvatarClick(User user);
    }

    private static final int USERS_LOADER = 0;

    private ListView usersList;
    private CursorAdapter usersListAdapter;
    private UsersListListener listener;

    public UsersListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (UsersListListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users_list, container, false);
        usersList = (ListView) rootView.findViewById(R.id.usersList);
        usersListAdapter = new UsersListAdapter(getActivity().getApplicationContext());
        usersList.setAdapter(usersListAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = usersListAdapter.getCursor();
                cursor.moveToPosition(i);
                User user = UsersFacade.fromCursor(cursor);
                listener.onSmallAvatarClick(user);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(USERS_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return UsersFacade.getUsers(getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        usersListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        usersListAdapter.swapCursor(null);
    }
}
