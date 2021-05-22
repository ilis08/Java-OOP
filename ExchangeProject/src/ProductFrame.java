

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
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
import javax.swing.JTable;
import javax.swing.JTextField;

public class ProductFrame extends JFrame{
	JFrame frame = new JFrame("ProductFrame");
	
	//table for products
	public JTable productTable = new JTable();
	JScrollPane scroller = new JScrollPane(productTable);
	
	
	int row;
	
	//connection
	public Connection conn = null;
	public PreparedStatement state = null;
	public ResultSet result = null;
	// id for row from table
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel prodNameL = new JLabel("Product Name:");
	JLabel beginPriceL = new JLabel("Begin Price:");
	JLabel stockPriceL = new JLabel("Stock Price:");
	JLabel quantityL = new JLabel("Quantity");
	
	JTextField prodNameTF = new JTextField();
	JTextField beginPriceTF = new JTextField();
	JTextField stockPriceTF = new JTextField();
	JTextField quantityTF = new JTextField();

			
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JComboBox<String> searchCombo = new JComboBox<String>();
	JButton searchBtn = new JButton("Search");
	JButton allBtn = new JButton("All");
	
		public ProductFrame() {
			frame.setSize(500, 600);
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			frame.setLayout(new GridLayout(4,1));

			//Up Panel
			upPanel.setLayout(new GridLayout(4, 2));
			upPanel.add(prodNameL);
			upPanel.add(prodNameTF);
			upPanel.add(beginPriceL);
			upPanel.add(beginPriceTF);
			upPanel.add(stockPriceL);
			upPanel.add(stockPriceTF);
			upPanel.add(quantityL);
			upPanel.add(quantityTF);
			
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
			
			DBHelper.FillCombo(searchCombo, "PRODUCT_NAME",  "PRODUCTS");
			
			//Down Panel
			
			downPanel.add(scroller);
			scroller.setPreferredSize(new Dimension(450, 150));
			productTable.setModel(DBHelper.getAllData("PRODUCTS"));
			productTable.addMouseListener(new TableListener());
			
			frame.add(downPanel);
			
			
			
			
			frame.setVisible(true);
		}
		
		// clearForm to string
		public void clearForm() {
			prodNameTF.setText("");
			beginPriceTF.setText("");
			stockPriceTF.setText("");
			quantityTF.setText("");
		}
		public class TableListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				row = productTable.getSelectedRow();
				id = Integer.parseInt(productTable.getValueAt(row, 0).toString());
			
				if(e.getClickCount()==2) {
					prodNameTF.setText(productTable.getValueAt(row, 1).toString());
					beginPriceTF.setText(productTable.getValueAt(row, 2).toString());
					stockPriceTF.setText(productTable.getValueAt(row, 3).toString());
					quantityTF.setText(productTable.getValueAt(row, 4).toString());
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
				String prodName = prodNameTF.getText();
				float beginPrice = Float.parseFloat(beginPriceTF.getText());
				float stockPrice = Float.parseFloat(stockPriceTF.getText());
				int quantity = Integer.parseInt(quantityTF.getText());
				float profit =  stockPrice - beginPrice ;
				
				conn = DBHelper.getConnection();
				try {
					state = conn.prepareStatement("INSERT INTO PRODUCTS VALUES(null, ?, ?, ?, ?, ?);");
					state.setString(1, prodName);
					state.setFloat(2, beginPrice);
					state.setFloat(3, stockPrice);
					state.setInt(4, quantity);
					state.setFloat(5, profit);
					
					
					state.execute();
					productTable.setModel(DBHelper.getAllData("PRODUCTS"));
					DBHelper.FillCombo(searchCombo, "PRODUCT_NAME",  "PRODUCTS");
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
		
		public class DeleteAction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				conn = DBHelper.getConnection();
				String sql = "DELETE FROM PRODUCTS WHERE ID=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					productTable.setModel(DBHelper.getAllData("PRODUCTS"));
					id = -1;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		
		public class EditAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
			
					conn = DBHelper.getConnection();
					String sql = "UPDATE PRODUCTS SET PRODUCT_NAME = \'" + prodNameTF.getText() + "\', BEGIN_PRICE = \'"  + beginPriceTF.getText() + "\' , STOCK_PRICE = \'" + stockPriceTF.getText() + "\', QUANTITY = \'" + quantityTF.getText() + "\' WHERE ID=?;";
					try {
						state = conn.prepareStatement(sql);
						state.setInt(1, id);
						state.execute();
						id = -1;
						productTable.setModel(DBHelper.getAllData("PRODUCTS"));
						DBHelper.FillCombo(searchCombo, "PRODUCT_NAME",  "PRODUCTS");
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
				int personId = Integer.parseInt(content[0]);
				
				conn = DBHelper.getConnection();
				String sql = "select * from products where id=?";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, personId);
					result = state.executeQuery();
					productTable.setModel(new MyModel(result));
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
				
					String sql = "SELECT * FROM PRODUCTS";
					
					try {
						state = conn.prepareStatement(sql);
						state.execute();	
						productTable.setModel(DBHelper.getAllData("PRODUCTS"));
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		
		
}
