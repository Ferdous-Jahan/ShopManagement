import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerUpdate extends JFrame implements ActionListener
{
	JLabel idLabel, nameLabel, phnLabel, addLabel, passLabel;
	JTextField idTF, nameTF, phnTF, addTF, passTF;
	JButton updateBtn, delBtn, backBtn, logoutBtn, loadBtn;
	JPanel panel;
	String userId;
	
	public CustomerUpdate(String userId)
	{
		super("View Details");
		
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 150, 150, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		idLabel = new JLabel("ID : ");
		idLabel.setBounds(250, 150, 120, 30);
		panel.add(idLabel);
		
		idTF = new JTextField();
		idTF.setBounds(400, 150, 120, 30);
		idTF.setEnabled(false);
		panel.add(idTF);
		
		nameLabel = new JLabel("Name : ");
		nameLabel.setBounds(250, 200, 120, 30);
		panel.add(nameLabel);
		
		nameTF = new JTextField();
		nameTF.setBounds(400, 200, 120, 30);
		nameTF.setEnabled(false);
		panel.add(nameTF);
		
		phnLabel = new JLabel("Phone No. : ");
		phnLabel.setBounds(250, 250, 120, 30);
		panel.add(phnLabel);
		
		phnTF = new JTextField();
		phnTF.setBounds(400, 250, 115, 30);
		phnTF.setEnabled(false);
		panel.add(phnTF);
		
		addLabel = new JLabel("Address : ");
		addLabel.setBounds(250, 300, 120, 30);
		panel.add(addLabel);
		
		addTF = new JTextField();
		addTF.setBounds(400, 300, 120, 30);
		addTF.setEnabled(false);
		panel.add(addTF);
		
		passLabel = new JLabel("password : ");
		passLabel.setBounds(250, 350, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(400, 350, 120, 30);
		passTF.setEnabled(false);
		panel.add(passTF);
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(200, 400, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(350, 400, 120, 30);
		delBtn.setEnabled(false);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
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
			Login lg = new Login();
			lg.setVisible(true);
			this.setVisible(false);
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String query = "SELECT `customerName`, `phoneNumber`, `address` FROM `customer` WHERE `userId`='"+userId+"';";
		String query1 = "SELECT `password` FROM `login` WHERE `userId` = '"+userId+"';";
		Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		
		Connection con1=null;//for connection
        Statement st1 = null;//for query execution
		ResultSet rs1 = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			st1 = con1.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			rs1 = st1.executeQuery(query1);
			System.out.println("results received");
			
			boolean flag = false;
			String eName = null;
			String phnNo = null;
			String add = null;
			String pass = null;		
			while(rs.next())
			{
                eName = rs.getString("customerName");
				phnNo = rs.getString("phoneNumber");
				add = rs.getString("address");
				
				flag=true;
				
				idTF.setText(userId);
				nameTF.setText(eName);
				phnTF.setText(phnNo);
				addTF.setText(add);
				nameTF.setEnabled(true);
				phnTF.setEnabled(true);
				addTF.setEnabled(true);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
			}
			
			while(rs1.next())
			{
				pass = rs1.getString("password");
				flag = true;
				
				passTF.setText(pass);
				passTF.setEnabled(true);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
				
			}
			if(!flag)
			{
				nameTF.setText("");
				phnTF.setText("");
				addTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
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
		String name = nameTF.getText();
		String phn = phnTF.getText();
		String add = addTF.getText();
		String pass = passTF.getText();
		
		String query = "UPDATE customer SET customerName='"+name+"', phoneNumber = '"+phn+"', address = '"+add+"' WHERE userId='"+userId+"'";	
        String query1 = "UPDATE login SET password='"+pass+"' WHERE userId='"+userId+"'";
		Connection con=null;//for connection
        Statement st = null;//for query execution
		Connection con1=null;//for connection
        Statement st1 = null;//for query execution
		System.out.println(query);
		System.out.println(query1);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			st = con.createStatement();//create statement
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop1","root","");
			st1 = con1.createStatement();//create statement
			st.executeUpdate(query);
			st1.executeUpdate(query1);
			st.close();
			con.close();
			st1.close();
			con1.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			nameTF.setText("");
			phnTF.setText("");
			addTF.setText("");
			passTF.setText("");
			nameTF.setEnabled(false);
			phnTF.setEnabled(false);
			addTF.setEnabled(false);
			passTF.setEnabled(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void deleteFromDB()
	{
		String newId = userId;
		String query1 = "DELETE from customer WHERE userId='"+newId+"';";
		String query2 = "DELETE from login WHERE userId='"+newId+"';";
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