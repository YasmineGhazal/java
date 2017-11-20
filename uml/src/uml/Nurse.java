package uml;

import javax.swing.JFrame;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;

public class Nurse extends JFrame {
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JPasswordField Old;
	private JPasswordField New;
	private JPasswordField ConfNew;
    Border grayBorder = BorderFactory.createLineBorder(Color.gray, 1);
    Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);  
    Connection c = null;
	java.sql.Statement stmt ;
	java.sql.Statement stmtUpdate ;
	private JTextField PID;
	private JTextField PTemp;
	private JTextField heartRate;
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
	private JTextField tfPN;
	private JTextField tfPPhone;
	private JTextField tfPAddress;
	private JTextField tfHR;
	private JTextField tfTemp;
	private JTextField tfAllergies;
	private JTextField tfNAllergies;

	public Nurse(String ID) {
		this.setVisible(true);;
		this.setSize(467,255);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Nurse");
		this.setLocationRelativeTo(null);
		
		JPanel patient = new JPanel();
		tabbedPane.addTab("patient info", null, patient, null);
		
		JLabel lblID = new JLabel("ID");
		
		PID = new JTextField();
		PID.setColumns(10);
		
		JLabel lblTepreture = new JLabel("Tepreture");
		
		PTemp = new JTextField();
		PTemp.setColumns(10);
		
		JLabel lblHeartRate = new JLabel("Heart rate");
		
		heartRate = new JTextField();
		heartRate.setColumns(10);
		
		JLabel lblHasNewAllergies = new JLabel("Has NEW Allergie(s) to specific Medicine");
		
		JRadioButton rdbtnNYes = new JRadioButton("Yes");
		
		JRadioButton rdbtnNNo = new JRadioButton("No");
		
		tfNAllergies = new JTextField();
		tfNAllergies.setEditable(false);
		tfNAllergies.setColumns(10);
		
		ButtonGroup	rg=new ButtonGroup();
		rg.add(rdbtnNNo);
		rg.add(rdbtnNYes);
		
		
		JButton btnWaitForA = new JButton("wait for a doctor");
		btnWaitForA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PID.getText().trim().equals("")||PTemp.getText().trim().equals("")||heartRate.getText().trim().equals("")||!rdbtnNYes.isSelected()&&!rdbtnNNo.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please insert all informations", "Failure", JOptionPane.ERROR_MESSAGE);						
				}
				else if(rdbtnNYes.isSelected()&&tfNAllergies.getText().trim().equals("")) {
					tfNAllergies.setEditable(true);
					JOptionPane.showMessageDialog(null, "Please insert all informations", "Failure", JOptionPane.ERROR_MESSAGE);		
							}
				else {
					try {
						Class.forName("org.sqlite.JDBC");
				        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
				        stmt = c.createStatement();
				        ResultSet rs=stmt.executeQuery("SELECT ID,Allergies FROM Patient where ID='"+PID.getText()+"';");
				        
				        if (rs.next()) {				        	
				        	 try{
				        		Date d = new Date();
				        		String date=dateFormater.format(d);
				        		String time=timeFormate.format(d);
				        		String Allergies=rs.getString("Allergies");
				        		String A=Allergies;
						        if(rdbtnNYes.isSelected()&&!tfNAllergies.getText().trim().equals(""))
						        	A=Allergies+","+tfNAllergies.getText();
				        		String quary="insert into Visit values("+PID.getText()+",NULL,'"+time+"','00:00:00','','','"+date+"','','"+heartRate.getText()+"','"+PTemp.getText()+"','"+A+"');";
	            		     	PreparedStatement pstmt = c.prepareStatement(quary);
				         	    pstmt.executeUpdate();
				         	    if(!A.equals(Allergies))
					         	    { quary="update Patient set Allergies='"+A+"' where ID='"+PID.getText()+"';";
					         	    pstmt = c.prepareStatement(quary);
					         	    pstmt.executeUpdate();
					         	   }
				         	    c.close();
				         	    JOptionPane.showMessageDialog(null, "Patient entered successfully ");
				              	PID.setText("");
				              	PTemp.setText("");
				              	heartRate.setText("");
				            	rdbtnNYes.setSelected(false);
								rdbtnNNo.setSelected(false);
								tfNAllergies.setText("");
								A="";
			    	 			}catch(Exception e1) {
			   			         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
			    	 			}
				        	
				        
				        }
				        else {
				        	PID.setBorder(errorBorder);
				        	JOptionPane.showMessageDialog(null, "patient ID is not correct ..", "Failure", JOptionPane.ERROR_MESSAGE);
				         }
			    	 }catch(Exception e1) {
			         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
	  		         System.exit(0);
		    	 }
				}
			}}
		);
		
		JButton btnClearAll = new JButton("clear all");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heartRate.setText("");
				PTemp.setText("");
				PID.setText("");
				rdbtnNYes.setSelected(false);
				rdbtnNNo.setSelected(false);
				tfNAllergies.setText("");
			}
		});
		
		
		JLabel label_2 = new JLabel("medicine(s) name(s) :");
		GroupLayout gl_patient = new GroupLayout(patient);
		gl_patient.setHorizontalGroup(
			gl_patient.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_patient.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHeartRate)
						.addComponent(lblTepreture)
						.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_patient.createParallelGroup(Alignment.TRAILING)
							.addComponent(PID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(PTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(heartRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_patient.createSequentialGroup()
							.addGap(24)
							.addComponent(rdbtnNYes, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnNNo, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_patient.createSequentialGroup()
							.addGap(1)
							.addComponent(lblHasNewAllergies, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
						.addGroup(gl_patient.createSequentialGroup()
							.addGroup(gl_patient.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_patient.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnClearAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnWaitForA))
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNAllergies, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
					.addGap(24))
		);
		gl_patient.setVerticalGroup(
			gl_patient.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_patient.createSequentialGroup()
					.addContainerGap(38, Short.MAX_VALUE)
					.addGroup(gl_patient.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_patient.createSequentialGroup()
							.addGroup(gl_patient.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblID)
								.addComponent(PID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(PTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTepreture))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(heartRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHeartRate)))
						.addGroup(gl_patient.createSequentialGroup()
							.addGap(10)
							.addComponent(lblHasNewAllergies)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnNYes)
								.addComponent(rdbtnNNo))
							.addGap(5)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_2)
								.addComponent(tfNAllergies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(3)))
					.addGap(18)
					.addComponent(btnWaitForA)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClearAll)
					.addGap(20))
		);
		patient.setLayout(gl_patient);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("patients first visit ", null, panel, null);
		
		JLabel lblName = new JLabel("Name");
		
		JLabel lblPhone = new JLabel("phone");
		
		JLabel lblAddress = new JLabel("address");
		
		JLabel lblTepreture_1 = new JLabel("tepreture");
		
		JLabel lblHeartRate_1 = new JLabel("heart rate");
		
		tfPN = new JTextField();
		tfPN.setColumns(10);
		
		tfPPhone = new JTextField();
		tfPPhone.setColumns(10);
		
		tfPAddress = new JTextField();
		tfPAddress.setColumns(10);
		
		JLabel lblDateOfBirth = new JLabel("date of birth");
		
		JSpinner spinnerDOB = new JSpinner();
		spinnerDOB.setModel(new SpinnerDateModel());
		spinnerDOB.setEditor(new JSpinner.DateEditor(spinnerDOB, dateFormater.toPattern()));
		
		tfHR = new JTextField();
		tfHR.setColumns(10);
		
		tfTemp = new JTextField();
		tfTemp.setColumns(10);
		
		JLabel lblEHasAllergies = new JLabel("Has Allergie(s) to specific Medicine");
		
		JRadioButton rdbtnYes = new JRadioButton("Yes");
		
		JRadioButton rdbtnNo = new JRadioButton("No");

		ButtonGroup	rgNew=new ButtonGroup();
		rgNew.add(rdbtnNo);
		rgNew.add(rdbtnYes);
		
		JLabel label = new JLabel("medicine(s) name(s) :");
		
		tfAllergies = new JTextField();
		tfAllergies.setEditable(false);
		tfAllergies.setColumns(10);
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if(tfPN.getText().trim().equals("")||tfTemp.getText().trim().equals("")||tfHR.getText().trim().equals("")
					||tfPPhone.getText().trim().equals("")||tfPAddress.getText().trim().equals("")||!rdbtnYes.isSelected()&&!rdbtnNo.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please insert all informations", "Failure", JOptionPane.ERROR_MESSAGE);	
				}
			else if(rdbtnYes.isSelected()&&tfAllergies.getText().trim().equals("")) {
				tfAllergies.setEditable(true);
				if(tfAllergies.getText().trim().equals(""))
						JOptionPane.showMessageDialog(null, "Please insert all informations", "Failure", JOptionPane.ERROR_MESSAGE);				
					}
			else if(!tfPPhone.getText().matches("[0-9]{10}")) {
	 	 			tfPPhone.setBorder(errorBorder);
		        	JOptionPane.showMessageDialog(null, "Phone number must contain 10 digits", "Failure", JOptionPane.ERROR_MESSAGE);
		    	 }
			
			 else {
				 					
				try {
					Date d = new Date();
	        		String date=dateFormater.format(d);
	        		String time=timeFormate.format(d);
					Class.forName("org.sqlite.JDBC");
			        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
			        stmt = c.createStatement();
			        Random rn = new Random();
		            int randomNum =  rn.nextInt(99988) + 10;
			        String NewPID;
					randomNum =  rn.nextInt(99988) + 10;
					NewPID = 3+""+randomNum;
					String A="";
					if(rdbtnYes.isSelected())
						A=tfAllergies.getText();
					
			        String quary="insert into Patient values('"+NewPID+"','"+tfPN.getText()+"','"+tfPAddress.getText()+"','"+tfPPhone.getText()+"','"+dateFormater.format(spinnerDOB.getValue())+"','"+A+"');";
            		     	PreparedStatement pstmt = c.prepareStatement(quary);
			         	    pstmt.executeUpdate();
			              	JOptionPane.showMessageDialog(null, "Successfully added \n patient ID: "+NewPID);
			              	String quary2="insert into Visit values('"+NewPID+"',NULL,'"+time+"','00:00:00','','','"+date+"','','"+tfHR.getText()+"','"+tfTemp.getText()+"','"+A+"');";
            		     	pstmt = c.prepareStatement(quary2);
			         	    pstmt.executeUpdate();
			              	JOptionPane.showMessageDialog(null, "Patient entered successfully ");
			              	c.close();
			              	tfTemp.setText("");
			              	tfHR.setText(""); 	
			              	tfPN.setText("");
			              	tfPAddress.setText("");
			              	tfPPhone.setText("");
			            	rdbtnYes.setSelected(false);
							rdbtnNo.setSelected(false);
							tfAllergies.setText("");
							A="";
						
		    	 	}catch(Exception e1) {
		    	 	    System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		  		         System.exit(0);
			    	 	}
			}	
		}
		});
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName)
								.addComponent(lblAddress)
								.addComponent(lblPhone))
							.addGap(26)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfPAddress)
								.addComponent(tfPPhone)
								.addComponent(tfPN)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblDateOfBirth)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerDOB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblHeartRate_1)
										.addComponent(lblTepreture_1))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tfHR, 0, 0, Short.MAX_VALUE)
										.addComponent(tfTemp, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
									.addComponent(btnAdd))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(tfAllergies, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(32)
									.addComponent(rdbtnYes, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnNo, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(13)
							.addComponent(lblEHasAllergies)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(tfPN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEHasAllergies))
					.addGap(3)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPhone)
								.addComponent(tfPPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAddress)
								.addComponent(tfPAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnYes)
								.addComponent(rdbtnNo))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfAllergies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label))
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDateOfBirth)
								.addComponent(spinnerDOB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addComponent(btnAdd))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTepreture_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfHR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHeartRate_1))))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel changePassword = new JPanel();
		tabbedPane.addTab("change password", null, changePassword, null);
		
		JLabel lblOld = new JLabel("old password");
		
		Old = new JPasswordField();
		
		JLabel lblNew = new JLabel("new password");
		
		New = new JPasswordField();
		
		JLabel lblconfNew = new JLabel("confirm new password");
		
		ConfNew = new JPasswordField();
		
		JButton btnSave = new JButton("save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(passwordsCheck()){
					try {
						Class.forName("org.sqlite.JDBC");
				        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
				        stmt = c.createStatement();
				        ResultSet rs=stmt.executeQuery("SELECT ID FROM Nurse where ID='"+ID+"' and password ='"+Old.getText()+"';");
				        if (rs.next()) {				        	
				        	 try{
                		     String quary="update Nurse set password ='"+New.getText()+"' where ID ='"+ID+"';";
                		     	PreparedStatement pstmt = c.prepareStatement(quary);
				         	    pstmt.executeUpdate();
				         	    c.close();
				              	JOptionPane.showMessageDialog(null, "Password changed");
				              	Old.setText("");
				              	New.setText("");
				              	ConfNew.setText(""); 	
			    	 			}catch(Exception e1) {}
				        	
				        
				        }
				        else {
				        	Old.setBorder(errorBorder);
				        	JOptionPane.showMessageDialog(null, "Old password is not correct ..", "Failure", JOptionPane.ERROR_MESSAGE);
				         }
			    	 }catch(Exception e1) {
				         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		  		         System.exit(0);
			    	 }
							
				}
			}
		});
		
		JButton btnCancle = new JButton("cancle");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Old.setText("");
				New.setText("");
				ConfNew.setText("");
				Old.setBorder(grayBorder);
				New.setBorder(grayBorder);
				ConfNew.setBorder(grayBorder);
				
			}
		});
		GroupLayout gl_changePassword = new GroupLayout(changePassword);
		gl_changePassword.setHorizontalGroup(
			gl_changePassword.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_changePassword.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOld)
						.addComponent(lblNew)
						.addComponent(lblconfNew))
					.addGap(22)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING, false)
						.addComponent(ConfNew)
						.addComponent(New)
						.addComponent(Old, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
					.addContainerGap(101, Short.MAX_VALUE))
				.addGroup(gl_changePassword.createSequentialGroup()
					.addContainerGap(246, Short.MAX_VALUE)
					.addComponent(btnCancle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_changePassword.setVerticalGroup(
			gl_changePassword.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_changePassword.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_changePassword.createSequentialGroup()
							.addComponent(Old, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(New, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_changePassword.createSequentialGroup()
							.addComponent(lblOld)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNew)))
					.addGap(18)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addComponent(lblconfNew)
						.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnCancle))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		changePassword.setLayout(gl_changePassword);
		
	}
	
	boolean passwordsCheck(){
		String pass1=New.getText();
		String pass2=ConfNew.getText();
		String errorMsg="";
		if(pass1.equals("")) {
	 			New.setBorder(errorBorder);
	 			errorMsg+=" * Please enter password \n";
	 		}
	 		else if(!pass1.matches("[a-zA-Z_0-9]{6}")) {
	 			New.setBorder(errorBorder);
	 			errorMsg+=" * Password must be contains 6 letters or numbers \n";
	 		}
	 		if(pass2.equals("")) {
	 			ConfNew.setBorder(errorBorder);
	 			errorMsg+=" * Please enter confirm passsword \n";
	 		}
	 		if(!pass1.equals(pass2)) {
	 			ConfNew.setBorder(errorBorder);
	 			errorMsg+=" * Passwords are not the same \n";
	 		}
	        if(!errorMsg.equals("")) {
	        	JOptionPane.showMessageDialog(null, errorMsg, "Failure", JOptionPane.ERROR_MESSAGE);
	        	return false;
	        }
	        New.setBorder(grayBorder);
	        ConfNew.setBorder(grayBorder);
	        
			return true;
		
	}
}
