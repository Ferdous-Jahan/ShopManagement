import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class SignUp extends JFrame implements ActionListener
{
	JLabel userLabel, passLabel, eNameLabel, phoneLabel, addLabel;
	JTextField userTF, passTF, phoneTF2, eNameTF, addTF;
	JButton addBtn, backBtn;
	JPanel panel;
	
	public SignUp()
	{
	super("Sign Up");
	
	this.setSize(800, 800);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	panel = new JPanel();
	panel.setLayout(null);
	
	userLabel = new JLabel("User ID : ");
	userLabel.setBounds(250, 100, 120, 30);
	panel.add(userLabel);
	
	userTF = new JTextField();
	userTF.setBounds(400, 100, 120, 30);
	panel.add(userTF);
	
	passLabel = new JLabel("Password : ");
	passLabel.setBounds(250, 150, 120, 30);
	panel.add(passLabel);

	passTF = new JTextField();
	passTF.setBounds(400, 150, 120, 30);
	panel.add(passTF);
	
	eNameLabel = new JLabel("Name : ");
	eNameLabel.setBounds(250, 200, 120, 30);
	panel.add(eNameLabel);
		
	eNameTF = new JTextField();
	eNameTF.setBounds(400, 200, 120, 30);
	panel.add(eNameTF);
	
	phoneLabel = new JLabel("Phone No. : ");
	phoneLabel.setBounds(250, 250, 120, 30);
	panel.add(phoneLabel);	
	
	phoneTF2 = new JTextField();
	phoneTF2.setBounds(400, 250, 120, 30);
	panel.add(phoneTF2);
	
	addLabel = new JLabel("address : ");
	addLabel.setBounds(250, 350, 120, 30);
	panel.add(addLabel);
	
	addTF = new JTextField();
	addTF.setBounds(400, 350, 120, 30);
	panel.add(addTF);
	
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
		String newId = userTF.getText();
		String newPass = passTF.getText();
		String eName = eNameTF.getText();
		String phnNo = phoneTF2.getText();
		String add = addTF.getText();
		int status = 1;
		
		
		String query1 = "INSERT INTO customer VALUES ('"+newId+"','"+eName+"','"+phnNo+"','"+add+"');";
		String query2 = "INSERT INTO Login VALUES ('"+newId+"','"+newPass+"',"+status+");";
		System.out.println(query1);
		System.out.println(query2);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
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