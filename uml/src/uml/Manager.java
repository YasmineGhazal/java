package uml;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RootPaneContainer;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JSpinner.DateEditor;

public class Manager extends JFrame{
	private JPasswordField passwordD;
	private JPasswordField passwordConfirmD;
	private JTextField tfAddressD;
	private JTextField tfPhoneD;
	private JTextField tfNameD;
	private JTextField tfNameN;
	private JTextField tfPhoneN;
	private JTextField tfAddressN;
	private JPasswordField passwordN;
	private JPasswordField passwordConfirmN;
	private JComboBox<String> comboBoxDoctorName;
	private JRadioButton rbMaleN = new JRadioButton("Male");
	private JRadioButton rbFemaleN = new JRadioButton("Female");
	private JRadioButton rbMaleD = new JRadioButton("Male");
	private JRadioButton rbFemaleD = new JRadioButton("Female");
	
	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel modelWaiting = new DefaultTableModel();
    DefaultTableModel tm=new DefaultTableModel();

    Border grayBorder = BorderFactory.createLineBorder(Color.gray, 1);
    Connection c = null;
	java.sql.Statement stmt ;
	String DoctorID="" ;
	private ButtonGroup rgD;
	private ButtonGroup rgN;	
	private JTable doctor_patient_info;
	SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
	private JSpinner spinnerFrom;
	private JSpinner spinnerFrom_1;
	private JSpinner spinnerTo;
	private JSpinner spinnerTo_1;
	private JScrollPane scrollPane_1;
	private JTable tableWaiting;
	private JTable table;
	public Manager() {
		this.setSize(910,483);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);
		this.setTitle("Manager");
		ImageIcon img = new ImageIcon(auth.class.getResource("/images/HIS.png"));
		this.setIconImage(img.getImage());
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel AddDoctor = new JPanel();
		AddDoctor.setBackground(new Color(167, 200, 183));
		tabbedPane.addTab("Add new doctor", null, AddDoctor, null);
		
		JLabel lblGenderD = new JLabel("Gender");
		lblGenderD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblPasswordD = new JLabel("Password");
		lblPasswordD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblNameD = new JLabel("Name");
		lblNameD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblPhoneD = new JLabel("Phone");
		lblPhoneD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		passwordD = new  RoundJPasswordField(50);
		passwordD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		passwordD.setColumns(10);
		passwordD.setBorder(grayBorder);
		
		JLabel lblConfD = new JLabel("Confirm password");
		lblConfD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		passwordConfirmD = new  RoundJPasswordField(50);
		passwordConfirmD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		passwordConfirmD.setColumns(10);
		passwordConfirmD.setBorder(grayBorder);
		
		
		tfAddressD = new RoundJTextField(50);
		tfAddressD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfAddressD.setColumns(10);
		tfAddressD.setBorder(grayBorder);
		
		tfPhoneD = new RoundJTextField(50);
		tfPhoneD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfPhoneD.setColumns(10);
		tfPhoneD.setBorder(grayBorder);
		
		tfNameD = new RoundJTextField(50);
		tfNameD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfNameD.setColumns(10);
		tfNameD.setBorder(grayBorder);
		
