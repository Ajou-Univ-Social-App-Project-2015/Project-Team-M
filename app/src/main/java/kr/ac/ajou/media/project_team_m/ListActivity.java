package kr.ac.ajou.media.project_team_m;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ListActivity extends Activity {

    private String user = "";
    private TextView textView;

    // Tool bar Properties

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_list);

//        Intent intent = getIntent();
//        user = intent.getStringExtra("user");
//        textView = (TextView)findViewById(R.id.testuser);
//        textView.setText(user);

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, LoginCheckActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}
