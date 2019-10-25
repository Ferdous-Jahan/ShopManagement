import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerEmp extends JFrame implements ActionListener
{
	JLabel id,name,phnNo,address;
	JTextField idTF,nameTF,phnTF,addressTF;
	JButton backBtn, logoutBtn, refreshBtn, loadBtn;
	JPanel panel;
	String userId;
	
	public CustomerEmp(String userId)
	{
		super("view customer");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(600, 50, 100, 30);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(250, 100, 275, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(550, 150, 150, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(500, 400, 120, 30);
		backBtn.addActionListener(this);
		panel.add(backBtn);
		
		id = new JLabel("Customer ID : ");
		id.setBounds(250, 150, 120, 30);
		panel.add(id);
		
		idTF = new JTextField();
		idTF.setBounds(400, 150, 120, 30);
		panel.add(idTF);
		
		name = new JLabel("Customer Name : ");
		name.setBounds(250, 200, 120, 30);
		panel.add(name);
		
		nameTF = new JTextField();
		nameTF.setBounds(400, 200, 120, 30);
		panel.add(nameTF);
		
		phnNo = new JLabel("Phone No. : ");
		phnNo.setBounds(250, 250, 120, 30);
		panel.add(phnNo);
		
		phnTF = new JTextField();
		phnTF.setBounds(400, 250, 115, 30);
		panel.add(phnTF);
		
		address = new JLabel("Address : ");
		address.setBounds(250, 300, 120, 30);
		panel.add(address);
		
		addressTF = new JTextField();
		addressTF.setBounds(400, 300, 120, 30);
		panel.add(addressTF);
		
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
		if(text.equals(refreshBtn.getText()))
		{
			nameTF.setEnabled(true);
			idTF.setText("");
			nameTF.setText("");
			addressTF.setText("");
			phnTF.setText("");
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
		else{}
	}
	
	public void loadFromDB()
	{
		String loadname = nameTF.getText();
		String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer` WHERE `CustomerName`='"+loadname+"';";     
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
			String id = null;
			String phnNo = null;
			String add = null;			
			while(rs.next())
			{
                id = rs.getString("userId");
				phnNo = rs.getString("phoneNumber");
				add = rs.getString("address");
				flag=true;
				
				idTF.setText(id);
				phnTF.setText(phnNo);
				addressTF.setText(add);
				nameTF.setEnabled(false);
			}
			if(!flag)
			{
				idTF.setText("");
				nameTF.setText("");
				phnTF.setText("");
				addressTF.setText("");
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
}