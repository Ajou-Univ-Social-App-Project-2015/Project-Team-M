package kr.ac.ajou.media.project_team_m;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

        ArrayList detail = getListData();
        final ListView listView = (ListView) findViewById(R.id.activityview);
        listView.setAdapter(new ListAdapter(this, detail));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ListItem data = (ListItem) o;
                Toast.makeText(ListActivity.this, "Selected: "+ data, Toast.LENGTH_LONG).show();
            }
        });

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    // Adapter
    private ArrayList getListData() {
        ArrayList<ListItem> results = new ArrayList<ListItem>();

        ListItem data = new ListItem();
        data.setTitle("The Mist");
        data.setContent("My soul was floating above a moonlit sea\n" +
                "At the same time I was drowning, it felt somehow freeing\n" +
                "Enraptured by his eyes, the burning eyes of a supreme hypnotist\n" +
                "I followed him into the mist");
        data.setDate("2015-12-01-22:55-TUE");
        data.setLike(2);
        data.setReply(2);
        data.setWritername("Lucy");
        results.add(data);

        // add other data.....
        ListItem data2 = new ListItem();
        data2.setTitle("Please Don't Make Me Love You");
        data2.setContent("You're the one I think of soon as I awaken\n" +
                "Funny how the heart tells the mind what to do\n" +
                "I'm not sure I can go through all the joy and the pain\n" +
                "Much better now to let these dreams take flight");
        data2.setDate("2015-12-01-22:57-TUE");
        data2.setLike(8);
        data2.setReply(5);
        data2.setWritername("Mina");
        results.add(data2);

        ListItem data3 = new ListItem();
        data3.setTitle("Loving you keeps me alive");
        data3.setContent("Loving you keeps me alive, think again before you leave me\n" +
                "His love cannot be as true as the love I offer you\n" +
                "You're wasting time pretending you belong to him\n" +
                "Come to your senses");
        data3.setDate("2015-12-01-22:30-TUE");
        data3.setLike(3);
        data3.setReply(4);
        data3.setWritername("Xia");
        results.add(data3);
        return results;
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection Simplifiable If Statement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
