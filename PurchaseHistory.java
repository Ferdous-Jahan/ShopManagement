import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class PurchaseHistory extends JFrame implements ActionListener
{
	JTable myTable;
	JScrollPane tableScrollPane;
	JButton backBtn,logoutBtn;
	JPanel panel;
	String userId;
	
	public PurchaseHistory(String userId)
	{
		super("Purchase History");
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		myTable = new JTable();
		String []col = {"quantity","amount","purchaseId","productId"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(col);
		
		String query = "SELECT `quantity`,`amount`,`purchaseId`,`productId` FROM purchaseinfo WHERE `userId`='"+userId+"';";
		Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next())
			{
				int col1 = rs.getInt("quantity");
				double col2 = rs.getDouble("amount");
				String col3 = rs.getString("purchaseId");
				String col4 = rs.getString("productId");
				model.addRow(new Object[]{col1,col2,col3,col4});
			}
			myTable.setModel(model);
		}
		catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex)
			{
				
				System.out.println("Exception : " +ex.getMessage());
			}
        }
		
		tableScrollPane = new JScrollPane(myTable);
		tableScrollPane.setBounds(50,100,500,200);
		panel.add(tableScrollPane);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(650, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(250, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		
		else{}
	}
}