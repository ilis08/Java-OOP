

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
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
	JTable productTable = new JTable();
	JScrollPane scroller = new JScrollPane(productTable);
	
	
	//connection
	private Connection conn = null;
	private PreparedStatement state = null;
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
	
		public ProductFrame() {
			frame.setSize(400, 600);
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
			
			frame.add(midPanel);
			
			//Down Panel
			
			downPanel.add(scroller);
			scroller.setPreferredSize(new Dimension(450, 160));
			productTable.setModel(DBHelper.getAllData("PRODUCTS"));
			productTable.addMouseListener(new TableListener());
			
			frame.add(downPanel);
			
			//add AddAction
			addBtn.addActionListener(new AddAction());
			//add DeleteAction
			deleteBtn.addActionListener(new DeleteAction());
			
			frame.setVisible(true);
		}
		
		// clearForm to string
		public void clearForm() {
			prodNameTF.setText("");
			beginPriceTF.setText("");
			stockPriceTF.setText("");
			quantityTF.setText("");
		}
		class TableListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = productTable.getSelectedRow();
				id = Integer.parseInt(productTable.getValueAt(row, 0).toString());
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
				float beginPrice = Float.parseFloat(beginPriceTF.getText());
				float stockPrice = Float.parseFloat(stockPriceTF.getText());
				int quantity = Integer.parseInt(quantityTF.getText());
				
				conn = DBHelper.getConnection();
				try {
					state = conn.prepareStatement("INSERT INTO PRODUCTS VALUES(null, ?, ?, ?, ?);");
					state.setString(1, prodName);
					state.setFloat(2, beginPrice);
					state.setFloat(3, stockPrice);
					state.setInt(4, quantity);
					
					state.execute();
					productTable.setModel(DBHelper.getAllData("PRODUCTS"));
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
}
 