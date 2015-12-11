package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView keywords, date, writer, like, reply, cont, title;
    private ImageView image, blike, breply;
    private String no, email, nick;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_articledetail);

        title = (TextView) findViewById(R.id.title);
        keywords = (TextView) findViewById(R.id.keywords);
        date = (TextView) findViewById(R.id.date);
        writer = (TextView) findViewById(R.id.writername);
        like = (TextView) findViewById(R.id.liketext);
        reply = (TextView) findViewById(R.id.replytext);
        cont = (TextView) findViewById(R.id.content);
        image = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        IllPercentClient.get("/details/"+no, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                try {
                    JSONObject response = success.getJSONObject(0);
                    title.setText(response.getString("title"));
                    keywords.setText(response.getString("keyarray"));
                    date.setText(response.getString("createdate").substring(9, 28));
                    writer.setText("Written by "+response.getString("writer"));
                    like.setText(response.getString("likecount"));
                    reply.setText(response.getString("commentcount"));
                    cont.setText(response.getString("cont"));

                    int temp = response.getInt("randimage");
                    if(temp == 1) {
                        image.setImageResource(R.drawable.image01);
                    } else if(temp == 2) {
                        image.setImageResource(R.drawable.image02);
                    } else if(temp == 3) {
                        image.setImageResource(R.drawable.image03);
                    } else if(temp == 4) {
                        image.setImageResource(R.drawable.image04);
                    } else if(temp == 5) {
                        image.setImageResource(R.drawable.image05);
                    } else if(temp == 6) {
                        image.setImageResource(R.drawable.image06);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        blike = (ImageView) findViewById(R.id.likebutton);
        breply = (ImageView) findViewById(R.id.replybutton);

        blike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                String liketemp = like.getText().toString();
                int temp = Integer.valueOf(liketemp)+1;
                params.add("likecount", Integer.toString(temp));
                IllPercentClient.put("/likes/" +no, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(ArticleDetailActivity.this, "♥", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(ArticleDetailActivity.this, "fail: " + statusCode +"/no: "+no, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        breply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleDetailActivity.this, ArticleCommentActivity.class);
                intent.putExtra("no", no);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
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
                Intent intent = new Intent(ArticleDetailActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }


    // Tool bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
