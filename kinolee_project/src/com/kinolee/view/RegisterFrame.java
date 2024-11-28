package com.kinolee.view;

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
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textId;
	private JLabel lblId;
	private JPasswordField textPassWord;
	private JPasswordField textCheckPW;
	private JTextField textEmail;
	private JTextField textLastName;
	private JTextField textFirstName;
	private JLabel lblcheckPassWord;
	private JLabel lblPassWord;
	private JLabel lblLastName;
	private JLabel lblFirstName;
	private JLabel lblEmail;
	private UsersDao userDao=UsersDao.Instance;
	private JLabel lblLogo;

	/**
	 * Launch the application.
	 */
	public static void showRegisterFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private RegisterFrame() {
		setTitle("Register");
		initialize();
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 337, 435);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(48, 99, 232, 205);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblId = new JLabel("Id");
		lblId.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(12, 27, 66, 15);
		panel.add(lblId);
		
		textId = new JTextField();
		textId.setBounds(90, 24, 106, 21);
		panel.add(textId);
		textId.setColumns(10);
		
		lblPassWord = new JLabel("Password");
		lblPassWord.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblPassWord.setBounds(12, 56, 66, 15);
		panel.add(lblPassWord);
		
		textPassWord = new JPasswordField();
		textPassWord.setColumns(10);
		textPassWord.setBounds(90, 53, 106, 21);
		panel.add(textPassWord);
		
		lblcheckPassWord = new JLabel("Check Pw");
		lblcheckPassWord.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblcheckPassWord.setBounds(12, 84, 66, 15);
		panel.add(lblcheckPassWord);
		
		textCheckPW = new JPasswordField();
		textCheckPW.setColumns(10);
		textCheckPW.setBounds(90, 81, 106, 21);
		panel.add(textCheckPW);
		
		lblEmail = new JLabel("email");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblEmail.setBounds(12, 172, 66, 15);
		panel.add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(90, 169, 106, 21);
		panel.add(textEmail);
		
		lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblLastName.setBounds(12, 115, 72, 15);
		panel.add(lblLastName);
		
		textLastName = new JTextField();
		textLastName.setColumns(10);
		textLastName.setBounds(90, 112, 106, 21);
		panel.add(textLastName);
		
		textFirstName = new JTextField();
		textFirstName.setColumns(10);
		textFirstName.setBounds(90, 143, 106, 21);
		panel.add(textFirstName);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblFirstName.setBounds(12, 147, 72, 15);
		panel.add(lblFirstName);
		
		JButton btnRegister = new JButton("Submit");
		btnRegister.setForeground(new Color(148, 14, 34));
		btnRegister.setFont(new Font("Alkatra", Font.PLAIN, 14));
		btnRegister.addActionListener(e->registerNewUser());
		btnRegister.setBounds(120, 314, 95, 23);
		contentPane.add(btnRegister);
		
		lblLogo = new JLabel("KinoLee");
		lblLogo.setForeground(new Color(148, 14, 34));
		lblLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 37));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(46, 33, 234, 44);
		contentPane.add(lblLogo);
	}

	private void registerNewUser() {
		String id=textId.getText();
		String password=textPassWord.getText();
		String checkPassWord=textCheckPW.getText();
		String firstName=textFirstName.getText();
		String lastName=textLastName.getText();
		String email=textEmail.getText();
		
		if(id.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(password.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(checkPassWord.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(firstName.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(lastName.equals(" ")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(email.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "입력되지 않은 정보가 있습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!password.equals(checkPassWord)){
			JOptionPane.showMessageDialog(contentPane, "비밀번호가 일치하지 않습니다","error",JOptionPane.ERROR_MESSAGE);
			textCheckPW.setText("");
			textPassWord.setText("");
			return;
		}
		User sameIdUser=userDao.read(id);
		if(sameIdUser!=null) {
			JOptionPane.showMessageDialog(contentPane, "동일한 아이디가 이미 존재합니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		User user=User.builder().userEmail(email).userPassword(password).userId(id).userFirstName(firstName).userLastName(lastName).build();
		int result2=userDao.insertUserToUsersTable(user);
		if(result2==1) {
			JOptionPane.showMessageDialog(contentPane, "회원가입 성공!");
			dispose();
		}
		
	}

}
