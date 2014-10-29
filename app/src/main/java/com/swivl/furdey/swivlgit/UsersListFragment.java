package com.swivl.furdey.swivlgit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.swivl.furdey.swivlgit.content.UsersFacade;
import com.swivl.furdey.swivlgit.widget.UsersListAdapter;

/**
 * Users List Fragment
 */
public class UsersListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int USERS_LOADER = 0;

    private ListView usersList;
    private CursorAdapter usersListAdapter;

    public UsersListFragment() {
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
