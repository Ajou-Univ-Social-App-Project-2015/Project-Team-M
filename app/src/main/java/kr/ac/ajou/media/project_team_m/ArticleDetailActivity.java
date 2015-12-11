package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView keywords, date, writer, like, reply, cont;
    private ImageView blike, breply;
    private String no, email, nick;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_articledetail);

        keywords = (TextView) findViewById(R.id.keywords);
        date = (TextView) findViewById(R.id.date);
        writer = (TextView) findViewById(R.id.writername);
        like = (TextView) findViewById(R.id.liketext);
        reply = (TextView) findViewById(R.id.replytext);
        cont = (TextView) findViewById(R.id.content);

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        IllPercentClient.get("/details/"+no, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                try {
                    JSONObject response = success.getJSONObject(0);
                    keywords.setText(response.getString("keyarray"));
                    date.setText(response.getString("createdate").substring(9, 28));
                    writer.setText("Written by "+response.getString("writer"));
                    like.setText(response.getString("likecount"));
                    reply.setText(response.getString("commentcount"));
                    cont.setText(response.getString("cont"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        blike = (ImageView) findViewById(R.id.likebutton);
        breply = (ImageView) findViewById(R.id.replybutton);

    }
}
