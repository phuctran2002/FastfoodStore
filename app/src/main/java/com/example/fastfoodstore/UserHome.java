package com.example.fastfoodstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout userDrawer;
    private NavigationView userNavigation;
    private Toolbar userToolBar;
    private FrameLayout userFrameLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        userDrawer = findViewById(R.id.user_drawer_layout);
        userNavigation = findViewById(R.id.user_navigation_view);
        userToolBar = findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(this, userDrawer, R.string.open, R.string.close);

        userToolBar.setTitle("Fastfood Store");
        setSupportActionBar(userToolBar);
        userDrawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userNavigation.setNavigationItemSelectedListener(this);

        setFragment(new HomePage());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.user_drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void displaySelectedListner(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_user_home:
                fragment = new HomePage();
                break;
            case R.id.nav_user_account:
                          Intent a = new Intent(getApplicationContext(), profile_main.class);
                            startActivity(a);
                break;
            case R.id.nav_user_cart:
                fragment = new CartItem();
                break;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.user_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        displaySelectedListner(menuItem.getItemId());
        return false;
    }
}