package com.coms309.gk_4_projname.colorchanger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ColorSwitchControl extends AppCompatActivity {

    //These are the value that are stored with the "shared preferences"
    public static final String COLOR_CHANGE_PREFERENCES = "pref";
    public static final String COLOR_CHANGE_THEME = "theme";
    public static final String COLOR_CASE_STATEMENT = "choice";

    //The color theme value that is used in the switch statement
    //Stored in COLOR_CASE_STATEMENT
    public String color = "";

    //boolean values that determine whether the color theme the user picks is active or not
    public boolean dark;
    public boolean red;
    public boolean orange;
    public boolean yellow;
    public boolean green;
    public boolean cyan;
    public boolean purple;
    public boolean pink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//****************EVERYTHING IN THIS GROUPING MUST BE INCLUDED AT THE TOP OF EVERY ACTIVITY OR FRAGMENT**********************************
//region CODE TO ALLOW COLOR PICKER
        //Pulls up the user's preferences as to what color/theme they want.
        //These preferences are edited by the "changeDarkLight" and "changeColors" methods.
        SharedPreferences prefColor = getSharedPreferences(COLOR_CHANGE_PREFERENCES, MODE_PRIVATE);

        //Whether or not a switch is toggled on or off.
        boolean changeColorTheme = prefColor.getBoolean(COLOR_CHANGE_THEME, false);

        //String value returned by "getString(string, string,)" method below. Return the color scheme
        String colorChoice = prefColor.getString(COLOR_CASE_STATEMENT, null);
        if(changeColorTheme == false) {
            setTheme(R.style.AppTheme);
            dark = false;
            red = false;
            orange = false;
            yellow = false;
            green = false;
            cyan = false;
            pink = false;
            purple = false;
        }
        else{
            switch (colorChoice){
                case "DarkLight": setTheme(R.style.AppTheme_Dark_NoActionBar);
                            dark = true;
                            break;
                case "Red": setTheme(R.style.RedTheme);
                            red = true;
                            break;
                case "Orange": setTheme(R.style.OrangeTheme);
                            orange = true;
                            break;
                case "Yellow": setTheme(R.style.YellowTheme);
                            yellow = true;
                            break;
                case "Green": setTheme(R.style.GreenTheme);
                            green = true;
                            break;
                case "Cyan": setTheme(R.style.CyanTheme);
                            cyan = true;
                            break;
                case "Purple": setTheme(R.style.PurpleTheme);
                            purple = true;
                            break;
                case "Pink": setTheme(R.style.PinkTheme);
                            pink = true;
                            break;
                default: setTheme(R.style.AppTheme);
                    dark = false;
                    red = false;
                    orange = false;
                    yellow = false;
                    green = false;
                    cyan = false;
                    pink = false;
                    purple = false;
                    break;
            }
        }
//endregion
//******************************************************************************************************************************************

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_switch_control);

//region Color switch initializations
        //This is the toggle switch to change the overall theme from light to dark (or color), or vice versa.
        Switch DarkLightSwitch = (Switch) findViewById(R.id.LightDarkToggle);
        Switch redColorSwitch = (Switch) findViewById(R.id.RedColorSwitchToggle);
        Switch orangeColorSwitch = (Switch) findViewById(R.id.OrangeColorSwitchToggle);
        Switch yellowColorSwitch = (Switch) findViewById(R.id.YellowColorSwitchToggle);
        Switch greenColorSwitch = (Switch) findViewById(R.id.GreenColorSwitchToggle);
        Switch cyanColorSwitch = (Switch) findViewById(R.id.CyanColorSwitchToggle);
        Switch purpleColorSwitch = (Switch) findViewById(R.id.PurpleColorSwitchToggle);
        Switch pinkColorSwitch = (Switch) findViewById(R.id.PinkColorSwitchToggle);

        //Sets the toggle switch to either the "checked" or "unchecked" position.
        DarkLightSwitch.setChecked(dark);
        redColorSwitch.setChecked(red);
        orangeColorSwitch.setChecked(orange);
        yellowColorSwitch.setChecked(yellow);
        greenColorSwitch.setChecked(green);
        cyanColorSwitch.setChecked(cyan);
        purpleColorSwitch.setChecked(purple);
        pinkColorSwitch.setChecked(pink);
//endregion

//region Color switch toggles
        //This activates when the switch is toggled.
        DarkLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean b) {
                color = "DarkLight";
                changeColors(b, color);
            }
        });
        redColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Red";
                changeColors(b, color);
            }
        });
        orangeColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Orange";
                changeColors(b, color);
            }
        });
        yellowColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Yellow";
                changeColors(b, color);
            }
        });
        greenColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Green";
                changeColors(b, color);
            }
        });
        cyanColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Cyan";
                changeColors(b, color);
            }
        });
        purpleColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Purple";
                changeColors(b, color);
            }
        });
        pinkColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                color = "Pink";
                changeColors(b, color);
            }
        });
//endregion
    }

//region Color change method
    //This method updates the user's preferences to use a color theme.
    //These preferences are stored on the user's phone to be drawn by the
    //"getSharedPreferences" whenever an app activity is created.
    //Method to change the color scheme (WIP)
    private void changeColors (boolean colorChoice, String theme){
        SharedPreferences.Editor editor = getSharedPreferences(COLOR_CHANGE_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(COLOR_CHANGE_THEME, colorChoice);
        editor.putString(COLOR_CASE_STATEMENT, theme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        //Restarts the activity with the user's preferences
        startActivity(intent);
    }
//endregion

}
