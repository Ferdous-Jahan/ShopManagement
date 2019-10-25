import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class Product extends JFrame implements ActionListener
{
	JLabel NameLabel,PriceLabel,QuantityLabel,idLabel;
	JTextField nameTF,priceTF,quantityTF,idTF;
	JButton refreshBtn,loadBtn,backBtn,logoutBtn,buyBtn;
	JPanel panel;
	String userId;
	
	public Product(String userId)
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
		
		idLabel = new JLabel("Product id : ");
		idLabel.setBounds(250, 250, 120, 30);
		panel.add(idLabel);
		
		idTF = new JTextField();
		idTF.setBounds(400, 250, 115, 30);
		panel.add(idTF);
		
		QuantityLabel = new JLabel("Quantity: ");
		QuantityLabel.setBounds(250, 300, 120, 30);
		panel.add(QuantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(400, 300, 120, 30);
		panel.add(quantityTF);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		buyBtn = new JButton("Buy");
		buyBtn.setBounds(250, 400, 120, 30);
		buyBtn.setEnabled(false);
		buyBtn.addActionListener(this);
		panel.add(buyBtn);
		
		this.add(panel);
	}


public void actionPerformed(ActionEvent ae)              //action command when button clicked
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			CustomerHome ch = new CustomerHome(userId);
			ch.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(refreshBtn.getText()))
		{
			nameTF.setEnabled(true);
			nameTF.setText("");
			priceTF.setText("");
			quantityTF.setText("");
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
		else if(text.equals(buyBtn.getText()))
		{
			insertIntoDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String loadName = nameTF.getText();
		String query = "SELECT `productId`,`productName`, `price`, `quantity` FROM `product` WHERE `productName`='"+loadName+"';";     
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
			String id = null;
			double price;
			int quantity;			
			while(rs.next())
			{
                productName = rs.getString("productName");            //fetching data from database
				price = rs.getDouble("price");
				id = rs.getString("productId");
				quantity = rs.getInt("quantity");
				flag=true;
				
				nameTF.setText(productName);                   //inserting fetched data into GUI
				priceTF.setText(""+price);
				idTF.setText(id);
				quantityTF.setText(""+quantity);
				nameTF.setEnabled(false);
				buyBtn.setEnabled(true);
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
	
	public void insertIntoDB()
	{
		String newId = idTF.getText();
		//String name = nameTF.getText();
		double price = Double.parseDouble(priceTF.getText());
		int quantity = 1;
		
		
		String query = "INSERT INTO purchaseinfo VALUES ("+quantity+","+price+",NULL,'"+newId+"','"+userId+"',NOW());";
		String query1 = "UPDATE product SET quantity = quantity-1 where productId='"+newId+"'";
		System.out.println(query);
		System.out.println(query1);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
			stm.execute(query1);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
    }
}