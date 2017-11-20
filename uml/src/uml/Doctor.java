package uml;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Doctor extends JFrame{
	Border grayBorder = BorderFactory.createLineBorder(Color.gray, 1);
	Border errorBorder = BorderFactory.createLineBorder(Color.RED, 1);  
	Connection c = null;
	java.sql.Statement stmt ;
	java.sql.Statement stmtUpdate ;
	private JTextField tfName;
	private JTextField tfID;
	private JTextField tfAge;
	private JTextField tfTempreture;
	private JTextField tfHeartRate;
	private JTextField tfDiagnosis;
	private JTextField tfMedicine;
	private JTextField tfTests;
	private JPasswordField Old;
	private JPasswordField New;
	private JPasswordField ConfNew;
	private JTable table;
	Date d = new Date();
	public String time;
	String PID;

	Doctor (String ID) {
		this.setVisible(true);;
		this.setSize(587,496);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Doctor");
		this.setLocationRelativeTo(null);
		DefaultTableModel model = new DefaultTableModel();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane)
		);
		
		JTextArea taAllergies = new JTextArea();
		taAllergies.setEditable(false);
		JPanel panel = new JPanel();
		tabbedPane.addTab("enter patient", null, panel, null);
		
		JButton btnEnter = new JButton("enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
			        if (model.getRowCount() > 0) {
			            for (int i = model.getRowCount() - 1; i > -1; i--) {
			            	model.removeRow(i);
			            }}
					SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
					Class.forName("org.sqlite.JDBC");
			        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
			        stmt = c.createStatement();
			        ResultSet rs=stmt.executeQuery("SELECT  MIN(arrival)  FROM Visit where doctorID IS NULL ;");
			        if(rs.next()) {
		        		time=timeFormate.format(d);
		        		String day=dateFormater.format(d);
			        	String arrival=rs.getString("MIN(arrival)");
				       	rs=stmt.executeQuery("SELECT  patientID,name,date_of_birth,tempreture, heart_rate,Patient.Allergies  FROM Visit, Patient where doctorID IS NULL and arrival = '"+arrival+"' and PatientID=ID;");
			           	PID=rs.getString("patientID");
				       	int age=Integer.parseInt(day.substring(0,4))-Integer.parseInt(rs.getString("date_of_birth").substring(0,4));
			        	tfName.setText(rs.getString("name"));
				       	tfID.setText(rs.getString("patientID"));
				       	tfTempreture.setText(rs.getString("tempreture"));
				       	tfHeartRate.setText(rs.getString("heart_rate"));
				       	taAllergies.setText(rs.getString("Allergies"));
				       	tfAge.setText(""+age);
				       	rs=stmt.executeQuery("SELECT  lab_test_requested,medicine_prescribed,diagnosis,date FROM Visit where patientID ='"+PID+"';");
			           	int i=1;
				       	while(rs.next()) {
			           		model.addRow(new String[]{i+"",rs.getString("date"),rs.getString("diagnosis"),rs.getString("medicine_prescribed"),rs.getString("lab_test_requested")});
			           		i++;
				       	}
				       		       	
			        }
			        else {
			        	JOptionPane.showMessageDialog(null,"There is not any waiting patients");
								}
			        c.close();
				}catch(Exception e1) {
						JOptionPane.showMessageDialog(null,"There is not any waiting patients");
					}		
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Patient information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(time!=null) {
					try {
		        		String day=dateFormater.format(d);
						Class.forName("org.sqlite.JDBC");
						c = DriverManager.getConnection("jdbc:sqlite:DB.db");
						String quary="update Visit set doctorID ='"+ID+"', enter='"+time+"',diagnosis='"+tfDiagnosis.getText()+"',medicine_prescribed='"+tfMedicine.getText()+"',lab_test_requested='"+tfTests.getText()+"' where patientID ='"+PID+"' and date='"+day+"';";
						PreparedStatement pstmt = c.prepareStatement(quary);
						pstmt.executeUpdate();
						c.close();
						JOptionPane.showMessageDialog(null, "Done successfully");
						  if (model.getRowCount() > 0) {
					            for (int i = model.getRowCount() - 1; i > -1; i--) {
					            	model.removeRow(i);
					            }}
							tfName.setText("");
							tfAge.setText("");
							tfTempreture.setText("");
							tfID.setText("");
							tfHeartRate.setText("");
							tfDiagnosis.setText("");
							tfMedicine.setText("");
							tfTests.setText("");	
							taAllergies.setText("");
					}catch(Exception e1) {
				         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		  		         System.exit(0);	
					}
				}
			}
		});
		
		JButton btnClearAll = new JButton("clear all");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (model.getRowCount() > 0) {
		            for (int i = model.getRowCount() - 1; i > -1; i--) {
		            	model.removeRow(i);
		            }}
				tfName.setText("");
				tfAge.setText("");
				tfTempreture.setText("");
				tfID.setText("");
				tfHeartRate.setText("");
				tfDiagnosis.setText("");
				tfMedicine.setText("");
				tfTests.setText("");	
				taAllergies.setText("");	
			}
		});
		
		JLabel lblDiagnosis = new JLabel("Diagnosis");
		
		tfDiagnosis = new JTextField();
		tfDiagnosis.setColumns(10);
		
		JLabel lblMedicinePrescribed = new JLabel("Medicine prescribed");
		
		tfMedicine = new JTextField();
		tfMedicine.setColumns(10);
		
		JLabel lblLabTestRequested = new JLabel("Lab test requested");
		
		tfTests = new JTextField();
		tfTests.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblPreviousVisits = new JLabel(" previous visits : ");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(31)
					.addComponent(lblPreviousVisits)
					.addContainerGap(377, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 355, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblDiagnosis)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(tfDiagnosis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblMedicinePrescribed)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tfMedicine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED, 74, Short.MAX_VALUE)
							.addComponent(lblLabTestRequested)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(tfTests, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(28))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnClearAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap(30, Short.MAX_VALUE)
							.addComponent(btnEnter)
							.addGap(13)
							.addComponent(btnDone)
							.addGap(9)
							.addComponent(btnClearAll)
							.addGap(28))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDiagnosis)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfDiagnosis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblLabTestRequested)
							.addComponent(tfTests, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMedicinePrescribed)
						.addComponent(tfMedicine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblPreviousVisits)
					.addGap(2)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		model.addColumn("Visit #");
		model.addColumn("Date");
		model.addColumn("Diagnosis");
		model.addColumn("Medicine(s)");
		model.addColumn("Lab test(s)");	
		
		JLabel lblName = new JLabel("Name");
		
		JLabel lblId = new JLabel("ID");
		
		JLabel lblAge = new JLabel("Age");
		
		tfName = new JTextField();
		tfName.setEditable(false);
		tfName.setColumns(10);
		
		tfID = new JTextField();
		tfID.setEditable(false);
		tfID.setColumns(10);
		
		tfAge = new JTextField();
		tfAge.setEditable(false);
		tfAge.setColumns(10);
		
		JLabel lblTepreture = new JLabel("Tepreture");
		
		JLabel lblNewLabel = new JLabel("Heart rate");
		
		tfTempreture = new JTextField();
		tfTempreture.setEditable(false);
		tfTempreture.setColumns(10);
		
		tfHeartRate = new JTextField();
		tfHeartRate.setEditable(false);
		tfHeartRate.setColumns(10);
		
		JLabel lblAllergies = new JLabel("Has Allergie(s) to Medicine :");
	
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAge, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
								.addComponent(lblId, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
								.addComponent(lblName))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
									.addComponent(tfAge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(18)
									.addComponent(taAllergies, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblAllergies)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(21)
							.addComponent(lblTepreture)
							.addGap(4)
							.addComponent(tfTempreture, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfHeartRate, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblName))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblId))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfAge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAge)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblAllergies)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(taAllergies, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfTempreture, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTepreture)
						.addComponent(lblNewLabel)
						.addComponent(tfHeartRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26))
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("change password", null, panel_1, null);
		
		JLabel label = new JLabel("old password");
		
		JLabel label_1 = new JLabel("new password");
		
		JLabel label_2 = new JLabel("confirm new password");
		
		Old = new JPasswordField();
		
		New = new JPasswordField();
		
		ConfNew = new JPasswordField();
		
		JButton btnCancle = new JButton("cancle");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Old.setText("");
				New.setText("");
				ConfNew.setText("");
				Old.setBorder(grayBorder);
				New.setBorder(grayBorder);
				ConfNew.setBorder(grayBorder);
			}
		});
		
		JButton btnSave = new JButton("save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(passwordsCheck()){
					Old.setBorder(grayBorder);
					try {
						Class.forName("org.sqlite.JDBC");
				        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
				        stmt = c.createStatement();
				        ResultSet rs=stmt.executeQuery("SELECT ID FROM Doctor where ID='"+ID+"' and password ='"+Old.getText()+"';");
				        if (rs.next()) {				        	
				        	 try{
                		     String quary="update Doctor set password ='"+New.getText()+"' where ID ='"+ID+"';";
                		     	PreparedStatement pstmt = c.prepareStatement(quary);
				         	    pstmt.executeUpdate();
				              	JOptionPane.showMessageDialog(null, "Password changed");
				              	c.close();
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
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
									.addGap(221)
									.addComponent(btnCancle, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(New, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addComponent(Old, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))))
							.addGap(12)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(100)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(Old, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(New, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancle)
						.addComponent(btnSave))
					.addContainerGap(214, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		}
	
	boolean passwordsCheck(){
		String pass1=New.getText();
		String pass2=ConfNew.getText();
		String errorMsg="";
		if(pass1.equals("")) {
	 			New.setBorder(errorBorder);
	 			errorMsg+=" * Please enter the new password \n";
	 		}
	 		else if(!pass1.matches("[a-zA-Z_0-9]{6}")) {
	 			New.setBorder(errorBorder);
	 			errorMsg+=" * Password must contain 6 letters or numbers \n";
	 		}
	 		if(pass2.equals("")) {
	 			ConfNew.setBorder(errorBorder);
	 			errorMsg+=" * Please enter confirm passsword \n";
	 		}
	 		else if(!pass1.equals(pass2)) {
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