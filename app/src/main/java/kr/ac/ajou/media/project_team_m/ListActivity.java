package kr.ac.ajou.media.project_team_m;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
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
                    for (int i = success.length()-1; i > -1; i--) {
                        JSONObject response = success.getJSONObject(i);
                        String date = response.getString("createdate").substring(9,28);
                        a.addItem(response.getInt("no"), response.getString("title"), response.getString("cont"),
                                "Written by "+response.getString("writer"), date, response.getString("commentcount"),
                                response.getString("likecount"), response.getString("keyarray"), response.getString("randimage"));
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


    // Adapter

    public class ListAdapter extends BaseAdapter {
        private Context context;
        public ArrayList<ListItem> listdata = new ArrayList<ListItem>();

        public ListAdapter(Context context) {
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.form_listlayout, null);

                holder.ttitle = (TextView) convertView.findViewById(R.id.title);
                holder.tcontent = (TextView) convertView.findViewById(R.id.content);
                holder.twriter = (TextView) convertView.findViewById(R.id.writername);
                holder.tdate = (TextView) convertView.findViewById(R.id.date);
                holder.treply = (TextView) convertView.findViewById(R.id.replytext);
                holder.tlike = (TextView) convertView.findViewById(R.id.liketext);
                holder.tkeys = (TextView) convertView.findViewById(R.id.keywords);

                convertView.setTag(holder);
            } else holder = (ViewHolder) convertView.getTag();

            ListItem listItem = listdata.get(position);

            holder.ttitle.setText(listItem.getTitle());
            holder.tcontent.setText(listItem.getContent());
            holder.twriter.setText(listItem.getWriter());
            holder.tdate.setText(listItem.getDate());
            holder.treply.setText(listItem.getReply());
            holder.tlike.setText(listItem.getLike());
            holder.tkeys.setText(listItem.getKeys());

            final ImageView likebutton = (ImageView) convertView.findViewById(R.id.likebutton);
            ImageView replybutton = (ImageView) convertView.findViewById(R.id.replybutton);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            if(listItem.getRand().equals("1")) {
                imageView.setImageResource(R.drawable.image01);
            } else if(listItem.getRand().equals("2")) {
                imageView.setImageResource(R.drawable.image02);
            } else if(listItem.getRand().equals("3")) {
                imageView.setImageResource(R.drawable.image03);
            } else if(listItem.getRand().equals("4")) {
                imageView.setImageResource(R.drawable.image04);
            } else if(listItem.getRand().equals("5")) {
                imageView.setImageResource(R.drawable.image05);
            } else if(listItem.getRand().equals("6")) {
                imageView.setImageResource(R.drawable.image06);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent zoom = new Intent(context, ArticleDetailActivity.class);
                    int temp = listdata.get(position).getNo();
                    zoom.putExtra("no", Integer.toString(temp));
                    zoom.putExtra("email", email);
                    zoom.putExtra("nick", nick);
                    parent.getContext().startActivity(zoom);
                }
            });

            likebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    String liketemp = listdata.get(position).getLike();
                    int temp = Integer.valueOf(liketemp)+1;
                    params.add("likecount", Integer.toString(temp));
                    IllPercentClient.put("/likes/" +listdata.get(position).getNo(), params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(ListActivity.this, "♥", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(ListActivity.this, "fail: " + statusCode +"/no: "+listdata.get(position).getNo(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            replybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent zoom = new Intent(context, ArticleCommentActivity.class);
                    int temp = listdata.get(position).getNo();
                    zoom.putExtra("no", Integer.toString(temp));
                    zoom.putExtra("email", email);
                    zoom.putExtra("nick", nick);
                    parent.getContext().startActivity(zoom);
                }
            });

            return convertView;
        }

        private class ViewHolder {
            public TextView ttitle, tcontent, twriter, tdate, treply, tlike, tkeys;
        }

        public void addItem(int no, String title, String cont, String writer, String date, String reply, String like, String keys, String rand) {
            ListItem add = new ListItem();
            add.setNo(no);
            add.setTitle(title);
            add.setContent(cont);
            add.setWriter(writer);
            add.setDate(date);
            add.setReply(reply);
            add.setLike(like);
            add.setKeys(keys);
            add.setRand(rand);
            listdata.add(add);
        }
    }
}
