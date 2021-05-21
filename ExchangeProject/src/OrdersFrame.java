import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class OrdersFrame {
JFrame frame = new JFrame("ProductFrame");
	
	//table for products
	JTable ordersTable = new JTable();
	JScrollPane scroller = new JScrollPane(ordersTable);
	
	
	//connection
	private Connection conn = null;
	private PreparedStatement state = null;
	private ResultSet result = null;
	// id for row from table
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel productNameL = new JLabel("Product");
	JLabel productQuantityL = new JLabel("Product quantity");
	JLabel sellerIdL = new JLabel("Seller");
	JLabel buyerIdL = new JLabel("Buyer");
	
	JComboBox<String> prodNameTF = new JComboBox<String>();
	JTextField productQuantityTF = new JTextField();
	JComboBox<String> sellerIdTF = new JComboBox<String>();
	JComboBox<String> buyerIdTF = new JComboBox<String>();

			
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JComboBox<String> searchCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("Search");
	JButton allBtn = new JButton("All");
	
		public OrdersFrame() {
			frame.setSize(600, 800);
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			frame.setLayout(new GridLayout(4,1));

			//Up Panel
			upPanel.setLayout(new GridLayout(4, 2));
			upPanel.add(productNameL);
			upPanel.add(prodNameTF);
			upPanel.add(productQuantityL);
			upPanel.add(productQuantityTF);
			upPanel.add(sellerIdL);
			upPanel.add(sellerIdTF);
			upPanel.add(buyerIdL);
			upPanel.add(buyerIdTF);
			upPanel.add(allBtn);
			
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
			
			DBHelper.FillCombo(searchCombo, "PRODUCT_ID",  "ORDERS");
			
			//Down Panel
			
			downPanel.add(scroller);
			scroller.setPreferredSize(new Dimension(450, 150));
			ordersTable.setModel(DBHelper.getAllData("ORDERS"));
			ordersTable.addMouseListener(new TableListener());
			
			frame.add(downPanel);
			
			
			
			
			frame.setVisible(true);
		}
		
		// clearForm to string
		public void clearForm() {
			prodNameTF.setText("");
			productQuantityTF.setText("");
			sellerIdTF.setText("");
			buyerIdTF.setText("");
		}
		class TableListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = ordersTable.getSelectedRow();
				id = Integer.parseInt(ordersTable.getValueAt(row, 0).toString());
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
		
		class AddAction implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				String prodName = prodNameTF.getText();
				float productQuantity = Float.parseFloat(productQuantityTF.getText());
				float sellerId = Float.parseFloat(sellerIdTF.getText());
				int buyerId = Integer.parseInt(buyerIdTF.getText());
				
				conn = DBHelper.getConnection();
				try {
					state = conn.prepareStatement("INSERT INTO ORDERS VALUES(null, ?, ?, ?, ?);");
					state.setString(1, prodName);
					state.setFloat(2, productQuantity);
					state.setFloat(3, sellerId);
					state.setInt(4, buyerId);
					
					state.execute();
					ordersTable.setModel(DBHelper.getAllData("ORDERS"));
					DBHelper.FillCombo(searchCombo, "ID",  "ORDERS");
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
		
		class DeleteAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				conn = DBHelper.getConnection();
				String sql = "DELETE FROM ORDERS WHERE ID=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					ordersTable.setModel(DBHelper.getAllData("ORDERS"));
					id = -1;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		class SearchAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String item = searchCombo.getSelectedItem().toString();
				String [] content = item.split(" ");
				int orderId = Integer.parseInt(content[0]);
				
				conn = DBHelper.getConnection();
				String sql = "select * from products where id=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, orderId);
					result = state.executeQuery();
					ordersTable.setModel(new MyModel(result));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}

		class SearchAllAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					conn = DBHelper.getConnection();
				
					String sql = "SELECT * FROM ORDERS";
					
					try {
						state = conn.prepareStatement(sql);
						state.execute();	
						ordersTable.setModel(DBHelper.getAllData("ORDERS"));
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		}
}
