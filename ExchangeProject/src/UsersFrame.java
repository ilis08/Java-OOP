import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;




public class UsersFrame extends JFrame {
JFrame frame = new JFrame("UsersFrame");
	
	//table for products
	public JTable usersTable = new JTable();
	JScrollPane scroller = new JScrollPane(usersTable);
	
	
	//connection
	public Connection conn = null;
	public PreparedStatement state = null;
	public ResultSet result = null;
	// id for row from table
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel userNameL = new JLabel("User Name:");
	JLabel ageL = new JLabel("Age:");
	JLabel cityL = new JLabel("City:");
	JLabel streetL = new JLabel("Street:");
	
	JTextField userNameTF = new JTextField();
	JTextField ageTF = new JTextField();
	JTextField cityTF = new JTextField();
	JTextField streetTF = new JTextField();

			
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JComboBox<String> searchCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("Search");
	JButton allBtn = new JButton("All");
	
		public UsersFrame() {
			frame.setSize(500, 600);
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			frame.setLayout(new GridLayout(4,1));

			//Up Panel
			upPanel.setLayout(new GridLayout(4, 2));
			upPanel.add(userNameL);
			upPanel.add(userNameTF);
			upPanel.add(ageL);
			upPanel.add(ageTF);
			upPanel.add(cityL);
			upPanel.add(cityTF);
			upPanel.add(streetL);
			upPanel.add(streetTF);
			
			frame.add(upPanel);
			
			// Mid Panel 
			midPanel.add(addBtn);
			midPanel.add(deleteBtn);
			midPanel.add(editBtn);
			midPanel.add(searchCombo);
			midPanel.add(searchBtn);
			midPanel.add(allBtn);
			
			frame.add(midPanel);
			
			//add AddAction
			addBtn.addActionListener(new AddAction());
			//add DeleteAction
			deleteBtn.addActionListener(new DeleteAction());
			searchBtn.addActionListener(new SearchAction());
			allBtn.addActionListener(new SearchAllAction());
			editBtn.addActionListener(new EditAction());
			
			DBHelper.FillCombo(searchCombo, "NAME",  "USERS");
			
			//Down Panel
			
			downPanel.add(scroller);
			scroller.setPreferredSize(new Dimension(450, 150));
			usersTable.setModel(DBHelper.getAllData("USERS"));
			usersTable.addMouseListener(new TableListener());
			
			frame.add(downPanel);
			
			
			
			
			frame.setVisible(true);
		}
		
		// clearForm to string
		public void clearForm() {
			userNameTF.setText("");
			ageTF.setText("");
			cityTF.setText("");
			streetTF.setText("");
		}
		public class TableListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = usersTable.getSelectedRow();
				id = Integer.parseInt(usersTable.getValueAt(row, 0).toString());
			
				if(e.getClickCount()==2) {
					userNameTF.setText(usersTable.getValueAt(row, 1).toString());
					ageTF.setText(usersTable.getValueAt(row, 2).toString());
					cityTF.setText(usersTable.getValueAt(row, 3).toString());
					streetTF.setText(usersTable.getValueAt(row, 4).toString());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		public class AddAction implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				String userName = userNameTF.getText();
				int age = Integer.parseInt(ageTF.getText());
				String city = cityTF.getText();
				String town = streetTF.getText();
				
				conn = DBHelper.getConnection();
				try {
					state = conn.prepareStatement("INSERT INTO USERS VALUES(null, ?, ?, ?, ?);");
					state.setString(1, userName);
					state.setInt(2, age);
					state.setString(3, city);
					state.setString(4, town);
					
					state.execute();
					usersTable.setModel(DBHelper.getAllData("USERS"));
					DBHelper.FillCombo(searchCombo, "NAME",  "USERS");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						state.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				clearForm();
			}
		}
		
		public class EditAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
			
					conn = DBHelper.getConnection();
					String sql = "UPDATE USERS SET NAME = \'" + userNameTF.getText() + "\', AGE = \'"  + ageTF.getText() + "\' , CITY = \'" + cityTF.getText() + "\', STREET = \'" + streetTF.getText() + "\' WHERE ID=?;";
					try {
						state = conn.prepareStatement(sql);
						state.setInt(1, id);
						state.execute();
						id = -1;
						usersTable.setModel(DBHelper.getAllData("USERS"));
						DBHelper.FillCombo(searchCombo, "NAME",  "USERS");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
		
		public class DeleteAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				conn = DBHelper.getConnection();
				String sql = "DELETE FROM USERS WHERE ID=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					usersTable.setModel(DBHelper.getAllData("USERS"));
					id = -1;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		public class SearchAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String item = searchCombo.getSelectedItem().toString();
				String [] content = item.split(" ");
				int userId = Integer.parseInt(content[0]);
				
				conn = DBHelper.getConnection();
				String sql = "select * from users where id=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, userId);
					result = state.executeQuery();
					usersTable.setModel(new MyModel(result));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}

		public class SearchAllAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					conn = DBHelper.getConnection();
				
					String sql = "SELECT * FROM USERS";
					
					try {
						state = conn.prepareStatement(sql);
						state.execute();	
						usersTable.setModel(DBHelper.getAllData("USERS"));
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		}
}

