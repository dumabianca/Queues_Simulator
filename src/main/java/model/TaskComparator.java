package model;

import model.Task;

import java.util.Comparator;
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if(o1.getArrivalTime()>o2.getArrivalTime())
            return 1;
        else return -1;

    }



}
