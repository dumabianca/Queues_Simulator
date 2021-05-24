package controller;

import model.SimulationManager;
import view.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private UI view;
    public Controller(UI view)
    {
        this.view=view;
        this.view.addStartListener(new StartListener());
    }
    class StartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int simulationTime=Integer.parseInt(view.getSimulationTime());
            int queues=Integer.parseInt(view.getNoQueues());
            int clients=Integer.parseInt(view.getNoClients());
            int minArrival=Integer.parseInt(view.getMinArrival());
            int maxArrival=Integer.parseInt(view.getMaxArrival());
            int minService=Integer.parseInt( view.getMinService());
            int maxService=Integer.parseInt(view.getMaxService());
            SimulationManager s=new SimulationManager(simulationTime,clients,queues,minService,maxService,maxArrival,minArrival,view);
            s.start();
        }

    }
}
