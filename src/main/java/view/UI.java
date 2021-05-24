package view;
import controller.Controller;
import model.Server;
import model.Task;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UI extends JFrame {
    private JLabel labelNoClients=new JLabel("Number of Clients: ");
    private JLabel labelNoQueues =new JLabel ("Number of Queues: ");
    private JLabel labelSimulationTime=new JLabel("Simulation Time(max): ");
    private JLabel labelMinMaxServiceTime=new JLabel("Minimum and Maximum service time: ");
    private JLabel labelMinMaxArrivalTime=new JLabel("Minimum and Maximum arrival time: ");
    private JTextField fieldNoClients= new JTextField(4);
    private JTextField fieldNoQueues= new JTextField(4);
    private JTextField fieldSimulationTime= new JTextField(4);
    private JTextField fieldMinArrivalTime= new JTextField(4);
    private JTextField fieldMaxArrivalTime= new JTextField(4);
    private JTextField fieldMinServiceTime= new JTextField(4);
    private JTextField fieldMaxServiceTime= new JTextField(4);
    private JButton startButton=new JButton("START");
    public JTextArea statusArea=new JTextArea("");
    public JLabel currentTimeLabel=new JLabel("Current time: ");
    private  ScrollPane pMain2;
    public UI(){
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pSimulationTime=new JPanel();
        pSimulationTime.add(labelSimulationTime);
        pSimulationTime.add(fieldSimulationTime);
        JPanel pClients=new JPanel();
        pClients.add(labelNoClients);
        pClients.add(fieldNoClients);
        JPanel pQueues=new JPanel();
        pQueues.add(labelNoQueues);
        pQueues.add(fieldNoQueues);
        JPanel pServiceTime=new JPanel();
        pServiceTime.add(labelMinMaxServiceTime);
        pServiceTime.add(fieldMinServiceTime);
        pServiceTime.add(fieldMaxServiceTime);
        JPanel pArrivalTime=new JPanel();
        pArrivalTime.add(labelMinMaxArrivalTime);
        pArrivalTime.add(fieldMinArrivalTime);
        pArrivalTime.add(fieldMaxArrivalTime);
        JPanel pButton=new JPanel();
        pButton.add(startButton);
        pButton.add(currentTimeLabel);
        JPanel pMain=new JPanel();
        pMain.setLayout(new BoxLayout(pMain,BoxLayout.Y_AXIS));
        pMain.add(pSimulationTime);
        pMain.add(pClients);
        pMain.add(pQueues);
        pMain.add(pServiceTime);
        pMain.add(pArrivalTime);
        pMain.add(pButton);

        JPanel pMain1=new JPanel();
        pMain1.add(pMain,BorderLayout.PAGE_START);
        pMain2=new ScrollPane();
        pMain2.add(statusArea);
        JPanel pM=new JPanel();
        pM.setLayout(new BoxLayout(pM,BoxLayout.Y_AXIS));
        pM.add(pMain1);
        pM.add(pMain2);
        startButton.setBackground(new Color(82,183,136));
        fieldMaxArrivalTime.setBackground(new Color(149,213,178));
        fieldMinArrivalTime.setBackground(new Color(149,213,178));
        fieldNoClients.setBackground(new Color(149,213,178));
        fieldSimulationTime.setBackground(new Color(149,213,178));
        fieldNoQueues.setBackground(new Color(149,213,178));
        fieldMaxServiceTime.setBackground(new Color(149,213,178));
        fieldMinServiceTime.setBackground(new Color(149,213,178));
        pMain1.setBackground(new Color(116,198,157));
        pMain2.setBackground(new Color(116,198,157));
        pMain.setBackground(new Color(116,198,157));
        pM.setBackground(new Color(116,198,157));
        statusArea.setBackground(new Color(82,183,136));
        pArrivalTime.setBackground(new Color(45,106,79));
        pServiceTime.setBackground(new Color(45,106,79));
        pSimulationTime.setBackground(new Color(45,106,79));
        pQueues.setBackground(new Color(45,106,79));
        pClients.setBackground(new Color(45,106,79));
        pButton.setBackground(new Color(45,106,79));
        this.setContentPane(pM);
        this.setTitle("Queues Simulator");
        labelMinMaxServiceTime.setForeground(Color.white);
        labelMinMaxArrivalTime.setForeground(Color.white);
        labelSimulationTime.setForeground(Color.white);
        labelNoClients.setForeground(Color.white);
        labelNoQueues.setForeground(Color.white);
        startButton.setForeground(Color.white);
        currentTimeLabel.setForeground(Color.white);
    }
    public static void main(String[] args)
    {
        UI view=new UI();
        Controller c=new Controller(view);
        view.setVisible(true);
    }
    public void viewStatus(List<Server> q, List<Task> c)
    {
        String result=new String();

        for(int i=0;i<q.size();i++)
        {
            int j=i+1;
            result=result+"Queue " + j+": ";
            String queueContent=new String();
            for(int k=0;k<q.get(i).getTasks().size();k++)
                queueContent=queueContent+("\uD83D\uDE4B").toLowerCase();
            result=result+queueContent+"\n";

        }
        this.statusArea.setText(result);
        this.statusArea.setForeground(Color.white);
    }
    public String getSimulationTime()
    {
        return fieldSimulationTime.getText();
    }
    public String getNoClients()
    {
        return fieldNoClients.getText();
    }
    public String getNoQueues()
    {
        return fieldNoQueues.getText();
    }
    public String getMaxArrival()
    {
        return fieldMaxArrivalTime.getText();
    }
    public String getMinArrival()
    {
        return fieldMinArrivalTime.getText();
    }
    public String getMaxService()
    {
        return fieldMaxServiceTime.getText();
    }
    public String getMinService()
    {
        return fieldMinServiceTime.getText();
    }
    public void setCurrentTime(String msg)
    {
        this.currentTimeLabel.setText(msg);
        this. currentTimeLabel.setForeground(Color.white);
    }
    public void addStartListener(ActionListener listenerForStart)
    {
        startButton.addActionListener(listenerForStart);
    }
    public String getCurrentTimeLabel()
    {
        return currentTimeLabel.getText();
    }
}
