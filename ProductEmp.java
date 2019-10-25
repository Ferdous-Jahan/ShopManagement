import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class ProductEmp extends JFrame implements ActionListener
{
	JLabel NameLabel,PriceLabel,QuantityLabel,productIdlabel;
	JTextField nameTF,priceTF,quantityTF,productIdTF;
	JButton refreshBtn,loadBtn,backBtn,logoutBtn,updateBtn,delBtn;
	JPanel panel;
	String userId;
	
	public ProductEmp(String userId)
	{
		super("Search Product");
		
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");                       //button and textfirelds and labels
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(250, 100, 275, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		NameLabel = new JLabel("Product Name : ");
		NameLabel.setBounds(250, 150, 120, 30);
		panel.add(NameLabel);
		
		nameTF = new JTextField();
		nameTF.setBounds(400, 150, 120, 30);
		panel.add(nameTF);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 150, 150, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		PriceLabel = new JLabel("Price: ");
		PriceLabel.setBounds(250, 200, 120, 30);
		panel.add(PriceLabel);
		
		priceTF = new JTextField();
		priceTF.setBounds(400, 200, 120, 30);
		panel.add(priceTF);
		
		QuantityLabel = new JLabel("Quantity: ");
		QuantityLabel.setBounds(250, 300, 120, 30);
		panel.add(QuantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(400, 300, 120, 30);
		panel.add(quantityTF);
		
		productIdlabel = new JLabel("product id: ");
		productIdlabel.setBounds(250,250, 120, 30);
		panel.add(productIdlabel);
		
		productIdTF = new JTextField();
		productIdTF.setBounds(400, 250, 120,30);
		panel.add(productIdTF);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(250, 400, 150, 30);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(410, 400, 100, 30);
		delBtn.setEnabled(false);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		this.add(panel);
	}


public void actionPerformed(ActionEvent ae)              //action command when button clicked
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		if(text.equals(refreshBtn.getText()))
		{
			productIdTF.setEnabled(true);
			delBtn.setEnabled(false);
			nameTF.setText("");
			priceTF.setText("");
			quantityTF.setText("");
			productIdTF.setText("");
		}
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();			
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadName = nameTF.getText();
		String query = "SELECT `productName`, `price`, `quantity`,`productId` FROM `product` WHERE `productName`='"+loadName+"';";     
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
			
			boolean flag = false;
			String productName = null;
			String productId = null;
			double price;
			int quantity;			
			while(rs.next())
			{
                productName = rs.getString("productName");            //fetching data from database
				price = rs.getDouble("price");
				quantity = rs.getInt("quantity");
				productId = rs.getString("productId");
				flag=true;
				
				nameTF.setText(productName);                   //inserting fetched data into GUI
				priceTF.setText(""+price);
				quantityTF.setText(""+quantity);
				productIdTF.setText(productId);
				productIdTF.setEnabled(false);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
			}
			if(!flag)
			{
				nameTF.setText("");
				priceTF.setText("");
				quantityTF.setText("");
				JOptionPane.showMessageDialog(this,"Product not available"); 
			}
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
            catch(Exception ex){}
        }
	}
	public void updateInDB()
	{
		String Loadid = productIdTF.getText();
		String name = nameTF.getText();
		int quantity = 0;
		double price = 0.0;
        
		try
		{
			price = Double.parseDouble(priceTF.getText());
			quantity = Integer.parseInt(quantityTF.getText());
		}
		catch(Exception e){}
		String query = "UPDATE product SET productName = '"+name+"', price = "+price+", quantity = "+quantity+" WHERE productId='"+Loadid+"'";
        Connection con=null;//for connection
        Statement st = null;//for query execution
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			productIdTF.setEnabled(true);
			nameTF.setText("");
			quantityTF.setText("");
			priceTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
		public void deleteFromDB()
	{
		String Loadid = productIdTF.getText();
		String query1 = "DELETE from product WHERE productId='"+Loadid+"';";
		//String query2 = "DELETE from login WHERE userId='"+newId+"';";
		System.out.println(query1);
		//System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			//stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			productIdTF.setEnabled(true);
			nameTF.setText("");
			quantityTF.setText("");
			priceTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}