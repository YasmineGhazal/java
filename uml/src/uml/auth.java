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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class auth extends JFrame {
	public static void main(String[] args) {
		new auth();
	}

	JButton ok=new JButton("Login");

	auth(){
		getContentPane().setBackground(new Color(128, 185, 215));
		getContentPane().setEnabled(false);
		this.setSize(547,320);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Log in");
		this.setLocationRelativeTo(null);
				
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 185, 215));
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		ImageIcon img = new ImageIcon(auth.class.getResource("/images/HIS.png"));
		this.setIconImage(img.getImage());
		
		JButton btnLogin = new JButton("login");
		btnLogin.setBackground(SystemColor.inactiveCaption);
		btnLogin.setFont(new Font("Palatino Linotype", Font.BOLD, 24));
		btnLogin.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(auth.class.getResource("/images/login.jpg")));
		
		JLabel lblID = new JLabel("ID : ");
		lblID.setHorizontalAlignment(SwingConstants.CENTER);
		lblID.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
		
		RoundJTextField tfID = new RoundJTextField(20);
		tfID.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		tfID.setBackground(SystemColor.inactiveCaptionBorder);
		tfID.setToolTipText("enter your ID");
		tfID.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
		
		RoundJPasswordField tfPassword = new RoundJPasswordField(20);
		tfPassword.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		tfPassword.setBackground(SystemColor.inactiveCaptionBorder);
		tfPassword.setToolTipText("Enter your password");
		tfPassword.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(38)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfID, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
								.addComponent(tfPassword, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
					.addGap(34)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(tfID, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addGap(21)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfPassword, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
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