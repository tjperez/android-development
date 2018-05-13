package com.rray.mytaskmanager.TabFragments;

import java.io.Serializable;

/**
 * Created by LENOVO on 03-Nov-17.
 */
public class TaskModel implements Serializable{

    private String taskName;
    private boolean isSelected;

    public TaskModel(String taskName, boolean isSelected) {
        this.taskName = taskName;
        this.isSelected = isSelected;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {

        this.taskName = taskName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
