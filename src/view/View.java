package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static java.awt.Component.*;

/**
 * @author Kostadin Georgiev
 *
 */
public class View {

	// GUI components
	// Main window frame.
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private JTextField search;
	private JTextField idField;
	private JButton startButton = new JButton("Start Thread");
	private JButton stopButton = new JButton("Stop Thread");
	private JComboBox filter;
	private DefaultComboBoxModel filterModel;

	// We are using Border Layout ("HOW TO USE BORDER LAYOUT DOCUMENTATION:
	// "https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html").
	private JToolBar toolBarVert = new JToolBar("topbar", SwingConstants.VERTICAL);
	private JToolBar toolBarHor = new JToolBar("rightbar", SwingConstants.HORIZONTAL);

	// JPanel for layout storage
	private JPanel layoutPanel = new JPanel(new BorderLayout());

	// Start Thread pop-up

	private JPanel startPopUp = new JPanel();

	public View() {
    	// Initialise GUI components.
    	frame = new JFrame("Thread Monitor");
    	model = new DefaultTableModel();
    	model.setDataVector(new Object[][] {},
    						new Object[]{"Thread Name", "Identifier", "State", "Priority", "isDaemon"});
    	filterModel = new DefaultComboBoxModel(new String[] {});
    	table = new JTable(model);
    	scroll = new JScrollPane(table);
		search = new JTextField();
		search.setMaximumSize(new Dimension(10000,30));
		idField = new JTextField();
		idField.setColumns(3);
		idField.setMaximumSize(new Dimension(500,30));
		filter = new JComboBox(filterModel);
		filter.setMaximumSize(new Dimension(7000, 30));
		filter.addItem("None");

		// Vertical toolbar
		toolBarVert.setFloatable(false);
		toolBarVert.add(new JLabel("ID"),CENTER_ALIGNMENT);
		toolBarVert.add(idField, RIGHT_ALIGNMENT);
		toolBarVert.addSeparator();
		toolBarVert.add(startButton, LEFT_ALIGNMENT);
		toolBarVert.add(stopButton, LEFT_ALIGNMENT);

		// Horizontal toolbar
		toolBarHor.setFloatable(false);
		toolBarHor.add(new JLabel("Search: "));
		toolBarHor.add(search, LEFT_ALIGNMENT);
		toolBarHor.addSeparator();
		toolBarHor.add(new JLabel("Filter: "));
		toolBarHor.add(filter, LEFT_ALIGNMENT);

		// Main layout JPanel
		layoutPanel.add(scroll, BorderLayout.CENTER);
		layoutPanel.add(toolBarHor, BorderLayout.PAGE_START);
		layoutPanel.add(toolBarVert, BorderLayout.LINE_END);

		// Window frame
		frame.add(layoutPanel);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void refreshThreadsData(String[][] threadsData) {
    	int selected = table.getSelectedRow();
    	int selectedId = -1;
    	if (selected != -1)
    		selectedId = Integer.valueOf(model.getValueAt(selected, 1).toString());
    	model.setRowCount(0);
    	Object[] row;
    	for(int i = 0; i < threadsData.length;i++) {
    		row = new Object[threadsData[i].length];
    		for(int j = 0; j < threadsData[i].length; j++) {
    			row[j] = threadsData[i][j];
    			if (j == 1) {
    				if (threadsData[i][j] != null) {
    					int threadId = Integer.parseInt(threadsData[i][j]);
        				if (threadId != -1 && selectedId == threadId) {
        					selected = i;
        				}
    				}
    			}
    		}
			if(row[0]!=null){
				model.addRow(row);
			}
    	}
    	System.out.println(selected);
    	int rowCount = table.getRowCount();
    	if (selected != -1 && selected < rowCount) 
			table.setRowSelectionInterval(selected, selected);
    }

	public void updateFilter(Thread[] threads) {
		for (int i = 0; i < threads.length-1;i++) {
			if(filterModel.getIndexOf(threads[i].getThreadGroup().getName()) == -1) {
				filter.addItem(threads[i].getThreadGroup().getName());
			}
		}
	}


	public JComboBox getFilter(){
		return filter;
	}

	public int getIntIdField() {
		return Integer.parseInt(idField.getText());
	}

	public JButton getStartButton() {
		return startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public JTextField getSearchBar() { return search; }
	
	public JFrame getFrame() {
		return frame;
	}

}

