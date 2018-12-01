package mullen.liftnotes;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
//    private AlarmManager alarmMgr;
//    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extra = getIntent().getExtras();

        mViewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager.setCurrentItem(1);

//        scheduleJob();

        if(extra != null) {
            int extraInt = extra.getInt("frag");
            switch (extraInt){
                case 0:
//                    Toast.makeText(getApplicationContext(), Integer.toString(extraInt), Toast.LENGTH_LONG).show();
                    mViewPager.setCurrentItem(0);
                    break;
                case 1:
//                    Toast.makeText(getApplicationContext(), Integer.toString(extraInt), Toast.LENGTH_LONG).show();
                    mViewPager.setCurrentItem(1);
                    break;
                case 2:
//                    Toast.makeText(getApplicationContext(), Integer.toString(extraInt), Toast.LENGTH_LONG).show();
                    mViewPager.setCurrentItem(2);
                    break;
            }
        }
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPageAdapter adapter = new TabsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new TabFragment0(), "Personal Records");
        adapter.addFragment(new TabFragment1(), "Workouts");
        adapter.addFragment(new TabFragment2(), "Diet");

        viewPager.setAdapter(adapter);
    }

    /**
     * These methods allow for the app to implement the AlarmReceiver class
     * so it can perform automatic functions at specific times.
     */
//    private void scheduleJob(){
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, 11);
//        c.set(Calendar.MINUTE, 59);
//        c.set(Calendar.SECOND, 0);
//
//        startAlarm(c);
//    }
//    private void startAlarm(Calendar c){
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
//
//        if(c.before(Calendar.getInstance())){
//            c.add(Calendar.DATE, 1);
//        }
//        am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
//    }
}
