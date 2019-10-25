import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeUpdate extends JFrame implements ActionListener
{
	JLabel nameLabel,phnLabel,passLabel;
	JTextField nameTF, phnTF, passTF;
	JButton updateBtn, backBtn, logoutBtn, loadBtn;
	JPanel panel;
	String userId;
	
	public EmployeeUpdate(String userId)
	{
		super("view deatils");
		
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
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(200, 400, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		nameLabel = new JLabel("name : ");
		nameLabel.setBounds(250, 150, 120, 30);
		panel.add(nameLabel);
		
		nameTF = new JTextField();
		nameTF.setBounds(400, 150, 120, 30);
		nameTF.setEnabled(false);
		panel.add(nameTF);
		
		phnLabel = new JLabel("Phone No. : ");
		phnLabel.setBounds(250, 250, 120, 30);
		panel.add(phnLabel);
		
		phnTF = new JTextField();
		phnTF.setBounds(400, 250, 115, 30);
		phnTF.setEnabled(false);
		panel.add(phnTF);
		
		passLabel = new JLabel("password : ");
		passLabel.setBounds(250, 350, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(400, 350, 120, 30);
		passTF.setEnabled(false);
		panel.add(passTF);
		
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
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();			
		}
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
		}
		else{}
	}
	
	public void loadFromDB()
	{
		String query = "SELECT `employeeName`, `phoneNumber` FROM `employee` WHERE `userId`='"+userId+"';";
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
			String pass = null;		
			while(rs.next())
			{
                eName = rs.getString("employeeName");
				phnNo = rs.getString("phoneNumber");
				
				flag=true;
				
				nameTF.setText(eName);
				phnTF.setText(phnNo);
				nameTF.setEnabled(true);
				phnTF.setEnabled(true);
				updateBtn.setEnabled(true);
			}
			
			while(rs1.next())
			{
				pass = rs1.getString("password");
				flag = true;
				
				passTF.setText(pass);
				passTF.setEnabled(true);
				updateBtn.setEnabled(true);				
			}
			if(!flag)
			{
				nameTF.setText("");
				phnTF.setText("");
				passTF.setText("");
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
		String pass = passTF.getText();
		
		String query = "UPDATE employee SET employeeName='"+name+"', phoneNumber = '"+phn+"' WHERE userId='"+userId+"'";	
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
			nameTF.setText("");
			phnTF.setText("");
			passTF.setText("");
			nameTF.setEnabled(false);
			phnTF.setEnabled(false);
			passTF.setEnabled(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
}