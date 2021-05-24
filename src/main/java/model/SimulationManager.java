package model;//import java.lang.System.Logger;
//import java.lang.System.Logger;
import model.Server;
import view.UI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SimulationManager extends Thread {
    private List<Server> servers=new ArrayList<Server>();
    private int simulationTime;
    private int numberClients;
    private int numberQueues;
    private List<Task> generatedTasks;
    private int minServiceTime;
    private int maxServiceTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private float averageServiceTime;
    private float averageWaitingTime;
    private int peak=0;
    private Path path;
    private FileWriter file;
    private BufferedWriter output;
    private int ok=1;
    private UI frame1;
    private int peakTime=0;
    public SimulationManager(int simulationTime, int numberClients,int numberQueues,int minServiceTime,int maxServiceTime,int maxArrivalTime,int minArrivalTime, UI frame) 	{
        this.simulationTime=simulationTime;
        this.numberClients=numberClients;
        this.numberQueues=numberQueues;
        this.maxServiceTime=maxServiceTime;
        this.minServiceTime=minServiceTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minArrivalTime=minArrivalTime;
        this.generatedTasks=new ArrayList<Task>();
        for(int i=0;i<numberQueues;i++)
            servers.add(new Server());
        for(Server s: servers)
            s.start();
        generateRandomTasks();
        System.out.println(getGeneratedTasks());
        for(int i=0;i<this.generatedTasks.size();i++)
        {
            this.averageServiceTime=this.averageServiceTime+this.generatedTasks.get(i).getServiceTime();
        }
        this.averageServiceTime=this.averageServiceTime/numberClients;
        System.out.println("The average service time is "+this.averageServiceTime+"s");
        String fileContent = "";
        path = Paths.get(".\\test");
        try {
            Files.write(path, fileContent.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.frame1=frame;
    }
    public void generateRandomTasks()
    {
        int i;
        TaskComparator comparator=new TaskComparator();
        for(i=0;i<this.numberClients;i++)
        {

            int arrivalTime= (int) (Math.random() * (maxArrivalTime - minArrivalTime)) + minArrivalTime;
            int serviceTime=(int) (Math.random() * (maxServiceTime - minServiceTime)) + minServiceTime;
            this.generatedTasks.add(new Task(i+1,arrivalTime,serviceTime));
            Collections.sort(generatedTasks,comparator);
        }
    }

    public List<Task> getGeneratedTasks() {
        return generatedTasks;
    }
    public void setGeneratedTasks(List<Task> generatedTasks) {
        this.generatedTasks = generatedTasks;
    }
    public Server getMinimumServer()
    {
        Server min=this.getServers().get(0);
        for(Server s: this.getServers())
        {

            int aux=s.waitingTime;
            if(aux<min.waitingTime)
                min=s;
        }
        return min;
    }
    public List<Server> getServers() {
        return servers;
    }
    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
    public int getSimulationTime() {
        return simulationTime;
    }
    public int getNumberClients() {
        return numberClients;
    }
    public int getNumberQueues() {
        return numberQueues;
    }
    public int getMinServiceTime() {
        return minServiceTime;
    }
    public int getMaxServiceTime() {
        return maxServiceTime;
    }
    public int getMinArrivalTime() {
        return minArrivalTime;
    }
    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }
    public  int sumServer()
    {
        int count=0;
        for(int i=0;i<this.servers.size();i++)
        {
            count=count+this.servers.get(i).getTasks().size();
        }
        return count;
    }
    @Override
    public void run() {
        int currentTime=0;
        String resultCurrentTime="";
        while(currentTime<this.simulationTime )
        {

            System.out.println("Current time: "+currentTime);
            this.frame1.setCurrentTime("Current time: "+currentTime);
            resultCurrentTime="---------------------------------------\nCurrent time: "+currentTime+"\n";
            try {
                Files.write(path, resultCurrentTime.getBytes(),StandardOpenOption.APPEND);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            while(this.generatedTasks.size()>0 && this.generatedTasks.get(0).getArrivalTime()==currentTime)
            {
                Task t1=this.generatedTasks.remove(0);
                this.getMinimumServer().addTask(t1);
            }

            this.frame1.viewStatus(this.getServers(),this.getGeneratedTasks());

            int count =0;
            for(int i=0;i<servers.size();i++)
            {
                count=count+servers.get(i).getTasks().size();
            }
            if(count>this.peak)
            {
                peakTime=currentTime;
                peak=count;
            }

            if (count==0)
                ok=0;

            if(this.generatedTasks.size()>0) {
                System.out.println("Waiting Clients: "+this.generatedTasks);
                String resultWaitingClients="";
                resultWaitingClients=resultWaitingClients+"\nWaiting Clients: "+this.generatedTasks+"\n";
                try {
                    Files.write(path, resultWaitingClients.getBytes(),StandardOpenOption.APPEND);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("\nWaiting Clients: none");
                String WaitingClientsNone="\nWaiting clients:none\n";
                try {
                    Files.write(path, WaitingClientsNone.getBytes(),StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(int i=0;i<servers.size();i++)
                this.averageWaitingTime=this.averageWaitingTime+servers.get(i).waitingTime;
            for(int i=0;i<servers.size();i++)
            {
                int j=i+1;
                String QueueNumber="\nQueue "+ j+": ";
                try {
                    Files.write(path, QueueNumber.getBytes(),StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String resultClients=" "+servers.get(i).getTasks()+"\n";

                if(servers.get(i).getTasks().size()>0) {
                    try {
                        Files.write(path, resultClients.getBytes(),StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }}
                else
                {
                    try {
                        Files.write(path, new String("closed\n").getBytes(),StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.print("Queue "+ j+": ");
                servers.get(i).show();
            }

            if(this.generatedTasks.size()<1 && sumServer()==0) break;

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentTime++;
        }


        this.averageWaitingTime=this.averageWaitingTime/this.numberClients;
        System.out.println("The average waiting time is "+this.averageWaitingTime+"s");
        System.out.println("Peak hour is "+this.peakTime);
        String resultWaitingTime="-------------------------------------\nThe average waiting time is "+this.averageWaitingTime+"s"+"\n";
        try {
            Files.write(path, resultWaitingTime.getBytes(),StandardOpenOption.APPEND);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String peakHour="\nPeak hour is "+this.peakTime;
        try {
            Files.write(path, peakHour.getBytes(),StandardOpenOption.APPEND);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
