package com.kinolee.view;

import java.awt.EventQueue;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.kinolee.controller.UsersDao;
import com.kinolee.model.User;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textId;
	private JLabel lblId;
	private JPasswordField textPassword;
	private JLabel lblPassword;
	private JButton btnLogin;
	private UsersDao userDao=UsersDao.Instance;
	private JLabel lblLogo;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 404, 400);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 149, 366, 86);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textId = new JTextField();
		textId.setBounds(137, 11, 178, 21);
		panel.add(textId);
		textId.setColumns(10);
		
		lblId = new JLabel("Id");
		lblId.setForeground(new Color(150, 2, 0));
		lblId.setFont(new Font("Alkatra", Font.PLAIN, 17));
		lblId.setBounds(109, 10, 52, 21);
		panel.add(lblId);
		
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(150, 2, 0));
		lblPassword.setFont(new Font("Alkatra", Font.PLAIN, 17));
		lblPassword.setBounds(56, 53, 116, 27);
		panel.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(10);
		textPassword.setBounds(137, 53, 178, 21);
		panel.add(textPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnLogin.addActionListener(e->checkLoginValid());
		btnLogin.setBounds(154, 262, 95, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("Registration");
		btnRegister.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnRegister.addActionListener(e->RegisterFrame.showRegisterFrame());
		btnRegister.setBounds(154, 295, 95, 23);
		contentPane.add(btnRegister);
		
		lblLogo = new JLabel("--Login--");
		lblLogo.setForeground(new Color(128, 128, 128));
		lblLogo.setFont(new Font("Alkatra", Font.BOLD, 20));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(null);
		lblLogo.setBounds(138, 124, 123, 23);
		contentPane.add(lblLogo);
		
		JLabel lblKinolee = new JLabel("KinoLee");
		lblKinolee.setIcon(null);
		lblKinolee.setHorizontalAlignment(SwingConstants.CENTER);
		lblKinolee.setForeground(new Color(165, 20, 16));
		lblKinolee.setFont(new Font("Edu AU VIC WA NT Pre", Font.ITALIC, 30));
		lblKinolee.setBounds(12, 50, 366, 40);
		contentPane.add(lblKinolee);
	}

	private void checkLoginValid() {
		String id=textId.getText();
		String password=textPassword.getText();
		if(id.equals("")||password.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "아이디와 비밀번호를 모두 입력하세요","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		User user=userDao.read(id);
		if(user!=null && user.getUserPassword().equals(password)) {
			
				JOptionPane.showMessageDialog(contentPane, "로그인 성공!");
				MainFrame.showMainFrame(user);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(contentPane, "아이디 또는 비밀번호가 일치하지 않습니다", "LoginFailed", JOptionPane.ERROR_MESSAGE);
				textId.setText("");
				textPassword.setText("");
			}
		}
	}

	

