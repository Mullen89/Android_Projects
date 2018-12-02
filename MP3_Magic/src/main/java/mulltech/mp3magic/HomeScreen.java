package mulltech.mp3magic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    private Button convertBtn;
    private TextView urlTV;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        /**
         * Grabs string URL address from text box
         */
        urlTV = (TextView) findViewById(R.id.urlTV);
        url = urlTV.getText().toString();

        /**
         *
         */
        convertBtn = (Button) findViewById(R.id.btnConvert);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
