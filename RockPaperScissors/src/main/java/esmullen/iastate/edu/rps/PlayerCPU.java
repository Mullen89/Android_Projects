package esmullen.iastate.edu.rps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerCPU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cpu);

        Button rock = (Button) findViewById(R.id.PCbtn1);
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRock = new Intent(getApplicationContext(), PCScorePage.class);
                intentRock.putExtra("StringRock", "You chose ROCK!");
                startActivity(intentRock);
            }
        });

        Button paper = (Button) findViewById(R.id.PCbtn2);
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPaper = new Intent(getApplicationContext(), PCScorePage.class);
                intentPaper.putExtra("StringPaper", "You chose PAPER!");
                startActivity(intentPaper);
            }
        });

        Button scissors = (Button) findViewById(R.id.PCbtn3);
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentScissors = new Intent(getApplicationContext(), PCScorePage.class);
                intentScissors.putExtra("StringScissors", "You chose SCISSORS!");
                startActivity(intentScissors);
            }
        });
    }
}
