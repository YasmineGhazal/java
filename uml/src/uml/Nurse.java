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
import javax.swing.ImageIcon;
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
import java.awt.Font;

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
		this.setSize(650,416);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Nurse");
		this.setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon(auth.class.getResource("/images/HIS.png"));
		this.setIconImage(img.getImage());
		JPanel patient = new JPanel();
		patient.setBackground(new Color(113,207,243));
		tabbedPane.addTab("patient info", null, patient, null);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		PID = new RoundJTextField(50);
		PID.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		PID.setColumns(10);
		
		PTemp = new RoundJTextField(50);
		PTemp.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		PTemp.setColumns(10);
		
		heartRate = new RoundJTextField(50);
		heartRate.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		heartRate.setColumns(10);
		
		JLabel lblHasNewAllergies = new JLabel("Has NEW Allergie(s) to specific Medicine");
		lblHasNewAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JRadioButton rdbtnNYes = new JRadioButton("Yes");
		rdbtnNYes.setBackground(new Color(113,207,243));
		rdbtnNYes.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		rdbtnNYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNYes.isSelected()) {
					tfNAllergies.setEditable(true);
				}
					
			}
		});
		
		JRadioButton rdbtnNNo = new JRadioButton("No");
		rdbtnNNo.setBackground(new Color(113,207,243));
		rdbtnNNo.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		rdbtnNNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNNo.isSelected()) {
					tfNAllergies.setEditable(false);
				}
			}
		});
		
		tfNAllergies = new JTextField();
		tfNAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfNAllergies.setEditable(false);
		tfNAllergies.setColumns(10);
		
		ButtonGroup	rg=new ButtonGroup();
		rg.add(rdbtnNNo);
		rg.add(rdbtnNYes);
		
		
		JButton btnWaitForA = new JButton("OK");
		btnWaitForA.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		
		JButton btnClearAll = new JButton("Clear");
		btnClearAll.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		label_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_6 = new JLabel("Tepmreture");
		label_6.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_7 = new JLabel("Heart rate");
		label_7.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(Nurse.class.getResource("/images/n1.png")));
		GroupLayout gl_patient = new GroupLayout(patient);
		gl_patient.setHorizontalGroup(
			gl_patient.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_patient.createSequentialGroup()
					.addGap(99)
					.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_patient.createSequentialGroup()
							.addGroup(gl_patient.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblID, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
								.addComponent(label_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(41)
							.addGroup(gl_patient.createParallelGroup(Alignment.LEADING, false)
								.addComponent(PTemp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(heartRate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(PID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 286, Short.MAX_VALUE))
						.addGroup(gl_patient.createSequentialGroup()
							.addComponent(lblHasNewAllergies, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
							.addGap(121))
						.addGroup(gl_patient.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNAllergies, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(47)
							.addComponent(btnWaitForA)
							.addGap(16)
							.addComponent(btnClearAll)))
					.addContainerGap())
				.addGroup(gl_patient.createSequentialGroup()
					.addGap(302)
					.addComponent(rdbtnNYes, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(rdbtnNNo, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(281))
		);
		gl_patient.setVerticalGroup(
			gl_patient.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_patient.createSequentialGroup()
					.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_patient.createSequentialGroup()
							.addGroup(gl_patient.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_patient.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_patient.createSequentialGroup()
									.addGap(76)
									.addGroup(gl_patient.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_patient.createSequentialGroup()
											.addComponent(PID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(14)
											.addComponent(PTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(heartRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_patient.createSequentialGroup()
											.addComponent(lblID)
											.addGap(44)
											.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblHasNewAllergies)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnNYes)
								.addComponent(rdbtnNNo))
							.addGap(14)
							.addGroup(gl_patient.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_2)
								.addComponent(tfNAllergies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnWaitForA)
								.addComponent(btnClearAll)))
						.addGroup(gl_patient.createSequentialGroup()
							.addGap(104)
							.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(60, Short.MAX_VALUE))
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
		panel.setBackground(new Color(14,120,160));
		tabbedPane.addTab("patients first visit ", null, panel, null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblPhone = new JLabel("phone");
		lblPhone.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblAddress = new JLabel("address");
		lblAddress.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblTepreture_1 = new JLabel("Tepmreture");
		lblTepreture_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblHeartRate_1 = new JLabel("Heart rate");
		lblHeartRate_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		tfPN = new JTextField();
		tfPN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfPN.setColumns(10);
		
		tfPPhone = new JTextField();
		tfPPhone.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfPPhone.setColumns(10);
		
		tfPAddress = new JTextField();
		tfPAddress.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfPAddress.setColumns(10);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth");
		lblDateOfBirth.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JSpinner spinnerDOB = new JSpinner();
		spinnerDOB.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		spinnerDOB.setModel(new SpinnerDateModel());
		spinnerDOB.setEditor(new JSpinner.DateEditor(spinnerDOB, dateFormater.toPattern()));
		
		tfHR = new JTextField();
		tfHR.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		tfHR.setColumns(10);
		
		tfTemp = new JTextField();
		tfTemp.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		tfTemp.setColumns(10);
		
		JLabel lblEHasAllergies = new JLabel("Has Allergie(s) to specific Medicine");
		lblEHasAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBackground(new Color(14,120,160));
		rdbtnYes.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		rdbtnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnYes.isSelected()) {
					tfAllergies.setEditable(true);
				}
					
			}
		});
		JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBackground(new Color(14,120,160));
		rdbtnNo.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		rdbtnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNo.isSelected()) {
					tfAllergies.setEditable(false);
				}
					
			}
		});
		ButtonGroup	rgNew=new ButtonGroup();
		rgNew.add(rdbtnNo);
		rgNew.add(rdbtnYes);
		
		JLabel label = new JLabel("medicine(s) name(s) :");
		label.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		tfAllergies = new JTextField();
		tfAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfAllergies.setEditable(false);
		tfAllergies.setColumns(10);
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Nurse.class.getResource("/images/nnp.png")));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDateOfBirth)
									.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
									.addComponent(spinnerDOB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblName)
										.addComponent(lblPhone)
										.addComponent(lblAddress))
									.addGap(26)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tfPAddress)
										.addComponent(tfPPhone)
										.addComponent(tfPN)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTepreture_1)
										.addComponent(lblHeartRate_1))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(tfHR, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
										.addComponent(tfTemp, 0, 0, Short.MAX_VALUE))))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(68)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addGap(86)
													.addComponent(rdbtnYes, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
													.addGap(31)
													.addComponent(rdbtnNo, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
												.addComponent(tfAllergies, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
												.addGroup(gl_panel.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(50)
											.addComponent(btnAdd)))
									.addContainerGap())
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblEHasAllergies)
									.addGap(20))))
						.addComponent(label_3, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfPN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName)
						.addComponent(lblEHasAllergies))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnNo)
								.addComponent(rdbtnYes)
								.addComponent(lblPhone))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(label)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfAllergies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(4)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAddress)
										.addComponent(tfPAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(tfPPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblDateOfBirth)
											.addGap(22)
											.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblTepreture_1)
												.addComponent(tfTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(spinnerDOB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblHeartRate_1)
										.addComponent(tfHR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(41)
									.addComponent(btnAdd)))
							.addContainerGap(122, Short.MAX_VALUE))
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		JPanel changePassword = new JPanel();
		changePassword.setBackground(new Color(253,239,117));
		tabbedPane.addTab("change password", null, changePassword, null);
		
		JLabel lblOld = new JLabel("old password");
		lblOld.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		Old = new RoundJPasswordField(50);
		Old.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		JLabel lblNew = new JLabel("new password");
		lblNew.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		New = new RoundJPasswordField(50);
		New.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		JLabel lblconfNew = new JLabel("confirm new password");
		lblconfNew.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		ConfNew = new RoundJPasswordField(50);
		ConfNew.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		JButton btnSave = new JButton("save");
		btnSave.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		btnCancle.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Nurse.class.getResource("/images/n2.png")));
		GroupLayout gl_changePassword = new GroupLayout(changePassword);
		gl_changePassword.setHorizontalGroup(
			gl_changePassword.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_changePassword.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_changePassword.createSequentialGroup()
							.addComponent(lblNew)
							.addContainerGap())
						.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_changePassword.createSequentialGroup()
								.addComponent(lblconfNew)
								.addContainerGap())
							.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_changePassword.createSequentialGroup()
									.addComponent(lblOld)
									.addContainerGap())
								.addGroup(gl_changePassword.createSequentialGroup()
									.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_changePassword.createSequentialGroup()
											.addGap(117)
											.addComponent(btnCancle)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnSave))
										.addGroup(gl_changePassword.createSequentialGroup()
											.addGap(129)
											.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
									.addComponent(label_1))))))
				.addGroup(gl_changePassword.createSequentialGroup()
					.addGap(156)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(Old, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(New, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
					.addGap(354))
		);
		gl_changePassword.setVerticalGroup(
			gl_changePassword.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_changePassword.createSequentialGroup()
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_changePassword.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblOld))
						.addGroup(gl_changePassword.createSequentialGroup()
							.addGap(32)
							.addComponent(Old, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_changePassword.createSequentialGroup()
							.addGap(38)
							.addComponent(New, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_changePassword.createSequentialGroup()
							.addGap(11)
							.addComponent(lblNew)))
					.addGap(18)
					.addGroup(gl_changePassword.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_changePassword.createSequentialGroup()
							.addGap(58)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 199, Short.MAX_VALUE))
						.addGroup(gl_changePassword.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblconfNew)
							.addGap(18)
							.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addGroup(gl_changePassword.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCancle))
							.addContainerGap())))
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