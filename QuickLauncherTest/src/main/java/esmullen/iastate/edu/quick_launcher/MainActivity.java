package esmullen.iastate.edu.quick_launcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btnOne);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOne = new Intent(getApplicationContext(), SecondActivity.class);
                intentOne.putExtra("StringKeyOne", "HELLO, WORLD!");
                startActivity(intentOne);
            }
        });
        Button btn2 = (Button) findViewById(R.id.btnTwo);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String google = "https://www.google.com";
                Uri webAddress = Uri.parse(google);
                Intent intentTwo = new Intent(Intent.ACTION_VIEW, webAddress);
                if (intentTwo.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentTwo);
                }

            }
        });
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intentThree = getPackageManager().getLaunchIntentForPackage("esmullen.iastate.edu.additiontest");
                if (intentThree != null) {
                    startActivity(intentThree);
                }
            }
        });
    }
}
