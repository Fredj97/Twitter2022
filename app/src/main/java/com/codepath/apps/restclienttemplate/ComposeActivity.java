package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

public static final String TAG = "ComposeActivity";
    public static final int MAX_LENGTH =200;

    EditText etCompose;
    Button btnTweet;
    TwitterClient client;
    Button DraftButton;
    TextView tvCompteur;
    FloatingActionButton ReturnButton;

    public void showAlertdialogButtonClicked(View view){
        //Set the alert builder
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("You can save what you texted in the draft and post it later");
        builder.setTitle("Attention!");

        //Add the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent= new Intent(ComposeActivity.this,TimelineActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent= new Intent(ComposeActivity.this, TimelineActivity.class);
                startActivity(intent);

            }
        });
        //creating dialog box
        AlertDialog alert=builder.create();
        //Alert setTitle("AlertDialogExample")
        alert.show();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose=findViewById(R.id.etCompose);
        btnTweet=findViewById(R.id.btnTweet);
        DraftButton=findViewById(R.id.DraftButton);
        ReturnButton=findViewById(R.id.ReturnButton);
        tvCompteur=findViewById(R.id.tvCompteur);
        client= TwitterApp.getRestClient(this);

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCompteur.setText(String.valueOf(MAX_LENGTH-charSequence.length()));
                ReturnButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String tweetContent = etCompose.getText().toString();
                        if (charSequence.length() > 0) {
                            Log.i("hm", "good");
                            String filename = "myfile" + ".txt";
                            String fileContents = tweetContent;
                            try (FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND)) {
                                fos.write(fileContents.getBytes(StandardCharsets.UTF_8));
                                fos.flush();
                                fos.close();
                                Log.i("yess", "ok save draft");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            showAlertdialogButtonClicked(view);
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        DraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComposeActivity.this,SaveActivity.class);
                startActivity(intent);

            }
        });

        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.length()>0){
                    Log.i("hm","good");
                    showAlertdialogButtonClicked(view);
                }
                if(tweetContent.isEmpty() ){
                    Intent intent = new Intent(ComposeActivity.this,TimelineActivity.class);
                    startActivity(intent);

                }

            }
        });

        //Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet can not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                //Make API call to twitter to publish the tweet

               client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                   @Override
                   public void onSuccess(int statusCode, Headers headers, JSON json) {
                       Log.i(TAG, "SIKSE POU PIBLIYE");
                       try {
                           Tweet tweet = Tweet.fromJson(json.jsonObject);
                           Log.i(TAG, "sikse" + tweet.body);
                           Intent intent = new Intent();
                           intent.putExtra("tweet", Parcels.wrap(tweet));
                           setResult(RESULT_OK, intent);
                           finish();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }

                   @Override
                   public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                         Log.i(TAG, "ECHEC", throwable);
                   }
               });
            }

        });

    }
}