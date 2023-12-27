package com.example.chatsphere;


import static com.example.chatsphere.R.id.newgroup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.chatsphere.ui.main.SectionsPagerAdapter;
import com.example.chatsphere.databinding.ActivityTabBinding;

public class TabAct extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        // tabs colors
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF000000"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabs.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#ffffff"));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            }
        });

        binding.menuTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(TabAct.this, binding.menuTab);
                popupMenu.setOnMenuItemClickListener(TabAct.this); // create a method for this in the same class
                popupMenu.inflate(R.menu.menufile);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        // TODO: add the functionality for the menu items
        if (menuItem.getItemId() == R.id.newgroup) {
            Toast.makeText(this, "New Group", Toast.LENGTH_SHORT).show();
            return true;

        } else if (menuItem.getItemId() == R.id.profile) {

            Intent intent = new Intent(TabAct.this, profile.class);
            startActivity(intent);
            return true;


        } else if (menuItem.getItemId() == R.id.privacy) {
            Toast.makeText(this, "Privacy", Toast.LENGTH_SHORT).show();
            return true;

        }


        return false;
    }
}