		JButton btnClearAllD = new JButton("CANCLE");
		btnClearAllD.setBackground(new Color(125, 149, 136));
		btnClearAllD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnClearAllD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAdd(1);
			}
		});
		
		JButton btnAddD = new JButton("ADD");
		btnAddD.setBackground(new Color(125, 149, 136));
		btnAddD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnAddD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkData(1))
			        insertToTable(1);
				
			}
		});
		
		JLabel lblAddressD = new JLabel("Address");
		lblAddressD.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		rgD=new ButtonGroup();
		rgD.add(rbFemaleD);
		rgD.add(rbMaleD);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(153, 255, 153));
		lblNewLabel.setIcon(new ImageIcon(Manager.class.getResource("/images/D1.jpg")));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Manager.class.getResource("/images/D2.jpg")));
		

		
		
		GroupLayout gl_AddDoctor = new GroupLayout(AddDoctor);
		gl_AddDoctor.setHorizontalGroup(
			gl_AddDoctor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddDoctor.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_AddDoctor.createSequentialGroup()
											.addComponent(lblNameD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
											.addGap(22)
											.addComponent(tfNameD, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_AddDoctor.createSequentialGroup()
											.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAddressD, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblPhoneD, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
											.addGap(36)
											.addGroup(gl_AddDoctor.createParallelGroup(Alignment.TRAILING)
												.addComponent(tfAddressD, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
												.addComponent(tfPhoneD, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))
											.addGap(18))
										.addGroup(gl_AddDoctor.createSequentialGroup()
											.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
												.addComponent(lblGenderD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblPasswordD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
											.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
												.addComponent(passwordD, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_AddDoctor.createSequentialGroup()
													.addComponent(rbMaleD, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
													.addGap(31)
													.addComponent(rbFemaleD, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))))
									.addGap(18))
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addComponent(lblConfD, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(passwordConfirmD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(Alignment.TRAILING, gl_AddDoctor.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnClearAllD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
								.addComponent(btnAddD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addGap(1620))
		);
		gl_AddDoctor.setVerticalGroup(
			gl_AddDoctor.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_AddDoctor.createSequentialGroup()
					.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGap(15)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNameD)
										.addComponent(tfNameD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.BASELINE)
										.addComponent(tfPhoneD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPhoneD))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
										.addComponent(lblAddressD)
										.addComponent(tfAddressD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.BASELINE)
										.addComponent(rbFemaleD)
										.addComponent(rbMaleD)
										.addComponent(lblGenderD))
									.addGap(16)
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.BASELINE)
										.addComponent(passwordD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPasswordD))
									.addGap(18)
									.addGroup(gl_AddDoctor.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblConfD)
										.addComponent(passwordConfirmD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addComponent(btnAddD)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClearAllD)))))
					.addGap(32))
		);
		rbFemaleD.setBackground(new Color(167, 200, 183));
		rbMaleD.setBackground(new Color(167, 200, 183));
		rbFemaleD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		rbMaleD.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		AddDoctor.setLayout(gl_AddDoctor);
		
		JPanel AddNurse = new JPanel();
		AddNurse.setBackground(new Color(200, 235, 231));
		tabbedPane.addTab("Add new nurse", null, AddNurse, null);
		

		rgN=new ButtonGroup();
		rgN.add(rbFemaleN);
		rgN.add(rbMaleN);
		
		tfNameN = new JTextField();
		tfNameN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfNameN.setColumns(10);
		tfNameN.setBorder(grayBorder);
		
		tfPhoneN = new JTextField();
		tfPhoneN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfPhoneN.setColumns(10);
		tfPhoneN.setBorder(grayBorder);
		
		tfAddressN = new JTextField();
		tfAddressN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		tfAddressN.setColumns(10);
		tfAddressN.setBorder(grayBorder);
		
		passwordN = new JPasswordField();
		passwordN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		passwordN.setColumns(10);
		passwordN.setBorder(grayBorder);
		
		passwordConfirmN = new JPasswordField();
		passwordConfirmN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		passwordConfirmN.setColumns(10);
		passwordConfirmN.setBorder(grayBorder);
		
		JButton btnAddN = new JButton("ADD");
		btnAddN.setBackground(new Color(70, 115, 154));
		btnAddN.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnAddN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkData(2))
			        insertToTable(2);

			}
		});
		
		JButton btnClearAllN = new JButton("CANCLE");
		btnClearAllN.setBackground(new Color(226, 108, 79));
		btnClearAllN.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnClearAllN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAdd(2);
			}
		});
		
		JLabel label_1 = new JLabel("Name");
		label_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_2 = new JLabel("Phone");
		label_2.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_3 = new JLabel("Address");
		label_3.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_4 = new JLabel("Gender");
		label_4.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_5 = new JLabel("Password");
		label_5.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel label_6 = new JLabel("Confirm password");
		label_6.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Manager.class.getResource("/images/N1.jpg")));
		
		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon(Manager.class.getResource("/images/N2.jpg")));
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Manager.class.getResource("/images/N4.jpg")));
		GroupLayout gl_AddNurse = new GroupLayout(AddNurse);
		gl_AddNurse.setHorizontalGroup(
			gl_AddNurse.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddNurse.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
							.addGap(27))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(btnAddN, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(31)))
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnClearAllN, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbMaleN, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNameN)
						.addComponent(tfPhoneN)
						.addComponent(tfAddressN)
						.addComponent(passwordConfirmN)
						.addComponent(passwordN)
						.addComponent(rbFemaleN, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
		);
		gl_AddNurse.setVerticalGroup(
			gl_AddNurse.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddNurse.createSequentialGroup()
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(20)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 436, Short.MAX_VALUE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfNameN, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfPhoneN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfAddressN, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_AddNurse.createSequentialGroup()
									.addGap(18)
									.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_AddNurse.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rbMaleN)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rbFemaleN)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordConfirmN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_AddNurse.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnClearAllN, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddN, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 117, Short.MAX_VALUE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addContainerGap(174, Short.MAX_VALUE)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		rbMaleN.setBackground(new Color(200, 235, 231));
		rbFemaleN.setBackground(new Color(200, 235, 231));
		rbMaleN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		rbFemaleN.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		AddNurse.setLayout(gl_AddNurse);
		
		spinnerFrom = new JSpinner(new SpinnerDateModel());
		
		spinnerTo = new JSpinner(new SpinnerDateModel());
		
		JPanel PatientsVisitedDoctors = new JPanel();
		PatientsVisitedDoctors.setBackground(new Color(0,182,214));
		tabbedPane.addTab("Patients visited a doctor", null, PatientsVisitedDoctors, null);
		
		JLabel NoOfPatients = new JLabel("Number of patients");
		NoOfPatients.setHorizontalAlignment(SwingConstants.CENTER);
		NoOfPatients.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		
		JLabel tfNoP = new JLabel();
		tfNoP.setHorizontalAlignment(SwingConstants.CENTER);
		tfNoP.setFont(new Font("Dialog", Font.BOLD, 25));
		
		 
		 JLabel tfNoV = new JLabel();
		 tfNoV.setHorizontalAlignment(SwingConstants.CENTER);
		 tfNoV.setFont(new Font("Dialog", Font.BOLD, 25));
		 
		JLabel lblNoOfP = new JLabel("patients have visited  Dr. ");
		lblNoOfP.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		 comboBoxDoctorName = new JComboBox<String>();
		 comboBoxDoctorName.setBackground(Color.WHITE);
		 comboBoxDoctorName.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 
		 JLabel lblFrom = new JLabel("from");
		 lblFrom.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 
		 JLabel lblTo = new JLabel("to");
		 lblTo.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 
		 JButton btnSearch = new JButton("Search");
		 btnSearch.setBackground(Color.WHITE);
		 btnSearch.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 btnSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		         if (model.getRowCount() > 0) {
		             for (int i = model.getRowCount() - 1; i > -1; i--) {
		             	model.removeRow(i);
		             }}

		 		try {
		 			
		 			String fromDate=formater.format(spinnerFrom.getValue());
		 			String ToDate=formater.format(spinnerTo.getValue());
		 			Class.forName("org.sqlite.JDBC");
		 	        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
		 	        stmt = c.createStatement();
		 	        String DoctorName=comboBoxDoctorName.getSelectedItem().toString();
		 	        ResultSet rs=stmt.executeQuery("SELECT patientID, Patient.name, date, enter FROM Visit,Patient,Doctor where patientID=Patient.ID and doctorID=Doctor.ID and Doctor.name='"+
		 	        		DoctorName.substring(0,DoctorName.indexOf(" ,"))+"' and date between '"+fromDate+"' and '"+ToDate+ "';");      
		 	        ArrayList<String> patients=new ArrayList<>();
		 	       int vs=0;
		 	        while(rs.next()) {
		 	        	String name=rs.getString("name");
		 	 			model.addRow(new String[]{rs.getString("patientID"),name,rs.getString("date"),rs.getString("enter")});
		 	 			if(!patients.contains(name))
		 	 				patients.add(name);
		 	 			vs++;
		 	        }
		 	    	tfNoP.setText(patients.size()+"");	
		 	    	tfNoV.setText(vs+"");
		 	    	tm.addRow(new String[]{DoctorName.substring(0,DoctorName.indexOf(" ,")),vs+""});
		 			c.close();
		     	 }catch(Exception e1) {
		     	 }
		 	
		 		
		 	}
		 });
		 
		 scrollPane_1 = new JScrollPane();
		 spinnerFrom_1 = new JSpinner();
		 spinnerFrom_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 spinnerFrom_1.setModel(new SpinnerDateModel());
		 DateEditor de_spinnerFrom_1 = new JSpinner.DateEditor(spinnerFrom_1, formater.toPattern());
		 de_spinnerFrom_1.setBackground(Color.WHITE);
		 spinnerFrom_1.setEditor(de_spinnerFrom_1);
		 
		 spinnerTo_1 = new JSpinner();
		 spinnerTo_1.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		 spinnerTo_1.setModel(new SpinnerDateModel());
		 DateEditor de_spinnerTo_1 = new JSpinner.DateEditor(spinnerTo_1, formater.toPattern());
		 de_spinnerTo_1.setBackground(Color.WHITE);
		 spinnerTo_1.setEditor(de_spinnerTo_1);
		 
		 JLabel label_8 = new JLabel("");
		 label_8.setIcon(new ImageIcon(Manager.class.getResource("/images/PD.png")));
		 
		 JLabel lblNumberOfVisits = new JLabel("Number of visits");
		 lblNumberOfVisits.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNumberOfVisits.setFont(new Font("Dialog", Font.BOLD, 18));
		 
		 JScrollPane scrollPane_2 = new JScrollPane();

		 
		 GroupLayout gl_PatientsVisitedDoctors = new GroupLayout(PatientsVisitedDoctors);
		 gl_PatientsVisitedDoctors.setHorizontalGroup(
		 	gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 			.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 					.addContainerGap()
		 					.addComponent(lblNoOfP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		 					.addPreferredGap(ComponentPlacement.RELATED)
		 					.addComponent(comboBoxDoctorName, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
		 					.addGap(29)
		 					.addComponent(lblFrom, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
		 					.addPreferredGap(ComponentPlacement.RELATED)
		 					.addComponent(spinnerFrom_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		 					.addGap(18)
		 					.addComponent(lblTo)
		 					.addPreferredGap(ComponentPlacement.RELATED)
		 					.addComponent(spinnerTo_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
		 					.addGap(116))
		 				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING, false)
		 						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 							.addGap(20)
		 							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE)
		 							.addPreferredGap(ComponentPlacement.UNRELATED))
		 						.addGroup(Alignment.TRAILING, gl_PatientsVisitedDoctors.createSequentialGroup()
		 							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		 							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
		 							.addGap(107)))
		 					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 						.addComponent(label_8)
		 						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
		 						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 							.addGap(6)
		 							.addComponent(NoOfPatients)
		 							.addPreferredGap(ComponentPlacement.RELATED)
		 							.addComponent(tfNoP, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		 						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 							.addComponent(lblNumberOfVisits, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
		 							.addGap(18)
		 							.addComponent(tfNoV, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
		 					.addGap(22)))
		 			.addContainerGap())
		 );
		 gl_PatientsVisitedDoctors.setVerticalGroup(
		 	gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 			.addGap(24)
		 			.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.BASELINE)
		 				.addComponent(lblNoOfP)
		 				.addComponent(comboBoxDoctorName, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
		 				.addComponent(lblFrom)
		 				.addComponent(spinnerFrom_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
		 				.addComponent(lblTo)
		 				.addComponent(spinnerTo_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		 			.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 					.addGap(18)
		 					.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
		 					.addGap(8)
		 					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 						.addComponent(NoOfPatients, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
		 						.addComponent(tfNoP, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
		 					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
		 					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
		 						.addComponent(lblNumberOfVisits, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
		 						.addComponent(tfNoV, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
		 					.addPreferredGap(ComponentPlacement.RELATED)
		 					.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
		 				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
		 					.addGap(11)
		 					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
		 					.addPreferredGap(ComponentPlacement.UNRELATED)
		 					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
		 			.addContainerGap())
		 );
		 table = new JTable(tm);
		 scrollPane_2.setViewportView(table);
		 tm.addColumn("Doctor Name");
		 tm.addColumn("Number of visits");
		 
		 PatientsVisitedDoctors.setLayout(gl_PatientsVisitedDoctors);
		 
	    scrollPane_1.setViewportView(doctor_patient_info);
	    doctor_patient_info = new JTable(model);
	    scrollPane_1.setViewportView(doctor_patient_info);
		
		JPanel PatientWaitngTime = new JPanel();
		PatientWaitngTime.setBackground(new Color(42, 80,128));
		tabbedPane.addTab("Patient's waiting time", null, PatientWaitngTime, null);
		
		JSpinner spinnerWaiting = new JSpinner();
		spinnerWaiting.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		spinnerWaiting.setModel(new SpinnerDateModel());
		spinnerWaiting.setEditor(new JSpinner.DateEditor(spinnerWaiting, formater.toPattern()));
		
		JLabel lblwaiting = new JLabel("Date :");
		lblwaiting.setForeground(Color.WHITE);
		lblwaiting.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JButton btnFind = new JButton("For all pateints");
		btnFind.setBackground(new Color(149,168,192));
		btnFind.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		JLabel lblAvg = new JLabel("00");
		lblAvg.setHorizontalAlignment(SwingConstants.CENTER);

		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String waitingDate=formater.format(spinnerWaiting.getValue());
		        int count=0;
		        long sum=0;
		        if (modelWaiting.getRowCount() > 0) {
		            for (int i = modelWaiting.getRowCount() - 1; i > -1; i--) {
		            	modelWaiting.removeRow(i);
		            }
		        }
				try {
					Class.forName("org.sqlite.JDBC");
			        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
			        stmt = c.createStatement();
			        DoctorID=comboBoxDoctorName.getSelectedItem().toString();
			        ResultSet rs=stmt.executeQuery("SELECT patientID, name, enter ,arrival  FROM Visit,Patient where patientID=ID and date = '"+waitingDate+"';");
			        while(rs.next()) {
			        	count++;
			        	String e=rs.getString("enter");
			        	String a=rs.getString("arrival");
			        	String[] asplit = a.split(":");
			        	int ahours = Integer.valueOf(asplit[0]);
			        	int aminutes = Integer.valueOf(asplit[1]);
			        	String[] esplit = e.split(":");
			        	int ehours = Integer.valueOf(esplit[0]);
			        	int eminutes = Integer.valueOf(esplit[1]);
			        	int diffMinutes=0;
			        	if(eminutes >= aminutes&&ehours>ahours) 
			        		diffMinutes = (eminutes-aminutes ) +60*(ehours-ahours)-60;
			        	else if(eminutes >= aminutes&&ehours==ahours) 
			        		diffMinutes = (eminutes-aminutes ) +60*(ehours-ahours);    	
			        	else if(eminutes > aminutes&&ehours<ahours) 
			        		diffMinutes = (eminutes-aminutes ) +ehours+24-ahours;
			        	else if(eminutes < aminutes&&ehours>ahours) 
			        		diffMinutes = (aminutes-eminutes )+60*(ehours-ahours);
			        	else
			        		diffMinutes = (aminutes+eminutes )+60*(ehours+ahours);
			            sum+=diffMinutes;
			 			modelWaiting.addRow(new String[]{rs.getString("patientID"),rs.getString("name"),diffMinutes+""});
			        }
			        c.close();
			      }catch(Exception e1){     }
				if(count>0)
					lblAvg.setText(sum/count+"");
				else 
					lblAvg.setText("00");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblAverage = new JLabel("Average \n in  minutes : ");
		lblAverage.setForeground(Color.WHITE);
		lblAverage.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		lblAverage.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblAvg.setFont(new Font("Dialog", Font.BOLD, 25));
		lblAvg.setForeground(Color.WHITE);
		
		JLabel label = new JLabel("Patient Name,ID :");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		
		JComboBox<String> comboBoxPName = new JComboBox<String>();
		comboBoxPName.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
	        stmt = c.createStatement();
	        ResultSet rs=stmt.executeQuery("SELECT name, ID FROM Patient;");
	        while(rs.next()) {
	        	comboBoxPName.addItem(rs.getString("name")+" ,ID: "+rs.getString("ID"));
	        }
    	 }catch(Exception e) {}
		JButton btnOneP = new JButton("For a specific patient");
		btnOneP.setBackground(new Color(149,168,192));
		btnOneP.setFont(new Font("Palatino Linotype", Font.PLAIN, 18));
		btnOneP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String waitingDate=formater.format(spinnerWaiting.getValue());
		        int count=0;
		        long sum=0;
		        if (modelWaiting.getRowCount() > 0) {
		            for (int i = modelWaiting.getRowCount() - 1; i > -1; i--) {
		            	modelWaiting.removeRow(i);
		            }
		        }
				try {
					String PN=(comboBoxPName.getSelectedItem()+"");
					PN=PN.substring(PN.indexOf(": ")+2);
					Class.forName("org.sqlite.JDBC");
			        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
			        stmt = c.createStatement();
			        DoctorID=comboBoxDoctorName.getSelectedItem().toString();
			        ResultSet rs=stmt.executeQuery("SELECT patientID, name, enter ,arrival  FROM Visit,Patient where patientID=ID and date = '"+waitingDate+"' and patientID='"+PN+"';");
			        while(rs.next()) {
			        	count++;
			        	String e=rs.getString("enter");
			        	String a=rs.getString("arrival");
			        	String[] asplit = a.split(":");
			        	int ahours = Integer.valueOf(asplit[0]);
			        	int aminutes = Integer.valueOf(asplit[1]);
			        	String[] esplit = e.split(":");
			        	int ehours = Integer.valueOf(esplit[0]);
			        	int eminutes = Integer.valueOf(esplit[1]);
			            int diffMinutes = (eminutes - aminutes) +60*(ehours-ahours);
			            sum+=diffMinutes;
			 			modelWaiting.addRow(new String[]{rs.getString("patientID"),rs.getString("name"),diffMinutes+""});
			        }
			        c.close();
			      }catch(Exception e1){     }
				if(count>0)
					lblAvg.setText(sum/count+"");
				else 
					lblAvg.setText("00");
			
				
			}
		});
		
		JLabel label_9 = new JLabel("");
		label_9.setIcon(new ImageIcon(Manager.class.getResource("/images/PW.png")));
		GroupLayout gl_PatientWaitngTime = new GroupLayout(PatientWaitngTime);
		gl_PatientWaitngTime.setHorizontalGroup(
			gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PatientWaitngTime.createSequentialGroup()
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(19)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblwaiting))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinnerWaiting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxPName, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnOneP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnFind, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))))
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(18)
							.addComponent(lblAverage, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAvg, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(27)
							.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)))
					.addGap(28))
		);
		gl_PatientWaitngTime.setVerticalGroup(
			gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PatientWaitngTime.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addComponent(spinnerWaiting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(comboBoxPName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_PatientWaitngTime.createSequentialGroup()
									.addComponent(btnFind)
									.addGap(2))
								.addGroup(gl_PatientWaitngTime.createSequentialGroup()
									.addComponent(lblwaiting)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(btnOneP)))
						.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblAverage, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblAvg, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)))
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addGap(58)
							.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		tableWaiting = new JTable(modelWaiting);
		scrollPane.setViewportView(tableWaiting);
		PatientWaitngTime.setLayout(gl_PatientWaitngTime);
		modelWaiting.addColumn("ID");
		modelWaiting.addColumn("Name");
		modelWaiting.addColumn("Waiting time (in minutes)");
    	try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
	        stmt = c.createStatement();
	        ResultSet rs=stmt.executeQuery("SELECT name, ID FROM Doctor;");
	        while(rs.next()) {
	        	comboBoxDoctorName.addItem(rs.getString("name")+" ,ID: "+rs.getString("ID"));
	        }
    	 }catch(Exception e) {}
    	 model.addColumn("ID");
    	 model.addColumn("Name");
 		 model.addColumn("Date of Visit");
 	 	 model.addColumn("Time of enter");
	}
	
	
	private boolean checkData(int t) {
        Border errorborder = BorderFactory.createLineBorder(Color.RED, 1);
        grayAll(t);        
        String pass1;
        String pass2;
        String name;
        String address;
        String phone;
        String errorMsg="";
        if(t==1) {
        	 pass1=passwordD.getText().trim();
	         pass2=passwordConfirmD.getText().trim();
	         name=tfNameD.getText().trim();
	         address=tfAddressD.getText().trim();
	         phone=tfPhoneD.getText().trim();
	         if(pass1.equals("")) {
	 			passwordD.setBorder(errorborder);
	 			errorMsg+=" * Please enter password \n";
	 		}
	 		else if(!pass1.matches("[a-zA-Z_0-9]{6}")) {
	 			passwordD.setBorder(errorborder);
	 			errorMsg+=" * Password must contain 6 letters or numbers \n";
	 		}
	 		if(pass2.equals("")) {
	 			passwordConfirmD.setBorder(errorborder);
	 			errorMsg+=" * Please enter confirm passsword \n";
	 		}
	 		if(!pass1.equals(pass2)) {
	 			passwordConfirmD.setBorder(errorborder);
	 			errorMsg+=" * Passwords are not the same \n";
	 		}
	 		if(name.equals("")) {
	 			tfNameD.setBorder(errorborder);
	 			errorMsg+=" * Please enter Name \n";
	 		}
	 		if(address.trim().equals("")) {
	 			tfAddressD.setBorder(errorborder);
	 			errorMsg+=" * Please enter Address \n";
	 		}
	 		if(phone.equals("")) {
	 			tfPhoneD.setBorder(errorborder);
	 			errorMsg+=" * Please enter Phone \n";
	 		}
	 		else if(!phone.matches("[0-9]{10}")) {
	 			tfPhoneD.setBorder(errorborder);
	 			errorMsg+=" * Phone number must contain 10 digits \n";
	 		}
	 		if(!rbMaleD.isSelected() && !rbFemaleD.isSelected()) {
	 			errorMsg+=" * Please choose gender \n";
	 		}
	         
         }
        else {
             pass1=passwordN.getText().trim();
             pass2=passwordConfirmN.getText().trim();
             name=tfNameN.getText().trim();
             address=tfAddressN.getText().trim();
             phone=tfPhoneN.getText().trim();
             if(pass1.equals("")) {
 	 			passwordN.setBorder(errorborder);
 	 			errorMsg+=" * Please enter password \n";
 	 		}
 	 		else if(!pass1.matches("[a-zA-Z_0-9]{5,}")) {
 	 			passwordN.setBorder(errorborder);
 	 			errorMsg+=" * Password must contain 6 letters or numbers \n";
 	 		}
 	 		if(pass2.equals("")) {
 	 			passwordConfirmN.setBorder(errorborder);
 	 			errorMsg+=" * Please enter confirm passsword \n";
 	 		}
 	 		if(!pass1.equals(pass2)) {
 	 			passwordConfirmN.setBorder(errorborder);
 	 			errorMsg+=" * Passwords are not the same \n";
 	 		}
 	 		if(name.equals("")) {
 	 			tfNameN.setBorder(errorborder);
 	 			errorMsg+=" * Please enter Name \n";
 	 		}
 	 		if(address.trim().equals("")) {
 	 			tfAddressN.setBorder(errorborder);
 	 			errorMsg+=" * Please enter Address \n";
 	 		}
 	 		if(phone.equals("")) {
 	 			tfPhoneN.setBorder(errorborder);
 	 			errorMsg+=" * Please enter Phone \n";
 	 		}
 	 		else if(!phone.matches("[0-9]{10}")) {
 	 			tfPhoneN.setBorder(errorborder);
 	 			errorMsg+=" * Phone number must contain 10 digits \n";
 	 		}
 	 		if(!rbMaleN.isSelected() && !rbFemaleN.isSelected()) {
 	 			errorMsg+=" * Please choose gender \n";
 	 		}
        }
        if(!errorMsg.equals("")) {
        	JOptionPane.showMessageDialog(null, errorMsg, "Failure", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
		return true;
	}
	private void clearAdd(int t) {
		if(t == 1) {
			passwordD.setText("");
			passwordConfirmD.setText("");
			tfNameD.setText("");
			tfAddressD.setText("");
			tfPhoneD.setText("");
			rgD.clearSelection();
		}else {
			passwordN.setText("");
			passwordConfirmN.setText("");
			tfNameN.setText("");
			tfAddressN.setText("");
			tfPhoneN.setText("");
			rgN.clearSelection();
		}
		grayAll(t);
	}

	private void grayAll(int t) {
		if(t == 1) {
			passwordD.setBorder(grayBorder);
			passwordConfirmD.setBorder(grayBorder);
			tfAddressD.setBorder(grayBorder);
			tfPhoneD.setBorder(grayBorder);
			tfNameD.setBorder(grayBorder);
		}else{
			passwordN.setBorder(grayBorder);
			passwordConfirmN.setBorder(grayBorder);
			tfAddressN.setBorder(grayBorder);
			tfPhoneN.setBorder(grayBorder);
			tfNameN.setBorder(grayBorder);
		}
		
	}
	private void insertToTable(int t) {
		try {
            Connection c = null;
            java.sql.Statement stmt ;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.db");
            stmt = c.createStatement();
            String gender;
            Random rn = new Random();
            int randomNum =  rn.nextInt(99988) + 10;
            if(t == 1) {
            	if(rbMaleD.isSelected())
                	gender="Male";
            	else
                	gender="Female";
            	 String ID = 1+""+randomNum;
				 ResultSet rs=null;
            	 rs = stmt.executeQuery("SELECT * FROM Doctor where ID = '"+ID+"';");
				 while(rs.next()) {
					 randomNum =  rn.nextInt(99988) + 10;
					 ID = 1+""+randomNum;
	            	 rs = stmt.executeQuery("SELECT * FROM Doctor where ID = '"+ID+"';");
				 }
            	 String password=passwordD.getText().trim();
                 String name=tfNameD.getText().trim();
                 String address=tfAddressD.getText().trim();
                 String phone=tfPhoneD.getText().trim();
                 stmt.executeUpdate("insert into doctor values( '"
     		             +ID+"', '"+password+"' , '"+gender+"' , '"
     		             +phone+"' ,' "+address+"' ,'"+name+"');"); 
                 c.close();
                 JOptionPane.showMessageDialog(null, "Successfully added \n ID : "+ID);
         		 clearAdd(t);
         		comboBoxDoctorName.addItem(name+", ID :"+ID);
                   }else {

                   	if(rbMaleN.isSelected())
                       	gender="Male";
                   	else
                       	gender="Female";
                   	 String ID = 2+""+randomNum;
       				 ResultSet rs=null;
                   	 rs = stmt.executeQuery("SELECT * FROM Nurse where ID = '"+ID+"';");
       				 while(rs.next()) {
       					 randomNum =  rn.nextInt(99988) + 10;
       					 ID = 2+""+randomNum;
       	            	 rs = stmt.executeQuery("SELECT * FROM Nurse where ID = '"+ID+"';");
       				 }
                   	    String password=passwordN.getText().trim();
                        String name=tfNameN.getText().trim();
                        String address=tfAddressN.getText().trim();
                        String phone=tfPhoneN.getText().trim();
                        stmt.executeUpdate("insert into nurse values( '"
            		             +ID+"', '"+password+"' , '"+gender+"' , '"
            		             +phone+"' ,' "+address+"' ,'"+name+"');"); 
                        c.close();
                        JOptionPane.showMessageDialog(null, "Successfully added \n ID : "+ID);
                		 clearAdd(t);
                   }
            	
            } catch ( Exception e1 ) {}         	
	}
}