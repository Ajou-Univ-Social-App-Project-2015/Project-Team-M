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

/**
 * Created by apple7 on 2015-11-16.
 */
public class InputActivity extends AppCompatActivity {


    private EditText title, content;
    private Button input;
    private LinearLayout keysCheckbox;
    private String[] keywords;

    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_input);

        title = (EditText) findViewById(R.id.input_title);
        content = (EditText) findViewById(R.id.input_content);
        input = (Button) findViewById(R.id.button_input);

        keysCheckbox = (LinearLayout) findViewById(R.id.keywords_checkbox);
        keywords = getResources().getStringArray(R.array.keywords);
        for (int i=0; i<keywords.length; i++) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(keywords[i]);
            keysCheckbox.addView(checkBox);
        }

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // button
    public void onClick(View view) {

        for(int i=0; i<keysCheckbox.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) keysCheckbox.getChildAt(i);
            if (checkBox.isChecked()) {
                Toast.makeText(getApplicationContext(), checkBox.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
