package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity {

    public String email, nick;
    private ListView listView;
    ListAdapter listAdapter;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

        listView = (ListView) findViewById(R.id.activityview);
        listAdapter = new ListAdapter(this);
        listView.setAdapter(listAdapter);
        adapterFunction(listAdapter);

        // User email
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_lime);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    // Adapter
    private void adapterFunction(ListAdapter adapter) {
        final ListAdapter a = adapter;

        IllPercentClient.get("/articles", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                try {
                    for (int i = 0; i < success.length(); i++) {
                        JSONObject response = success.getJSONObject(i);
                        String date = response.getString("createdate").substring(9,28);
                        a.addItem(response.getInt("no"), response.getString("title"), response.getString("cont"),
                                "Written by "+response.getString("writer"), date, response.getString("commentcount"),
                                response.getString("likecount"), response.getString("keyarray"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection Simplifiable If Statement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ListActivity.this, SettingActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_write) {
            Intent intent = new Intent(ListActivity.this, InputActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(ListActivity.this, "기능 준비 중입니다.", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
