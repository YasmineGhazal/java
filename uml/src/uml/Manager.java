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
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

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
	private JSpinner spinnerTo;
	private JScrollPane scrollPane_1;
	private JTable tableWaiting;
	public Manager() {
		this.setSize(675,350);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);
		this.setTitle("Manager");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel AddDoctor = new JPanel();
		tabbedPane.addTab("Add new Doctor", null, AddDoctor, null);
		
		JLabel lblGenderD = new JLabel("gender");
		
		JLabel lblPasswordD = new JLabel("password");
		
		JLabel lblNameD = new JLabel("Name");
		
		JLabel lblPhoneD = new JLabel("phone");
		
		passwordD = new JPasswordField();
		passwordD.setColumns(10);
		passwordD.setBorder(grayBorder);
		
		JLabel lblConfD = new JLabel("confirm password");
		
		passwordConfirmD = new JPasswordField();
		passwordConfirmD.setColumns(10);
		passwordConfirmD.setBorder(grayBorder);
		
		
		tfAddressD = new JTextField();
		tfAddressD.setColumns(10);
		tfAddressD.setBorder(grayBorder);
		
		tfPhoneD = new JTextField();
		tfPhoneD.setColumns(10);
		tfPhoneD.setBorder(grayBorder);
		
		tfNameD = new JTextField();
		tfNameD.setColumns(10);
		tfNameD.setBorder(grayBorder);
		
		JButton btnClearAllD = new JButton("CLEAR ALL");
		btnClearAllD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAdd(1);
			}
		});
		
		JButton btnAddD = new JButton("ADD");
		btnAddD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkData(1))
			        insertToTable(1);
				
			}
		});
		
		JLabel lblAddressD = new JLabel("Address");
		
		rgD=new ButtonGroup();
		rgD.add(rbFemaleD);
		rgD.add(rbMaleD);
		

		
		
		GroupLayout gl_AddDoctor = new GroupLayout(AddDoctor);
		gl_AddDoctor.setHorizontalGroup(
			gl_AddDoctor.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_AddDoctor.createSequentialGroup()
					.addContainerGap(97, Short.MAX_VALUE)
					.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addComponent(lblNameD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(tfNameD, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addComponent(lblPhoneD, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(tfPhoneD, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addComponent(lblPasswordD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordD, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(lblConfD, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordConfirmD, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addComponent(lblGenderD, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rbMaleD, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbFemaleD, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addComponent(lblAddressD, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
									.addGap(64)
									.addComponent(tfAddressD, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
							.addGap(45)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnAddD, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnClearAllD, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))))
					.addGap(71))
		);
		gl_AddDoctor.setVerticalGroup(
			gl_AddDoctor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddDoctor.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_AddDoctor.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addGap(1)
									.addComponent(lblNameD))
								.addComponent(tfNameD, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPhoneD)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addGap(4)
									.addComponent(tfPhoneD, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAddressD)
								.addComponent(tfAddressD, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_AddDoctor.createSequentialGroup()
									.addGap(4)
									.addComponent(lblGenderD))
								.addComponent(rbMaleD)
								.addComponent(rbFemaleD))
							.addGap(18))
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addComponent(btnAddD)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClearAllD)
							.addGap(30)))
					.addGroup(gl_AddDoctor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGap(1)
							.addComponent(lblPasswordD))
						.addComponent(passwordD, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_AddDoctor.createSequentialGroup()
							.addGap(1)
							.addComponent(lblConfD))
						.addComponent(passwordConfirmD, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(117, Short.MAX_VALUE))
		);
		AddDoctor.setLayout(gl_AddDoctor);
		
		JPanel AddNurse = new JPanel();
		tabbedPane.addTab("Add new Nurs", null, AddNurse, null);
		
		JLabel lblGenderN = new JLabel("gender");
		

		rgN=new ButtonGroup();
		rgN.add(rbFemaleN);
		rgN.add(rbMaleN);
		
		JLabel lblNameN = new JLabel("Name");
		
		tfNameN = new JTextField();
		tfNameN.setColumns(10);
		tfNameN.setBorder(grayBorder);
		
		JLabel lblPhoneN = new JLabel("phone");
		
		tfPhoneN = new JTextField();
		tfPhoneN.setColumns(10);
		tfPhoneN.setBorder(grayBorder);
		
		JLabel lblAddressN = new JLabel("Address");
		
		tfAddressN = new JTextField();
		tfAddressN.setColumns(10);
		tfAddressN.setBorder(grayBorder);
		
		JLabel lblPasswordN = new JLabel("password");
		
		passwordN = new JPasswordField();
		passwordN.setColumns(10);
		passwordN.setBorder(grayBorder);
		
		JLabel lblConfN = new JLabel("confirm password");
		
		passwordConfirmN = new JPasswordField();
		passwordConfirmN.setColumns(10);
		passwordConfirmN.setBorder(grayBorder);
		
		JButton btnAddN = new JButton("ADD");
		btnAddN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkData(2))
			        insertToTable(2);

			}
		});
		
		JButton btnClearAllN = new JButton("CLEAR ALL");
		btnClearAllN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAdd(2);
			}
		});
		GroupLayout gl_AddNurse = new GroupLayout(AddNurse);
		gl_AddNurse.setHorizontalGroup(
			gl_AddNurse.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddNurse.createSequentialGroup()
					.addGap(80)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(lblNameN, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(tfNameN, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(lblPhoneN, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(tfPhoneN, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(lblAddressN, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addGap(64)
							.addComponent(tfAddressN, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(68)
							.addComponent(btnAddN, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(lblGenderN, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rbMaleN, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addComponent(rbFemaleN, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addGap(45)
							.addComponent(btnClearAllN, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addComponent(lblPasswordN, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordN, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(lblConfN, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordConfirmN, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(88, Short.MAX_VALUE))
		);
		gl_AddNurse.setVerticalGroup(
			gl_AddNurse.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_AddNurse.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNameN))
						.addComponent(tfNameN, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPhoneN)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(4)
							.addComponent(tfPhoneN, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
					.addGap(8)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(10)
							.addComponent(lblAddressN))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(10)
							.addComponent(tfAddressN, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAddN))
					.addGap(4)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(18)
							.addComponent(lblGenderN))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(14)
							.addComponent(rbMaleN))
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(14)
							.addComponent(rbFemaleN))
						.addComponent(btnClearAllN))
					.addGap(18)
					.addGroup(gl_AddNurse.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(1)
							.addComponent(lblPasswordN))
						.addComponent(passwordN, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_AddNurse.createSequentialGroup()
							.addGap(1)
							.addComponent(lblConfN))
						.addComponent(passwordConfirmN, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(118, Short.MAX_VALUE))
		);
		AddNurse.setLayout(gl_AddNurse);
		
		JPanel PatientsVisitedDoctors = new JPanel();
		tabbedPane.addTab("Patients visited a doctor", null, PatientsVisitedDoctors, null);
				
		JLabel NoOfPatients = new JLabel("Number of patients");
		NoOfPatients.setFont(new Font("Graph", Font.BOLD, 9));
		
		JLabel tfNoP = new JLabel();
		tfNoP.setHorizontalAlignment(SwingConstants.CENTER);
		tfNoP.setFont(new Font("Dialog", Font.BOLD, 27));
		
		JLabel lblNoOfP = new JLabel("patients have visited  Dr. ");
		
		 comboBoxDoctorName = new JComboBox<String>();
		
		JLabel lblFrom = new JLabel("from");
		
		spinnerFrom = new JSpinner();
		
		JLabel lblTo = new JLabel("to");
		
		spinnerTo = new JSpinner();
		
		JButton btnSearch = new JButton("Search");
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
			        ResultSet rs=stmt.executeQuery("SELECT PatientID, patient.Name, Date, enter FROM Visit,Patient,doctor where PatientID=patient.ID and DoctorID=Doctor.ID and doctor.name='"+
			        		DoctorName+ "' and Date between '"+fromDate+"' and '"+ToDate+"';");
			        int NoPatient=0;
			        while(rs.next()) {
			 			model.addRow(new String[]{rs.getString("PatientID"),rs.getString("Name"),rs.getString("Date"),rs.getString("enter")});
			 			NoPatient++;
			        }
			    	tfNoP.setText(NoPatient+"");				
					
		    	 }catch(Exception e1) {
		    		 JOptionPane.showMessageDialog(null,"there is not any doctors yet");
			    	 }
			
				
			}
		});
		
		scrollPane_1 = new JScrollPane();
		spinnerFrom = new JSpinner();
		spinnerFrom.setModel(new SpinnerDateModel());
		spinnerFrom.setEditor(new JSpinner.DateEditor(spinnerFrom, formater.toPattern()));
		
		spinnerTo = new JSpinner();
		spinnerTo.setModel(new SpinnerDateModel());
		spinnerTo.setEditor(new JSpinner.DateEditor(spinnerTo, formater.toPattern()));
		
		
		GroupLayout gl_PatientsVisitedDoctors = new GroupLayout(PatientsVisitedDoctors);
		gl_PatientsVisitedDoctors.setHorizontalGroup(
			gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
							.addComponent(lblNoOfP, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(comboBoxDoctorName, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFrom, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerFrom, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerTo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 552, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
									.addComponent(NoOfPatients, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
								.addComponent(tfNoP, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_PatientsVisitedDoctors.setVerticalGroup(
			gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNoOfP)
							.addComponent(comboBoxDoctorName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFrom)
								.addComponent(spinnerFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTo)
								.addComponent(spinnerTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGroup(gl_PatientsVisitedDoctors.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
							.addGap(34)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(NoOfPatients, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNoP, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
						.addGroup(gl_PatientsVisitedDoctors.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		PatientsVisitedDoctors.setLayout(gl_PatientsVisitedDoctors);
		
		JPanel PatientWaitngTime = new JPanel();
		tabbedPane.addTab("Patient's waiting time", null, PatientWaitngTime, null);
		
		JSpinner spinnerWaiting = new JSpinner();
		spinnerWaiting.setModel(new SpinnerDateModel());
		spinnerWaiting.setEditor(new JSpinner.DateEditor(spinnerWaiting, formater.toPattern()));
		
		JLabel lblwaiting = new JLabel("Date :");
		
		JButton btnFind = new JButton("Find out");
		JLabel lblAvg = new JLabel("00");

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
			        ResultSet rs=stmt.executeQuery("SELECT PatientID, Name, enter ,arrival  FROM Visit,Patient where PatientID=ID and Date = '"+waitingDate+"';");
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
			 			modelWaiting.addRow(new String[]{rs.getString("PatientID"),rs.getString("Name"),diffMinutes+""});
			        }
			      }catch(Exception e1){     }
				if(count>0)
					lblAvg.setText(sum/count+"");
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblAverage = new JLabel("Average in minutes : ");
		
		lblAvg.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAvg.setForeground(UIManager.getColor("OptionPane.errorDialog.border.background"));
		GroupLayout gl_PatientWaitngTime = new GroupLayout(PatientWaitngTime);
		gl_PatientWaitngTime.setHorizontalGroup(
			gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PatientWaitngTime.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addComponent(lblwaiting)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spinnerWaiting, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnFind)
							.addGap(69)
							.addComponent(lblAverage)
							.addGap(18)
							.addComponent(lblAvg)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_PatientWaitngTime.setVerticalGroup(
			gl_PatientWaitngTime.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PatientWaitngTime.createSequentialGroup()
					.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.BASELINE)
								.addComponent(spinnerWaiting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblwaiting)
								.addComponent(btnFind))
							.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
						.addGroup(gl_PatientWaitngTime.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_PatientWaitngTime.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAverage)
								.addComponent(lblAvg))
							.addGap(18)))
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tableWaiting = new JTable(modelWaiting);
		scrollPane.setViewportView(tableWaiting);
		PatientWaitngTime.setLayout(gl_PatientWaitngTime);
		modelWaiting.addColumn("ID");
		modelWaiting.addColumn("Name");
		modelWaiting.addColumn("Waiting time (in minutes)");
		
	    scrollPane_1.setViewportView(doctor_patient_info);
		doctor_patient_info = new JTable(model);
		scrollPane_1.setViewportView(doctor_patient_info);
    	try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:DB.db");
	        stmt = c.createStatement();
	        ResultSet rs=stmt.executeQuery("SELECT Name, ID FROM doctor;");
	        while(rs.next()) {
	        	comboBoxDoctorName.addItem(rs.getString("Name"));
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
	 			errorMsg+="Please enter password \n";
	 		}
	 		else if(!pass1.matches("[a-zA-Z_0-9]{6}")) {
	 			passwordD.setBorder(errorborder);
	 			errorMsg+="password must be contains 6 letters or numbers \n";
	 		}
	 		if(pass2.equals("")) {
	 			passwordConfirmD.setBorder(errorborder);
	 			errorMsg+="Please enter confirm passsword \n";
	 		}
	 		if(!pass1.equals(pass2)) {
	 			passwordConfirmD.setBorder(errorborder);
	 			errorMsg+="passwords are not the same \n";
	 		}
	 		if(name.equals("")) {
	 			tfNameD.setBorder(errorborder);
	 			errorMsg+="Please enter Name \n";
	 		}
	 		if(address.trim().equals("")) {
	 			tfAddressD.setBorder(errorborder);
	 			errorMsg+="Please enter Address \n";
	 		}
	 		if(phone.equals("")) {
	 			tfPhoneD.setBorder(errorborder);
	 			errorMsg+="Please enter Phone \n";
	 		}
	 		else if(!phone.matches("[0-9]{10}")) {
	 			tfPhoneD.setBorder(errorborder);
	 			errorMsg+="Phone number must contain 10 digits \n";
	 		}
	 		if(!rbMaleD.isSelected() && !rbFemaleD.isSelected()) {
	 			errorMsg+="Please choose gender \n";
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
 	 			errorMsg+="Please enter password \n";
 	 		}
 	 		else if(!pass1.matches("[a-zA-Z_0-9]{5,}")) {
 	 			passwordN.setBorder(errorborder);
 	 			errorMsg+="password must be contains 6 letters or numbers \n";
 	 		}
 	 		if(pass2.equals("")) {
 	 			passwordConfirmN.setBorder(errorborder);
 	 			errorMsg+="Please enter confirm passsword \n";
 	 		}
 	 		if(!pass1.equals(pass2)) {
 	 			passwordConfirmN.setBorder(errorborder);
 	 			errorMsg+="passwords are not the same \n";
 	 		}
 	 		if(name.equals("")) {
 	 			tfNameN.setBorder(errorborder);
 	 			errorMsg+="Please enter Name \n";
 	 		}
 	 		if(address.trim().equals("")) {
 	 			tfAddressN.setBorder(errorborder);
 	 			errorMsg+="Please enter Address \n";
 	 		}
 	 		if(phone.equals("")) {
 	 			tfPhoneN.setBorder(errorborder);
 	 			errorMsg+="Please enter Phone \n";
 	 		}
 	 		else if(!phone.matches("[0-9]{10}")) {
 	 			tfPhoneN.setBorder(errorborder);
 	 			errorMsg+="Phone number must contain 10 digits \n";
 	 		}
 	 		if(!rbMaleN.isSelected() && !rbFemaleN.isSelected()) {
 	 			errorMsg+="Please choose gender \n";
 	 		}
        }
        if(!errorMsg.equals("")) {
        	JOptionPane.showMessageDialog(null, errorMsg, "Failure", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
		return true;
	}
	void clearAdd(int t) {
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

	void grayAll(int t) {
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
	void insertToTable(int t) {
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
            	 rs = stmt.executeQuery("SELECT * FROM doctor where ID = '"+ID+"';");
				 while(rs.next()) {
					 randomNum =  rn.nextInt(99988) + 10;
					 ID = 1+""+randomNum;
	            	 rs = stmt.executeQuery("SELECT * FROM doctor where ID = '"+ID+"';");
				 }
            	 String password=passwordD.getText().trim();
                 String name=tfNameD.getText().trim();
                 String address=tfAddressD.getText().trim();
                 String phone=tfPhoneD.getText().trim();
                 stmt.executeUpdate("insert into doctor values( '"
     		             +ID+"', '"+password+"' , '"+gender+"' , '"
     		             +phone+"' ,' "+address+"' ,'"+name+"');"); 
                 c.close();
                 JOptionPane.showMessageDialog(null, "Successfully added \n ID : "+ID+"\n password : "+password);
         		 clearAdd(t);
                   }else {

                   	if(rbMaleN.isSelected())
                       	gender="Male";
                   	else
                       	gender="Female";
                   	 String ID = 2+""+randomNum;
       				 ResultSet rs=null;
                   	 rs = stmt.executeQuery("SELECT * FROM nurse where ID = '"+ID+"';");
       				 while(rs.next()) {
       					 randomNum =  rn.nextInt(99988) + 10;
       					 ID = 2+""+randomNum;
       	            	 rs = stmt.executeQuery("SELECT * FROM nurse where ID = '"+ID+"';");
       				 }
                   	    String password=passwordN.getText().trim();
                        String name=tfNameN.getText().trim();
                        String address=tfAddressN.getText().trim();
                        String phone=tfPhoneN.getText().trim();
                        stmt.executeUpdate("insert into nurse values( '"
            		             +ID+"', '"+password+"' , '"+gender+"' , '"
            		             +phone+"' ,' "+address+"' ,'"+name+"');"); 
                        c.close();
                        JOptionPane.showMessageDialog(null, "Successfully added \n ID : "+ID+"\n password : "+password);
                		 clearAdd(t);
                   }
            	
            } catch ( Exception e1 ) {}         	
	}
}
