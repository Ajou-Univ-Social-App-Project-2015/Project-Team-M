package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class InputActivity extends AppCompatActivity {


    private EditText editTitle, editContent;
    private LinearLayout keysCheckbox;
    private String[] keywords;
    private String email, nick;

    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_input);

        editTitle = (EditText) findViewById(R.id.input_title);
        editContent = (EditText) findViewById(R.id.input_content);

        keysCheckbox = (LinearLayout) findViewById(R.id.keywords_checkbox);
        keywords = getResources().getStringArray(R.array.keywords);
        for (int i=0; i<keywords.length; i++) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(keywords[i]);
            keysCheckbox.addView(checkBox);
        }

        // User email
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로
        getSupportActionBar().setIcon(R.drawable.logo_lime);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection Simplifiable If Statement
        if (id == R.id.action_submit) {
            inputArticle();
            Intent intent = new Intent(InputActivity.this, ListActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            editContent.setText("");
            editTitle.setText("");
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(InputActivity.this, ListActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            editContent.setText("");
            editTitle.setText("");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void inputArticle() {
        String keys = "";
        // checkbox: Keywords
        for(int i=0; i<keysCheckbox.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) keysCheckbox.getChildAt(i);
            if (checkBox.isChecked()) {
                keys += checkBox.getText().toString()+" ";
            }
        }

        // Database connection
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();

        if(title.length() == 0 || content.length() == 0) Toast.makeText(getApplicationContext(), "채우세요.", Toast.LENGTH_SHORT).show();
        else {
            RequestParams params = new RequestParams();
            params.add("writer",nick);
            params.add("title",title);
            params.add("cont",content);
            params.add("keyarray",keys);
            int rand = new Random().nextInt(6)+1;
            params.add("randimage", String.valueOf(rand));

            IllPercentClient.post("/articles", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(InputActivity.this, "Created!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(InputActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
