import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton updateAccount, logoutBtn, searchProduct, historyBtn;
	JPanel panel;
	String userId;
	
	public CustomerHome(String userId)
	{
		super("Customer Home");
		
		this.userId = userId;
		this.setSize(1000,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("WELCOME");             //welcomes customer
		welcomeLabel.setBounds(100,100,300,50);
		panel.add(welcomeLabel);
		
		updateAccount = new JButton("Update info");       //change info button
		updateAccount.setBounds(100,200,200,50);
		updateAccount.addActionListener(this);
		panel.add(updateAccount);
		
		historyBtn = new JButton("Purchase History");
		historyBtn.setBounds(500,400,200,50);
		historyBtn.addActionListener(this);
		panel.add(historyBtn);
		
		searchProduct = new JButton("Search Product");     //product searching button
		searchProduct.setBounds(500,200,300,50);
		searchProduct.addActionListener(this);
		panel.add(searchProduct);
		
		logoutBtn = new JButton("Logout");               //logout button
		logoutBtn.setBounds(100,400,100,50); 
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)           //action events for clicking a button
	{
		String text = ae.getActionCommand();
		
		if(text.equals(updateAccount.getText()))             //go to info update page
		{
			CustomerUpdate cu = new CustomerUpdate(userId);
			cu.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(searchProduct.getText()))            //go to product search page
		{
			Product pr = new Product(userId);
			pr.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(logoutBtn.getText()))             //go to login page
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(historyBtn.getText()))             //go to login page
		{
			PurchaseHistory ph = new PurchaseHistory(userId);
			ph.setVisible(true);
			this.setVisible(false);
		}
		
		else{}
	}
}