import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LaunchFrame extends JFrame implements ActionListener {
	JFrame frame = new JFrame("LaunchFrame");

	LaunchFrame(){
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 600);
		frame.setLayout(new GridLayout(4,1));
		//Add Product btn
		JButton productBtn = new JButton("Add new product");
		
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
		frame.add(productBtn);

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
		frame.add(usersBtn);
		
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
		frame.add(ordersBtn);
		
		frame.setVisible(true);
	}

	


	


	
}
