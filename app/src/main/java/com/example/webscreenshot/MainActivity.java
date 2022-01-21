package com.example.webscreenshot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {

    private Button submit;
    private TextInputLayout url;
    private ImageView imageview;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = findViewById(R.id.submit);
        url = findViewById(R.id.url);
        imageview = findViewById(R.id.imageview);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Screenshot Generator");
        mProgressDialog.setMessage("Please wait, generating screenshot...");
        mProgressDialog.setCancelable(true);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkinfo = cm.getActiveNetworkInfo();

                if (networkinfo != null && networkinfo.isConnected() == true) {
                    new ApiProcess1().execute();
                } else {
                    display_internet_label();
                }
            }
        });
    }


    private void display_internet_label() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("oops!")
                .setMessage("Check your internet connection")
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .create();

        dialog.show();
    }

    public class ApiProcess1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            mProgressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {

            String response = "https://v1.nocodeapi.com/praveenkumar100501/screen/enFcKjMdtButtRjU/screenshot?url=" + validate_url(url.getEditText().getText().toString()) +"";
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                Glide.with(MainActivity.this).load(s).into(imageview);
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String validate_url(String s) {

        String imgurl = "";

        if (url != null) {
            if (!s.isEmpty()) {

                if (s.contains("www")) {
                    imgurl = s;
                } else {
                    imgurl = "www." + s;
                }

                if (s.contains("http")) {
                    imgurl = s;
                } else {
                    imgurl = "http://" + s;
                }
            }
        }
        return imgurl;
    }
}