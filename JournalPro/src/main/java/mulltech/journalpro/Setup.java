package mulltech.journalpro;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Setup extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;
    private final String passwordKey = "password";
    private final String emailKey = "email";
    private EditText email1;
    private EditText email2;
    private EditText PIN1;
    private EditText PIN2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(Utility.loadValue(passwordKey, this) == null)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
            setContentView(R.layout.setup);

//*****************************************************PERMISSIONS*****************************************************
        /*
        This bit of code shows the pop-up to allow the usage of the user's device's storage system.
        This must be allowed in order to use the CSV exporter in the "Exercises" screen.
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            /*
             * This shows if permission is NOT granted
             */
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("In order to allow the saving of your files to the internal hard drive on your device," +
                                " this app needs access to your device's storage (media, files, etc..)," +
                                " otherwise if this app is deleted/uninstalled, all information will be lost.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Setup.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        STORAGE_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
            }
        }
//*********************************************************************************************************************

        email1 = (EditText) findViewById(R.id.enterEmail);
        email2 = (EditText) findViewById(R.id.reenterEmail);
        PIN1 = (EditText) findViewById(R.id.createPIN);
        PIN2 = (EditText) findViewById(R.id.reenterPIN);

        Button submit = (Button) findViewById(R.id.submitSetupBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStore = email1.getText().toString();
                String PIN_Store = PIN1.getText().toString();
                if(!isValidEmail(emailStore)){
                    Toast.makeText(getApplicationContext(), "Email is invalid.", Toast.LENGTH_LONG).show();
                } else if(!match(email1, email2)) {
                    Toast.makeText(getApplicationContext(), "Emails is do not match.", Toast.LENGTH_LONG).show();
                } else if(PIN_Store.equals("")) {
                    Toast.makeText(getApplicationContext(), "Pin# is not set.", Toast.LENGTH_LONG).show();
                } else if(!match(PIN1, PIN2)){
                    Toast.makeText(getApplicationContext(), "Pin#'s do not match.", Toast.LENGTH_LONG).show();
                } else if(PIN1.length() < 4){
                    Toast.makeText(getApplicationContext(), "Pin# must be 4 digits in length.", Toast.LENGTH_LONG).show();
                } else {
                    Utility.saveValue(emailStore, emailKey, getApplicationContext());
                    Utility.saveValue(PIN_Store, passwordKey, getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * This method is used to determine whether or not the emails or PIN#'s entered match.
     */
    private boolean match(EditText text1, EditText text2){
        boolean matches;
        String textCheck1 = text1.getText().toString();
        String textCheck2 = text2.getText().toString();
        if(textCheck1.equals(textCheck2)){
            matches = true;
        } else {
            matches = false;
        }
        return matches;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
