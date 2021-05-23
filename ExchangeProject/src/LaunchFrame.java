import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LaunchFrame extends JFrame implements ActionListener {
	JFrame frame = new JFrame("LaunchFrame");

	JPanel panel = new JPanel();
	
	LaunchFrame(){
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 600);
		
		
		//Add Product btn
		JButton productBtn = new JButton("Add new Product");
		
		productBtn.setFocusable(false);
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==productBtn) {
					ProductFrame productFrame = new ProductFrame();
				}
			}
		});
		productBtn.setBounds(100,160,200,40);
	

		///
		//Add Users Btn
		
		JButton usersBtn = new JButton("Add new User");
		
		
		usersBtn.setFocusable(false);
		usersBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==usersBtn) {
					UsersFrame usersFrame = new UsersFrame();
				}
			}
			
		});
		usersBtn.setBounds(100,160,200,40);
		
		
		JButton ordersBtn = new JButton("Add new Order");
		
		ordersBtn.setFocusable(false);
		ordersBtn.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==ordersBtn) {
					OrdersFrame orderFrame = new OrdersFrame();
				}
			}
		});
		ordersBtn.setBounds(100,160,200,40);
	
		
		JButton searchButton = new JButton("Search by two criteria");
		
		searchButton.setFocusable(false);
		searchButton.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==searchButton) {
					SearchFrame searchFrame = new SearchFrame();
				}
			}
		});
		searchButton.setBounds(100,160,200,40);
		
		
		panel.add(productBtn);
		panel.add(usersBtn);
		panel.add(ordersBtn);
		panel.add(searchButton);
		
		frame.add(panel);
		frame.setVisible(true);
	}

	


	


	
}
