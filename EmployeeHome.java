import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton viewDetailsBtn, logoutBtn;
	JButton productBtn, customerBtn, addproductBtn;
	JPanel panel;
	String userId;
	
	public EmployeeHome(String userId)
	{
		super("Employee Home");
		
		this.userId = userId;
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(350, 50, 100, 30);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(400, 120, 150, 30);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		productBtn = new JButton("Products");
		productBtn.setBounds(50, 200, 150,30);
		productBtn.addActionListener(this);
		panel.add(productBtn);
		
		customerBtn = new JButton("Customers");
		customerBtn.setBounds(220,200,150,30);
		customerBtn.addActionListener(this);
		panel.add(customerBtn);
		
		addproductBtn = new JButton("Add product");
		addproductBtn.setBounds(400,200,150,30);
		addproductBtn.addActionListener(this);
		panel.add(addproductBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(productBtn.getText()))
		{
			ProductEmp pre = new ProductEmp(userId);
			pre.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(customerBtn.getText()))
		{
			CustomerEmp ce = new CustomerEmp(userId);
			ce.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(viewDetailsBtn.getText()))
		{
			EmployeeUpdate eu = new EmployeeUpdate(userId);
			eu.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(addproductBtn.getText()))
		{
			AddProduct ap = new AddProduct(userId);
			ap.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
	
}