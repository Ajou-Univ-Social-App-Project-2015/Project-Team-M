package kr.ac.ajou.media.project_team_m;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class SettingActivity extends AppCompatActivity {

    private TextView textUsernick, textUseremail, hidden;
    private Button buttonArticle, buttonLike;
    private ImageView userImage;
    private String email, nick;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_setting);
        hidden = (TextView) findViewById(R.id.hidden);
        textUseremail = (TextView) findViewById(R.id.useremail);
        textUsernick = (TextView) findViewById(R.id.usernick);
        buttonArticle = (Button) findViewById(R.id.buttonarticle);
        buttonLike = (Button) findViewById(R.id.buttonlike);
        userImage = (ImageView) findViewById(R.id.userimage);
        final ListView titleview = (ListView) findViewById(R.id.listtitleView);

        // User email, nick
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");
        textUsernick.setText(nick);
        textUseremail.setText(email);
        textUsernick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "닉네임을 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "사진을 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // title 준비
        final ArrayList<String> str = new ArrayList<String>();
        final ArrayList<String> nostr = new ArrayList<String>();
        IllPercentClient.get("/articles/" + nick, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                try {
                    for (int i = success.length() - 1; i > -1; i--) {
                        JSONObject response = success.getJSONObject(i);
                        String title = response.getString("title");
                        int no = response.getInt("no");
                        str.add(title);
                        nostr.add(String.valueOf(no));
                        hidden.setText(String.valueOf(no));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, str);

        buttonArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleview.setAdapter(adapter);
                titleview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context,ArticleDetailActivity.class);
                        String temp = hidden.getText().toString();
                        intent.putExtra("no", nostr.get(position));
                        intent.putExtra("email", email);
                        intent.putExtra("nick", nick);
                        startActivity(intent);
                    }
                });
            }
        });

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "준비 중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로
        getSupportActionBar().setIcon(R.drawable.logo_lime);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    // Tool bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(SettingActivity.this, ListActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
