package com.example.kamalksharma.filemanager.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.dropbox.core.android.Auth;
import com.example.kamalksharma.filemanager.R;


public class MainActivity extends AppCompatActivity {
    private Button msignInBtn;
    private Button mlocalStorageBtn;
    private int mLoginCount;
    private boolean isLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLocalStorage = true;
        msignInBtn = (Button) findViewById(R.id.dropboxLoginBtn);
        mlocalStorageBtn = (Button) findViewById(R.id.deviceMemoryBtn);
        msignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocalStorage = false;
                if (Auth.getOAuth2Token() != null) {
                    Toast.makeText(getApplicationContext(), "login ", Toast.LENGTH_SHORT).show();
                    getAccessToken();
                } else {
                    Auth.startOAuth2Authentication(MainActivity.this, getString(R.string.APP_KEY));
                }

            }
        });
        mlocalStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocalStorage = true;
                Intent startIntent = new Intent(MainActivity.this, FileListViewActivity.class);
                startIntent.putExtra("isLocalStorage", true);
                startActivity(startIntent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mLoginCount == 0 && !isLocalStorage) {
            getAccessToken();
            isLocalStorage = false;
        } else if (!isLocalStorage) {
            mLoginCount--;
            isLocalStorage = false;
        }

    }
    public void getAccessToken() {
        String accessToken = Auth.getOAuth2Token();
        if (accessToken != null) {
            mLoginCount++;
            Intent intent = new Intent(MainActivity.this, FileListViewActivity.class);
            intent.putExtra("isLocalStorage", false);
            startActivity(intent);
        }
    }

}
