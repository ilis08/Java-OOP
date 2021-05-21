import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class UsersFrame extends JFrame {
	
	
	JFrame frame = new JFrame("Users");
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel userNameL = new JLabel("User Name:");
	JLabel AgeL = new JLabel("Age:");
	JLabel CityL = new JLabel("City:");
	JLabel StreetL = new JLabel("Street");
	
	JTextField userNameTF = new JTextField();
	JTextField AgeTF = new JTextField();
	JTextField CityTF = new JTextField();
	JTextField StreetTF = new JTextField();

			
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	
		public UsersFrame() {
			frame.setSize(500, 600);
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			frame.setLayout(new GridLayout(4,1));

			//Up Panel
			upPanel.setLayout(new GridLayout(4, 2));
			upPanel.add(userNameL);
			upPanel.add(userNameTF);
			upPanel.add(AgeL);
			upPanel.add(AgeTF);
			upPanel.add(CityL);
			upPanel.add(CityTF);
			upPanel.add(StreetL);
			upPanel.add(StreetTF);
			
			frame.add(upPanel);
			
			// Mid Panel 
			midPanel.add(addBtn);
			midPanel.add(deleteBtn);
			midPanel.add(editBtn);
			
			frame.add(midPanel);
			
			frame.setVisible(true);
		}
}

