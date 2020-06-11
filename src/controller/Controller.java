package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import model.Model;
import model.MyDate;
import view.View;

public class Controller extends Thread implements ActionListener {
    View gui;
    Model model;
	boolean runFlag;
	private String filter = "none";

    public Controller(String name) {
    	super(name);
        gui = new View();
        gui.getStartButton().addActionListener(this::actionPerformed);
        gui.getStopButton().addActionListener(this::actionPerformed);
        gui.getFilter().addActionListener(this::actionPerformed);
        model = new Model();
    }
    
    @Override
	public void run() {
		runFlag = true;

		while(runFlag) {

		    if(gui.getSearchBar().getText().equals("")){
		        refreshThreads(model.filterThreads(model.getThreadsInfo(model.getAllThreads()),filter));
            }else{
                refreshThreads(model.filterThreads(model.getThreadsInfoByName(model.getAllThreads(), gui.getSearchBar().getText()),filter));
            }
        }
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.getStartButton()){
            new Thread(new MyDate("MyDate")).start();
        }

        if (e.getSource() == gui.getStopButton()){
            model.getThreadById(gui.getIntIdField()).interrupt();
        }

        if(e.getSource() == gui.getFilter()){
            filter = gui.getFilter().getSelectedItem().toString();
        }

    }

    public void refreshThreads(String[][] data){
        gui.refreshThreadsData(data);
        gui.updateFilter(model.getAllThreads());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            runFlag = false;
            gui.getFrame().dispatchEvent(new WindowEvent(gui.getFrame(), WindowEvent.WINDOW_CLOSING));
            System.out.println("Thread interrupted.");
        }
    }
}
