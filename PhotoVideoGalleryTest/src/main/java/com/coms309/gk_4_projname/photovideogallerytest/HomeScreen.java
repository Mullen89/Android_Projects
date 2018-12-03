package com.coms309.gk_4_projname.photovideogallerytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.ib.custom.toast.CustomToast;

public class HomeScreen extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btn = (Button) findViewById(R.id.testBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.makeText(getApplicationContext(), Toast.LENGTH_SHORT,CustomToast.SUCCESS,
                        "Toast is working",false).show();
            }
        });
    }
}
