import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddProduct extends JFrame implements ActionListener
{
	JLabel productIdLabel, productNameLabel, priceLabel, quantityLabel;
	JTextField productIdTF, productNameTF, priceTF, quantityTF;
	JButton addBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	public AddProduct(String userId)
	{
		super("Add product");
		
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		productIdLabel = new JLabel("product ID : ");
		productIdLabel.setBounds(250, 100, 120, 30);
		panel.add(productIdLabel);
		
		productIdTF = new JTextField();
		productIdTF.setBounds(400, 100, 120, 30);
		panel.add(productIdTF);
		
		productNameLabel = new JLabel("Product name: ");
		productNameLabel.setBounds(250, 150, 120, 30);
		panel.add(productNameLabel);
		
		productNameTF = new JTextField();
		productNameTF.setBounds(400, 150, 120, 30);
		panel.add(productNameTF);
		
		priceLabel = new JLabel("price: ");
		priceLabel.setBounds(250, 200, 120, 30);
		panel.add(priceLabel);
		
		priceTF = new JTextField();
		priceTF.setBounds(400, 200, 120, 30);
		panel.add(priceTF);
		
		quantityLabel = new JLabel("quantity: ");
		quantityLabel.setBounds(250, 250, 120, 30);
		panel.add(quantityLabel);
		
		quantityTF = new JTextField();
		quantityTF.setBounds(400, 250, 120, 30);
		panel.add(quantityTF);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(250, 400, 120, 30);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(400, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome(userId);
			eh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(logoutBtn.getText()))
		{
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
		}
		else{}
	}
	
	public void insertIntoDB()
	{
		String name = productNameTF.getText();
		String id = productIdTF.getText();
		int quantity = Integer.parseInt(quantityTF.getText());
		double price = Double.parseDouble(priceTF.getText());
		
		String query = "INSERT INTO product VALUES ('"+id+"','"+name+"',"+price+","+quantity+");";
		System.out.println(query);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query);
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