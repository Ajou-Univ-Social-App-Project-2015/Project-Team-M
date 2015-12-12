package kr.ac.ajou.media.project_team_m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ArticleCommentActivity extends AppCompatActivity {

    public String no, email, nick;
    private ListView listView;
    ReplyAdapter replyAdapter;

    private EditText editCont;
    private ImageView input;

    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_reply);

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        editCont = (EditText) findViewById(R.id.replycontent);
        input = (ImageView) findViewById(R.id.replyimagebutton);

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 뒤로
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_lime);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleCommentActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.replyview);
        replyAdapter = new ReplyAdapter(this);
        listView.setAdapter(replyAdapter);
        adapterFunction(replyAdapter);

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cont = editCont.getText().toString();

                if (cont.length() == 0)
                    Toast.makeText(ArticleCommentActivity.this, "내용을 다시 확인하세요.", Toast.LENGTH_SHORT).show();
                else {
                    RequestParams params = new RequestParams();
                    params.add("writer", nick);
                    params.add("cont", cont);
                    params.add("article_no", no);

                    IllPercentClient.post("/comments", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(ArticleCommentActivity.this, "작성!", Toast.LENGTH_SHORT).show();
                            editCont.setText("");
                            Intent intent = new Intent(ArticleCommentActivity.this, ArticleCommentActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("nick", nick);
                            intent.putExtra("no", no);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(ArticleCommentActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // 댓글 카운트
                    IllPercentClient.get("/details/" + no, null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                            try {
                                JSONObject response = success.getJSONObject(0);
                                int count = Integer.valueOf(response.getString("commentcount"));

                                RequestParams params2 = new RequestParams();
                                int temp = count + 1;
                                params2.add("commentcount", Integer.toString(temp));
                                IllPercentClient.put("/comments/" + no, params2, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        //Toast.makeText(ArticleCommentActivity.this, "!", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        //Toast.makeText(ArticleCommentActivity.this, "fail: " + statusCode + "/no: " + no, Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void adapterFunction(ReplyAdapter adapter) {
        final ReplyAdapter a = adapter;

        IllPercentClient.get("/comments/" + no, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                try {
                    for (int i = 0; i < success.length(); i++) {
                        JSONObject response = success.getJSONObject(i);
                        String date = response.getString("createdate").substring(9, 28);
                        a.addItem(response.getString("writer"), response.getString("cont"), date);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    // Adapter
    public class ReplyAdapter extends BaseAdapter {

        private Context context;
        public ArrayList<ReplyItem> listdata = new ArrayList<ReplyItem>();

        public ReplyAdapter(Context context) {
            super();
            this.context = context;
        }


        @Override
        public int getCount() {
            return listdata.size();
        }

        @Override
        public Object getItem(int position) {
            return listdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.form_replylayout, null);

                holder.cont = (TextView) convertView.findViewById(R.id.content);
                holder.writer = (TextView) convertView.findViewById(R.id.username);
                holder.date = (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            } else holder = (ViewHolder) convertView.getTag();

            ReplyItem replyItem = listdata.get(position);
            holder.cont.setText(replyItem.getCont());
            holder.date.setText(replyItem.getDate());
            holder.writer.setText(replyItem.getWriter());

            return convertView;
        }

        private class ViewHolder {
            public TextView writer, cont, date;
        }

        public void addItem(String writer, String cont, String date) {
            ReplyItem add = new ReplyItem();
            add.setCont(cont);
            add.setWriter(writer);
            add.setDate(date);
            listdata.add(add);
        }
    }
}
