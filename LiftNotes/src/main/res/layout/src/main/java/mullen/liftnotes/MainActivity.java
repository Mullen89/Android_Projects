package mullen.liftnotes;

import android.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extra = getIntent().getExtras();

        mViewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

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
        adapter.addFragment(new TabFragment1(), "Workout");
        adapter.addFragment(new TabFragment2(), "Diet");

        viewPager.setAdapter(adapter);
    }
}
