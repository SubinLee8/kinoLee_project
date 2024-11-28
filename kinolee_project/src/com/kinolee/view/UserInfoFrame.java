package com.kinolee.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;


import com.kinolee.controller.UsersDao;
import com.kinolee.model.User;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class UserInfoFrame extends JFrame {
	
	public interface UpdateNotify{
		void notifyUpdateSuccess();
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parentComponent;
	private User user;
	private JButton btnDefault;
	private JButton btnUpdateUserInfo;
	private JTextArea textID;
	private JLabel lblUserEmail;
	private JTextArea textEmail;
	private JTextArea textLastName;
	private JTextArea textFirstName;
	private JPasswordField textPassword;
	private JLabel lblUserLastName;
	private JLabel lblUserFirstName;
	private JLabel lblUserPassword;
	private JLabel lblUserId;
	private UsersDao userDao=UsersDao.Instance;
	private UpdateNotify app;
	private JPasswordField textCheckPassword;
	private JLabel lblCheckPassword;
	private JLabel lblLogo;

	/**
	 * Launch the application.
	 */
	public static void showUserInfoFrame(Component parentComponent,User user, UpdateNotify app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInfoFrame frame = new UserInfoFrame(parentComponent,user,app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	UserInfoFrame(Component parentComponent,User user,UpdateNotify app){
		setTitle("User Info");
		this.app=app;
		this.parentComponent=parentComponent;
		this.user=user;
		initialize();
		toDefault();
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 425);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(255, 255, 255));
		panelInfo.setBounds(97, 125, 213, 195);
		contentPane.add(panelInfo);
		panelInfo.setLayout(null);
		
		lblUserId = new JLabel("ID:");
		lblUserId.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblUserId.setBounds(12, 15, 52, 15);
		panelInfo.add(lblUserId);
		
		textID = new JTextArea();
		textID.setFont(new Font("Alkatra", Font.PLAIN, 15));
		textID.setBounds(107, 10, 94, 24);
		textID.setEnabled(false);
		panelInfo.add(textID);
		
		lblUserPassword = new JLabel("Password:");
		lblUserPassword.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblUserPassword.setBounds(12, 45, 83, 15);
		panelInfo.add(lblUserPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(107, 40, 94, 24);
		panelInfo.add(textPassword);
		
		lblUserFirstName = new JLabel("Last Name:");
		lblUserFirstName.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblUserFirstName.setBounds(12, 113, 83, 15);
		panelInfo.add(lblUserFirstName);
		
		textLastName = new JTextArea();
		textLastName.setFont(new Font("Alkatra", Font.PLAIN, 12));
		textLastName.setBounds(107, 108, 94, 24);
		panelInfo.add(textLastName);
		
		lblUserLastName = new JLabel("First Name:");
		lblUserLastName.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblUserLastName.setBounds(12, 143, 33, 15);
		panelInfo.add(lblUserLastName);
		
		textFirstName = new JTextArea();
		textFirstName.setFont(new Font("Alkatra", Font.PLAIN, 12));
		textFirstName.setBounds(107, 138, 94, 24);
		panelInfo.add(textFirstName);
		
		lblUserEmail = new JLabel("Email:");
		lblUserEmail.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblUserEmail.setBounds(12, 173, 52, 15);
		panelInfo.add(lblUserEmail);
		
		textEmail = new JTextArea();
		textEmail.setFont(new Font("Alkatra", Font.PLAIN, 12));
		textEmail.setBounds(107, 168, 94, 24);
		panelInfo.add(textEmail);
		
		lblCheckPassword = new JLabel("Check Password");
		lblCheckPassword.setFont(new Font("Alkatra", Font.PLAIN, 12));
		lblCheckPassword.setBounds(12, 79, 83, 15);
		panelInfo.add(lblCheckPassword);
		
		textCheckPassword = new JPasswordField();
		textCheckPassword.setText((String) null);
		textCheckPassword.setBounds(107, 74, 94, 24);
		panelInfo.add(textCheckPassword);
		
		btnUpdateUserInfo = new JButton("Submit");
		btnUpdateUserInfo.setForeground(new Color(148, 14, 34));
		btnUpdateUserInfo.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnUpdateUserInfo.addActionListener(e->updateUserTable());
		btnUpdateUserInfo.setBounds(215, 330, 95, 23);
		contentPane.add(btnUpdateUserInfo);
		
		btnDefault = new JButton("Origin Settings");
		btnDefault.setForeground(new Color(148, 14, 34));
		btnDefault.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnDefault.addActionListener(e->toDefault());
		btnDefault.setBounds(97, 331, 105, 23);
		contentPane.add(btnDefault);
		
		lblLogo = new JLabel("KinoLee");
		lblLogo.setForeground(new Color(148, 14, 34));
		lblLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 30));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(97, 40, 213, 61);
		contentPane.add(lblLogo);
	}

	private void updateUserTable() {
		String id=textID.getText();
		String email=textEmail.getText();
		String firstName=textFirstName.getText();
		String lastName=textLastName.getText();
		String checkPassWord=textCheckPassword.getText();
		@SuppressWarnings("deprecation")
		String password=textPassword.getText();
		if(!password.equals(checkPassWord)){
			JOptionPane.showMessageDialog(contentPane, "비밀번호가 일치하지 않습니다","error",JOptionPane.ERROR_MESSAGE);
			textCheckPassword.setText("");
			textPassword.setText("");
			return;
		}
		User user=User.builder().userId(id).userPassword(password).userEmail(email).userFirstName(firstName).userLastName(lastName).build();
		int result=userDao.updateUsers(user);
		if(result==1) {
			app.notifyUpdateSuccess();
			dispose();
		}
		else{
			JOptionPane.showMessageDialog(parentComponent, "회원정보 업데이트 실패","error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void toDefault() {
		textID.setText(user.getUserId());
		textPassword.setText(user.getUserPassword());
		textEmail.setText(user.getUserEmail());
		textFirstName.setText(user.getUserFirstName());
		textLastName.setText(user.getUserLastName());
	}
}
