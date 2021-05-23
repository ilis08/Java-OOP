import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SearchFrame {
	JFrame searchFrame = new JFrame("SearchFrame");
	
	//table for products
	public JTable searchTable = new JTable();
	JScrollPane scroller = new JScrollPane(searchTable);
	
	//connection
		public Connection conn = null;
		public PreparedStatement state = null;
		public ResultSet result = null;
		// id for row from table
		int id = -1;
		
		JPanel upPanel = new JPanel();
		JPanel downPanel = new JPanel();
		
		JComboBox<String> searchCombo = new JComboBox<String>();
		JComboBox<String> searchCombo1 = new JComboBox<String>();
		JButton searchBtn = new JButton("Search");
		JButton allBtn = new JButton("All");
		
		SearchFrame() {
			searchFrame.setSize(500, 600);
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			searchFrame.setLayout(new GridLayout(2,1));
			searchFrame.add(upPanel);
			searchFrame.add(downPanel);
			
			upPanel.add(searchCombo);
			upPanel.add(searchCombo1);
			upPanel.add(searchBtn);
			upPanel.add(allBtn);
			
			downPanel.add(scroller);
			
			searchBtn.addActionListener(new SearchAction());
			allBtn.addActionListener(new SearchAllAction());
			
			DBHelper.FillCombo(searchCombo, "PRODUCT_NAME",  "PRODUCTS");
			DBHelper.FillCombo(searchCombo1, "NAME",  "USERS");
			
			scroller.setPreferredSize(new Dimension(450, 150));
			searchTable.setModel(DBHelper.getAllDataTable());
			
			searchFrame.setVisible(true);
		}
		
		public class SearchAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String productItem = searchCombo.getSelectedItem().toString();
				String [] productContent = productItem.split(" ");
				int productId = Integer.parseInt(productContent[0]);
				
				String userItem = searchCombo1.getSelectedItem().toString();
				String [] userContent = userItem.split(" ");
				int userId = Integer.parseInt(userContent[0]);
				
				conn = DBHelper.getConnection();
				
				String sql = "SELECT\r\n"
			    		+ "    O.ID, O.QUANTITY_SOLD AS PRODUCT_QUANTITY,\r\n"
			    		+ "P.PROFIT, P.PRODUCT_NAME AS PRODUCT,\r\n"
			    		+ "    U1.NAME AS BUYER,\r\n"
			    		+ "    U2.NAME AS SELLER\r\n"
			    		+ "FROM\r\n"
			    		+ "    ORDERS O,\r\n"
			    		+ "    USERS U1,\r\n"
			    		+ "USERS U2,\r\n"
			    		+ "PRODUCTS P\r\n"
			    		+ "WHERE\r\n"
			    		+ "        U1.ID= O.SELLER_ID\r\n"
			    		+ "    AND\r\n"
			    		+ "        U2.ID= O.BUYER_ID\r\n"
			    		+ "AND \r\n"
			    		+ "P.ID = O.PRODUCT_ID \r\n"
			    		+ "AND O.PRODUCT_ID=? \r\n"
			    		+ "AND O.SELLER_ID=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, productId);
					state.setInt(2, userId);
					result = state.executeQuery();
					searchTable.setModel(new MyModel(result));
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
				
					String sql = "SELECT\r\n"
				    		+ "    O.ID, O.QUANTITY_SOLD AS PRODUCT_QUANTITY,\r\n"
				    		+ "P.PROFIT, P.PRODUCT_NAME AS PRODUCT,\r\n"
				    		+ "    U1.NAME as BUYER,\r\n"
				    		+ "    U2.NAME as SELLER\r\n"
				    		+ "FROM\r\n"
				    		+ "    ORDERS O,\r\n"
				    		+ "    USERS U1,\r\n"
				    		+ "USERS U2,\r\n"
				    		+ "PRODUCTS P\r\n"
				    		+ "WHERE\r\n"
				    		+ "        U1.ID= O.BUYER_ID\r\n"
				    		+ "    AND\r\n"
				    		+ "        U2.ID= O.SELLER_ID\r\n"
				    		+ "AND \r\n"
				    		+ "P.ID = O.PRODUCT_ID";
					
					try {
						state = conn.prepareStatement(sql);
						state.execute();	
						searchTable.setModel(DBHelper.getAllDataTable());
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
}
