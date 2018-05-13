package com.rray.mytaskmanager.TabFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.rray.mytaskmanager.R;
import com.rray.mytaskmanager.RVAdapter;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by LENOVO on 03-Nov-17.
 */
public class PersonalTaskFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private RVAdapter mAdapter;
    List<TaskModel> task_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal_task_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RVAdapter(task_list);
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        task_list.add(new TaskModel("Gym", false));
//        task_list.add(new TaskModel("Call Plumber", false));
//        task_list.add(new TaskModel("Call Electrician", false));
//        task_list.add(new TaskModel("Call Dietcian", false));
//        task_list.add(new TaskModel("Fix appointment with doctor", false));
//        task_list.add(new TaskModel("Order cooking gas", false));
//        task_list.add(new TaskModel("Do gardening", false));


        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add a new task");
                builder.setMessage("What do you want to do?");
                final EditText inputField = new EditText(getActivity());
                builder.setView(inputField);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Code to add new item to RecyclerView
                        String newTaskVal = String.valueOf(inputField.getText());
                        Integer index = mAdapter.getItemCount();
                       mAdapter.addTask(newTaskVal);
                        Toast.makeText(getActivity(), "Added New Task", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();


            }


        });

        mAdapter = new RVAdapter(task_list);
        recyclerView.setAdapter(mAdapter);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem= menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) (searchItem.getActionView());
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<TaskModel> filteredModelList = filter(task_list, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<TaskModel> filter(List<TaskModel> models, String query) {
        query = query.toLowerCase();

        final List<TaskModel> filteredModelList = new ArrayList<>();
        for (TaskModel model : models) {
            final String text = model.getTaskName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
