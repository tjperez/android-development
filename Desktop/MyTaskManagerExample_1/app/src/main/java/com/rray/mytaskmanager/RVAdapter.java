package com.rray.mytaskmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.rray.mytaskmanager.TabFragments.TaskModel;



public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
//ViewHolder ia a inner class of RVAdapter class.It is declared at the bottom

    List<TaskModel> task_list = new ArrayList<>();

    public RVAdapter(List<TaskModel> task_list) {

        this.task_list = task_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void addTask(String str) {

        task_list.add(new TaskModel(str, false));
        notifyItemInserted(task_list.size());
        notifyItemRangeChanged(0, task_list.size());

    }

    public void UpdateTask(int position,TaskModel taskModel){

        task_list.remove(position);
        task_list.add(position,taskModel);
        notifyItemChanged(position);
        notifyDataSetChanged();

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos = position;
        holder.task_name.setText(task_list.get(position).getTaskName());
        holder.chkSelected.setChecked(task_list.get(position).isSelected());
        holder.chkSelected.setTag(task_list.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                TaskModel model = (TaskModel) cb.getTag();

                model.setSelected(cb.isChecked());
                task_list.get(pos).setSelected(cb.isChecked());

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                deleteItemFromList(v, pos);

            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editItemFromList(v, pos);


            }
        });

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                viewItemFromList(v, pos);


            }
        });

    }

    @Override
    public int getItemCount() {
        return task_list.size();
    }


    // confirmation dialog box to delete an unit
    private void deleteItemFromList(View v, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        //builder.setTitle("Dlete ");
        builder.setMessage("Delete Task ?")
                .setCancelable(false)
                .setPositiveButton("CONFIRM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                task_list.remove(position);
                                notifyDataSetChanged();


                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        builder.show();

    }


    //  dialog box to edit an item
    private void editItemFromList(final View v, final int position)
    {

        LayoutInflater li = LayoutInflater.from(v.getContext());
        View alertLayout = li.inflate(R.layout.input_box, null);


        final EditText inputTask = (EditText) alertLayout.findViewById(R.id.txtinput);


        String  taskVal= task_list.get(position).getTaskName().toString();
        inputTask.setText(taskVal);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        // this is set the view from XML inside AlertDialog
        builder.setView(alertLayout);
        builder.setTitle("Edit Task");
        builder.setMessage("What do you want to do?");
        builder.setCancelable(false);


        builder.setPositiveButton("UpdateTask",new DialogInterface.OnClickListener()

                {
                    public void onClick (DialogInterface dialog,int id){

                        //Code to add new item to RecyclerView
                        String editedTaskVal = String.valueOf(inputTask.getText());
                        TaskModel taskModel = new TaskModel(editedTaskVal ,false);
                        UpdateTask(position, taskModel);
                        Toast.makeText(v.getContext(), "Updated Task", Toast.LENGTH_SHORT).show();


                    }
                }

        );
        builder.setNegativeButton("Cancel",null);
        Toast.makeText(v.getContext(), "Update Cancelled", Toast.LENGTH_SHORT).show();
        builder.create().show();

    };

    //  dialog box to view a row
    private void viewItemFromList(final View v, final int position)
    {

        LayoutInflater li = LayoutInflater.from(v.getContext());
        View alertLayout = li.inflate(R.layout.view_box, null);
        final TextView TaskVal = (TextView) alertLayout.findViewById(R.id.txtTaskVal);

        String  taskVal= task_list.get(position).getTaskName().toString();

        TaskVal.setText(taskVal);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(alertLayout);
        builder.setTitle("View Task");
        builder.setCancelable(false);
        builder.setPositiveButton("DeleteTask",new DialogInterface.OnClickListener()

                {
                    public void onClick (DialogInterface dialog,int id){

                        deleteItemFromList( v,position);


                    }
                }

        );
        builder.setNegativeButton("Cancel",null);
        builder.create().show();

    };


    public void setFilter(List<TaskModel> taskModels){
        task_list = new ArrayList<>();
        task_list.addAll(taskModels);
        notifyDataSetChanged();
    }

    //Inner Class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView task_name;
        public ImageButton btn_delete;
        public ImageButton btn_edit;
        public CheckBox chkSelected;
        public View layout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            layout = itemLayoutView;

            task_name = (TextView) itemLayoutView.findViewById(R.id.txt_Name);
            btn_delete = (ImageButton) itemLayoutView.findViewById(R.id.btn_delete_unit);
            btn_edit = (ImageButton) itemLayoutView.findViewById(R.id.btn_edit_unit);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chk_selected);

        }
    }


}