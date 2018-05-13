package com.rray.mytaskmanager;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

import com.rray.mytaskmanager.TabFragments.TaskModel;
import com.rray.mytaskmanager.TabFragments.BusinessTaskFragment;
import com.rray.mytaskmanager.TabFragments.PersonalTaskFragment;




public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView mRecyclerView;



    List<TaskModel> tasklist = new ArrayList<>();
    private RVAdapter adapter = new RVAdapter(tasklist);
    private TaskModel newTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        ImageButton btnEdit = (ImageButton) findViewById(R.id.btn_edit_unit);
        //Wanted to display tooltip 'Edit' for the button,but tool tip is available only for API Level 26 and above
        //btnEdit.setHovered(true);


        actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager)

                findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout)

                findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PersonalTaskFragment(), "Personal");
        adapter.addFragment(new BusinessTaskFragment(), "Business");

        viewPager.setAdapter(adapter);

    }


    public TaskModel getNewTask() {

        return newTask;
    }

    public void setNewTask(TaskModel newTask) {

        this.newTask = newTask;
    }

}