package kr.ac.ajou.media.project_team_m;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("ALL")
public class LoginCheckActivity extends AppCompatActivity implements View.OnClickListener{

    private String uid = "";
    private EditText unick, uemail;
    private Button register;

    private static final String REGISTER_URL = "http://webauthoring.ajou.ac.kr/~sap15M/register.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_register);

        Intent intent = getIntent();
        uid = intent.getStringExtra("user");
        unick = (EditText) findViewById(R.id.text_nick);
        uemail = (EditText) findViewById(R.id.text_email);
        register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == register) {
            registerUser();
        }
    }

    private void registerUser() {
        String email = uemail.getText().toString().trim().toLowerCase();
        String nick = unick.getText().toString().trim().toLowerCase();

        register(email, nick, uid);
    }

    private void register(String email, String nick, String id) {
        String urlSuffix = "?uemail="+email+"&unick="+nick+"&uid="+id;

        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginCheckActivity.this, "Please wait.", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;

                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String result = bufferedReader.readLine();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute(urlSuffix);
    }
}
