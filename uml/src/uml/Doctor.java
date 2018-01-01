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
import javax.swing.ImageIcon;
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
import java.awt.Font;
import javax.swing.JComboBox;

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
	private JPasswordField Old;
	private JPasswordField New;
	private JPasswordField ConfNew;
	private JTable table;
	private JComboBox tfTest;
	Date d = new Date();
	String time;
	String PID;

	Doctor (String ID) {
		this.setVisible(true);;
		this.setSize(776,569);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setTitle("Doctor");
		this.setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon(auth.class.getResource("/images/HIS.png"));
		this.setIconImage(img.getImage());
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
		taAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		taAllergies.setEditable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(235,235,235));
		tabbedPane.addTab("enter patient", null, panel, null);
		
		JButton btnEnter = new JButton("enter");
		btnEnter.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		btnDone.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(time!=null) {
					try {
		        		String day=dateFormater.format(d);
						Class.forName("org.sqlite.JDBC");
						c = DriverManager.getConnection("jdbc:sqlite:DB.db");
						String quary="update Visit set doctorID ='"+ID+"', enter='"+time+"',diagnosis='"+tfDiagnosis.getText()+"',medicine_prescribed='"+tfMedicine.getText()+"',lab_test_requested='"+tfTest.getSelectedItem().toString()+"' where patientID ='"+PID+"' and date='"+day+"';";
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
							tfTest.setSelectedIndex(0);	
							taAllergies.setText("");
					}catch(Exception e1) {
				         System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
		  		         System.exit(0);	
					}
				}
			}
		});
		
		JButton btnClearAll = new JButton("cancle");
		btnClearAll.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
				tfTest.setSelectedIndex(0);
				taAllergies.setText("");	
			}
		});
		
		JLabel lblDiagnosis = new JLabel("Diagnosis");
		lblDiagnosis.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		tfDiagnosis = new JTextField();
		tfDiagnosis.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfDiagnosis.setColumns(10);
		
		JLabel lblMedicinePrescribed = new JLabel("Medicine prescribed");
		lblMedicinePrescribed.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		tfMedicine = new JTextField();
		tfMedicine.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfMedicine.setColumns(10);
		
		JLabel lblLabTestRequested = new JLabel("Lab test requested");
		lblLabTestRequested.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblPreviousVisits = new JLabel(" previous visits : ");
		lblPreviousVisits.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
	    String[] labTests= {
	    		"None",
	    		"ACTH Suppression",
	    		"Adrenocorticotropic Hormone (ACTH)",
	    		"Alanine Aminotransferase (ALT)",
	    		"Albumin",
	    		"Alkaline Phosphatase",
	    		"Allergy Tests",
	    		"Alpha-Fetoprotein (AFP)",
	    		"Amylase",
	    		"Antibody Tests (Coombs Test)",
	    		"Antinuclear Antibodies (ANA)",
	    		"Aspartate Aminotransferase (AST)",
	    		"Bicarbonate (Carbon Dioxide)",
	    		"Bilirubin",
	    		"Blood Culture",
	    		"Blood Glucose",
	    		"Blood Type",
	    		"Blood Urea Nitrogen (BUN)",
	    		"Breast Cancer (BRCA) Gene",
	    		"C-Reactive Protein (CRP)",
	    		"Calcium (Ca)",
	    		"Cardiac Enzyme Studies",
	    		"CD4+ Count",
	    		"Chemistry Screen",
	    		"Chlamydia Tests",
	    		"Chloride (Cl)",
	    		"Cholesterol and Triglycerides",
	    		"Cobalamin",
	    		"Complete Blood Count (CBC)",
	    		"Coombs Test",
	    		"Creatinine and Creatinine Clearance",
	    		"Dexamethasone Suppression Test",
	    		"Electrolyte Panel ",
	    		"Estrogens",
	    		"Folic Acid",
	    		"Follicle-Stimulating Hormone",
	    		"Globulin",
	    		"Glucose",
	    		"Glycohemoglobin (HbA1c, A1c)",
	    		"Gonorrhea",
	    		"Growth Hormone",
	    		"HDL Cholesterol",
	    		"Helicobacter pylori",
	    		"Hepatitis Panel",
	    		"Homocysteine",
	    		"Human Chorionic Gonadotropin (hCG)",
	    		"Human Immunodeficiency Virus (HIV)",
	    		"Iron (Fe)",
	    		"Ketones",
	    		"Lactic Acid Dehydrogenase (LDH)",
	    		"LDL Cholesterol",
	    		"Lead (Pb)",
	    		"Liver Function Panel",
	    		"Magnesium (Mg)",
	    		"Microalbumin Urine Test",
	    		"Mononucleosis  ",
	    		"Parathyroid Hormone (PTH)",
	    		"Partial Thromboplastin Time",
	    		"Phosphate (Phosphorus)",
	    		"Potassium (K) in Blood",
	    		"Potassium (K) in Urine",
	    		"Pregnancy Test",
	    		"Progesterone",
	    		"Prolactin",
	    		"Prostate-Specific Antigen (PSA)",
	    		"Prothrombin Time",
	    		"Reticulocyte Count",
	    		"Rheumatoid Factor (RF)",
	    		"Rubella",
	    		"Sedimentation Rate",
	    		"Sickle Cell Test",
	    		"Sodium (Na)",
	    		"Stool Analysis",
	    		"Stool Analysis for Giardiasis (Ova and Parasite Test)",
	    		"Stool Antigen Test",
	    		"Stool Culture",
	    		"Syphilis",
	    		"Testosterone",
	    		"Thyroid Hormone",
	    		"Thyroid-Stimulating Hormone (TSH)",
	    		"Total Serum Protein",
	    		"Uric Acid",
	    		"Urine Test",
	    		"Viral Tests",
	    		"Vitamin B12"};
		 tfTest = new JComboBox(labTests);
		tfTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Doctor.class.getResource("/images/d1.png")));
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Doctor.class.getResource("/images/d2.png")));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblDiagnosis)
													.addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
													.addComponent(tfDiagnosis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblMedicinePrescribed)
													.addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
													.addComponent(tfMedicine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
											.addGap(81))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblLabTestRequested)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(tfTest, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
											.addGap(92)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnClearAll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnEnter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
										.addComponent(btnDone, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)))
								.addComponent(lblPreviousVisits))
							.addGap(29)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 744, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 608, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(label_3)
							.addGap(10)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(22)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDiagnosis)
										.addComponent(tfDiagnosis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMedicinePrescribed)
										.addComponent(tfMedicine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(6)
									.addComponent(btnEnter)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDone, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLabTestRequested)
								.addComponent(tfTest, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnClearAll))
							.addGap(18)
							.addComponent(lblPreviousVisits)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addGap(78))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_3)
							.addContainerGap())))
		);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		model.addColumn("Visit #");
		model.addColumn("Date");
		model.addColumn("Diagnosis");
		model.addColumn("Medicine(s)");
		model.addColumn("Lab test(s)");	
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		tfName = new JTextField();
		tfName.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfName.setEditable(false);
		tfName.setColumns(10);
		
		tfID = new JTextField();
		tfID.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfID.setEditable(false);
		tfID.setColumns(10);
		
		tfAge = new JTextField();
		tfAge.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfAge.setEditable(false);
		tfAge.setColumns(10);
		
		JLabel lblTepreture = new JLabel("Tepreture");
		lblTepreture.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("Heart rate");
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		tfTempreture = new JTextField();
		tfTempreture.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfTempreture.setEditable(false);
		tfTempreture.setColumns(10);
		
		tfHeartRate = new JTextField();
		tfHeartRate.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfHeartRate.setEditable(false);
		tfHeartRate.setColumns(10);
		
		JLabel lblAllergies = new JLabel("Has Allergie(s) to Medicine :");
		lblAllergies.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
	
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName)
								.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addGap(37)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(tfID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblAge, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(tfAge)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblTepreture))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(tfTempreture, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfHeartRate, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addComponent(lblAllergies)
							.addGap(80))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(53)
							.addComponent(taAllergies, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblName)
									.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblAllergies))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(tfID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblId))
									.addGap(7)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblAge)
										.addComponent(tfAge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(23)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(tfHeartRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(taAllergies, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblTepreture)
							.addComponent(tfTempreture, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(198,226,237));
		tabbedPane.addTab("change password", null, panel_1, null);
		
		JLabel label = new JLabel("old password");
		label.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_1 = new JLabel("new password");
		label_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_2 = new JLabel("confirm new password");
		label_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		Old = new RoundJPasswordField(50);
		Old.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		New = new RoundJPasswordField(50);
		New.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		ConfNew = new RoundJPasswordField(50);
		ConfNew.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		
		JButton btnCancle = new JButton("cancle");
		btnCancle.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		btnSave.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
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
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Doctor.class.getResource("/images/dp.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(163)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCancle)
						.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(37)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(158)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(New, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
								.addComponent(Old, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(405, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(57)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Old, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_1)
							.addGap(14)
							.addComponent(New, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_2)
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(59)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnCancle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(256)
							.addComponent(ConfNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(54, Short.MAX_VALUE))
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