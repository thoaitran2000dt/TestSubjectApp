package android.uit.testsubjectsapp;

import android.database.SQLException;
import android.os.Bundle;
import android.uit.testsubjectsapp.question.DBHelper;
import android.uit.testsubjectsapp.question.SearchQuestionFragment;
import android.uit.testsubjectsapp.score.ScoreFragment;
import android.uit.testsubjectsapp.subjects.CivicEducationFragment;
import android.uit.testsubjectsapp.subjects.CodingFragment;
import android.uit.testsubjectsapp.subjects.EnsignFragment;
import android.uit.testsubjectsapp.subjects.HistoryFragment;
import android.uit.testsubjectsapp.subjects.HomeFragment;
import android.uit.testsubjectsapp.subjects.CivicEducationFragment;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, homeFragment, homeFragment.getTag()).commit();

        DBHelper db = new DBHelper(this);

        /*try {
            db.deleteDataBase();
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "bi loi rui", Toast.LENGTH_SHORT).show();
        }*/

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if ( id == R.id.home) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, homeFragment, homeFragment.getTag()).commit();

        } else if ( id == R.id.m_gdcd) {
            CivicEducationFragment civicEducationFragment = new CivicEducationFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, civicEducationFragment, civicEducationFragment.getTag()).commit();

        } else if (id == R.id.m_th) {
            CodingFragment codingFragment = new CodingFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, codingFragment, codingFragment.getTag()).commit();

        } else if (id == R.id.m_ls) {
            HistoryFragment historyFragment = new HistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, historyFragment, historyFragment.getTag()).commit();

        } else if (id == R.id.m_qk) {
                EnsignFragment ensignFragment = new EnsignFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, ensignFragment, ensignFragment.getTag()).commit();

        } else if (id == R.id.search) {
            SearchQuestionFragment searchQuestionFragment = new SearchQuestionFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, searchQuestionFragment, searchQuestionFragment.getTag()).commit();
        } else if (id == R.id.score) {
            ScoreFragment scoreFragment = new ScoreFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, scoreFragment, scoreFragment.getTag()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}