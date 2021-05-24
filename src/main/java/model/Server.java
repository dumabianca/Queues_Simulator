package model;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Server extends Thread {
    private BlockingQueue<Task> tasks;
    protected int waitingTime;
    public Server()
    {
        tasks=new LinkedBlockingQueue<>() ;
        waitingTime=0;
    }
    public void addTask(Task newTask)
    {
        this.tasks.add(newTask);
        waitingTime=waitingTime+newTask.getServiceTime();
    }
    public void removeTask()
    {
        this.tasks.remove();

    }
    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public void show()
    {
        if(this.getTasks().size()<1)
            System.out.println("closed");
        else
            System.out.println(this.getTasks());
    }
    @Override
    public void run() {

        while(true) {
            if (this.tasks.size() > 0) {
                if (this.tasks.peek().getServiceTime() > 0) {
                    //System.out.println(this.tasks.get(0).getServiceTime());
                    this.waitingTime--;
                    this.tasks.peek().setServiceTime(this.tasks.peek().getServiceTime() - 1);
                }
                if (this.tasks.peek().getServiceTime() == 0)
                    this.removeTask();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
