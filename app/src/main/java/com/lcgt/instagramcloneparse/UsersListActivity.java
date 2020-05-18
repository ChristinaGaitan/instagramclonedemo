package com.lcgt.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);


        final ArrayList<String> usersList = new ArrayList<>();
        ListView usersListView = findViewById(R.id.usersListView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usersList);
        usersListView.setAdapter(arrayAdapter);


        ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();
        usersQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        usersQuery.addAscendingOrder("username");

        usersQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null) {
                    if (objects != null && objects.size() > 0) {
                        for(ParseUser user : objects) {
                            usersList.add(user.getUsername());
                            Log.i("Username: ", user.getUsername());
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        Log.i("UsersList: ", usersList.toString());
    }
}
