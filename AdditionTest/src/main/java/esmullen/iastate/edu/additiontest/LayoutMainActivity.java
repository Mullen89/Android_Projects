package esmullen.iastate.edu.additiontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LayoutMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Button addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText firstNumEditText = (EditText) findViewById(R.id.firstNumEditText);
                EditText secondNumEditText = (EditText) findViewById(R.id.secondNumEditText);
                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

                resultTextView.setText(addNumbers(firstNumEditText, secondNumEditText) + "");
            }
        });
    }
    public int addNumbers(EditText num1, EditText num2){
        int number1 = Integer.parseInt(num1.getText().toString());
        int number2 = Integer.parseInt(num2.getText().toString());
        return number1 + number2;
    }
}
