package com.matyas.krista.pylt.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.matyas.krista.pylt.Calculator.Calculator;
import com.matyas.krista.pylt.Commons.AppColors;
import com.matyas.krista.pylt.Database.AppDatabase;
import com.matyas.krista.pylt.EIObjects.EIObject;
import com.matyas.krista.pylt.EIObjects.EITag;
import com.matyas.krista.pylt.EIObjects.EIType;
import com.matyas.krista.pylt.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadData();
        createPieChart(EIType.OVERALL);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button buttonBalance = (Button) findViewById(R.id.main_balance);
        Button buttonSettings = (Button) findViewById(R.id.main_settings);

        buttonBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_balance) {
            Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        final AppDatabase adb = AppDatabase.getFileDatabase(getApplicationContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
//                // deletes all table entries
//                adb.eiObject().nukeTable();
                EIObject.getAllObjects().clear();
                EITag.getTags().clear();
                EIObject[] eiObjects = adb.eiObject().loadAllObjects();
                for (EIObject eiObject : eiObjects) {
                    EITag.addTag(eiObject.getTag().getName(), eiObject.getAmount(), eiObject.getEitype());
                }
                return 1;
            }
        }.execute();
    }

    private void createPieChart(EIType type) {
        PieChart chart = (PieChart) findViewById(R.id.chart_mini);
        chart.clear();
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((Calculator.getEI(EIType.INCOME))));
        entries.add(new PieEntry((Calculator.getEI(EIType.EXPENSE))));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setValueTextColor(Color.TRANSPARENT);
        dataSet.setColors(AppColors.getColors());
        PieData pieData = new PieData(dataSet);
        chart.setData(pieData);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.setCenterText("");
        chart.invalidate();
    }
}
