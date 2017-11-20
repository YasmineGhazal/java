package uml;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class auth extends JFrame {
	JButton ok=new JButton("Login");
	private JTextField tfID;
	private JPasswordField tfPassword;

	auth(){
		getContentPane().setEnabled(false);
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(10);
		borderLayout.setHgap(10);
		this.setSize(423,131);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Log in");
		this.setLocationRelativeTo(null);
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(2, 2, 5, 5));
		
		JLabel lblID = new JLabel("ID : ");
		lblID.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblID);
		
		tfID = new JTextField();
		tfID.setToolTipText("enter your ID");
		panel_1.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblPassword = new JLabel("password :");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblPassword);
		
		tfPassword = new JPasswordField();
		tfPassword.setToolTipText("Enter your password");
		panel_1.add(tfPassword);
		tfPassword.setColumns(10);
				
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnLogin = new JButton("login");
		btnLogin.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID=tfID.getText();
				String password=tfPassword.getText();
				if(ID.trim().equals("")||password.trim().equals("")){
					JOptionPane.showMessageDialog(null,
						    "Please enter your ID and password. ",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
			          try {
			  	    	 Connection c = null;
			  	    	 java.sql.Statement stmt ;
			  	    	 Class.forName("org.sqlite.JDBC");
			  	         c = DriverManager.getConnection("jdbc:sqlite:DB.db");
			  	         stmt = c.createStatement();
						 ResultSet rs=null;
						 String tableName="";
						 if(ID.startsWith("0")) 
							 tableName="Manager";
						 else if(ID.startsWith("1"))
							 tableName="Doctor";
						 else if(ID.startsWith("2"))
							 tableName="Nurse";
						 try {
						 rs = stmt.executeQuery("SELECT * FROM "+tableName+" where ID = '"+ID+"' AND password = '"+password+"'");
						 }catch(org.sqlite.SQLiteException ee) {
							 JOptionPane.showMessageDialog(null,
									    "wrong ID or password ",
									    "Inane error",
									    JOptionPane.ERROR_MESSAGE);
							 JOptionPane.showMessageDialog(null, ee.getClass().getName() + ": " + ee.getMessage() );

			            		c.close();
						 }
						 
						 if(rs.next()) {
							 auth.this.setVisible(false);
							 c.close();
							 JFrame nextFrame;
							 JOptionPane.showMessageDialog(null,"Welcome "+tableName,"",JOptionPane.INFORMATION_MESSAGE);
							 if(tableName.equals("Manager")) {
								  nextFrame= new Manager();
							 }
							 else if(tableName.equals("Nurse")) {
								  nextFrame= new Nurse(ID);
							 }
							 else if(tableName.equals("Doctor")) {
								  nextFrame= new Doctor(ID);
							 }
						 }
						else {
		            		JOptionPane.showMessageDialog(null,
								    "wrong ID or password ",
								    "Inane error",
								    JOptionPane.ERROR_MESSAGE);
		            		c.close();
		             }
						 
			          } catch ( Exception e1 ) {
			  		         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
			  		         System.exit(0);
			  		      }					
				}
			}
				  					
		});
		pack();
		setVisible(true);	
	}

}
