package esmullen.iastate.edu.rps;

        import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

public class PCScorePage extends AppCompatActivity {

    int choice = 0;
    String CPUchoice = "";
    int numChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcscore_page);

//************************************PLAYER DECISION***********************************************
        if(getIntent().hasExtra("StringRock")){
            TextView tv = (TextView) findViewById(R.id.PCtvPlyr);
            String text = getIntent().getExtras().getString("StringRock");
            choice = 0;
            tv.setText(text);
        }
        else if(getIntent().hasExtra("StringPaper")){
            TextView tv = (TextView) findViewById(R.id.PCtvPlyr);
            String text = getIntent().getExtras().getString("StringPaper");
            choice = 1;
            tv.setText(text);
        }
        else if(getIntent().hasExtra("StringScissors")){
            TextView tv = (TextView) findViewById(R.id.PCtvPlyr);
            String text = getIntent().getExtras().getString("StringScissors");
            choice = 2;
            tv.setText(text);
        }

//************************************COMPUTER DECISION*********************************************
        numChoice = computerChoice();
        if(numChoice == 0) CPUchoice = "Computer chooses ROCK!";
        else if(numChoice == 1) CPUchoice = "Computer chooses PAPER!";
        else if(numChoice == 2) CPUchoice = "Computer chooses SCISSORS!";

        TextView CPU = (TextView) findViewById(R.id.PCtvCPU);
        CPU.setText(CPUchoice);

//*******************************************GAME***************************************************
        TextView game = (TextView) findViewById(R.id.PCgameResult);
        game.setText(gameResult(choice, numChoice));
    }

    public int computerChoice(){
        Random rand = new Random();
        int CPUweapon = rand.nextInt(3);
        return CPUweapon;
    }

    public String gameResult(int player, int CPU){
        if(player == 0 && CPU == 0) return "DRAW!";
        else if(player == 0 && CPU == 1) return "CPU wins! You LOSE!";
        else if(player == 0 && CPU == 2) return "You WIN!";

        else if(player == 1 && CPU == 0) return "You WIN!";
        else if(player == 1 && CPU == 1) return "DRAW!";
        else if(player == 1 && CPU == 2) return "CPU wins! You LOSE!";

        else if(player == 2 && CPU == 0) return "CPU wins! You LOSE!";
        else if(player == 2 && CPU == 1) return "You WIN!";
        else {return "DRAW!";}
    }
}
