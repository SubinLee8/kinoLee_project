package com.kinolee.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.kinolee.view.BookingInfoFrame.BookingUpdateNotify;
import com.kinolee.view.UserInfoFrame.UpdateNotify;
import com.kinolee.model.User;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MyPageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private User user;
	private Component parentComponent;
	private MainFrame app;

	/**
	 * Launch the application.
	 */
	public static void showMyPage(Component parentComponent, User user, MainFrame app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyPageFrame frame = new MyPageFrame(parentComponent, user, app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MyPageFrame(Component parentComponent, User user, MainFrame app) {
		setTitle("My Page");
		this.app = app;
		this.parentComponent = parentComponent;
		this.user = user;
		initialize();
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 393, 264);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel(user.getUserFirstName());
		lblName.setFont(new Font("AlternateGothic2 BT", Font.PLAIN, 15));
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(213, 92, 130, 44);
		contentPane.add(lblName);
		
		JLabel lblHello = new JLabel("Hello,");
		lblHello.setForeground(new Color(128, 128, 128));
		lblHello.setFont(new Font("Alkatra", Font.PLAIN, 20));
		lblHello.setBounds(160, 100, 67, 29);
		contentPane.add(lblHello);
		
		JLabel lblEnd = new JLabel("");
		lblEnd.setBounds(326, 100, 67, 29);
		contentPane.add(lblEnd);
		
		JButton btnCheckBooking = new JButton("Check/Cancel/Update Booking");
		btnCheckBooking.setForeground(new Color(148, 14, 34));
		btnCheckBooking.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnCheckBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookingInfoFrame.showBookingInfoFrame(parentComponent, user, app);
				dispose();
			}});
		btnCheckBooking.setBounds(160, 181, 183, 23);
		contentPane.add(btnCheckBooking);
		
		JButton btnUserInfo = new JButton("Check/Update User Info");
		btnUserInfo.setForeground(new Color(148, 14, 34));
		btnUserInfo.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnUserInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInfoFrame.showUserInfoFrame(parentComponent, user,app);
				dispose();
			}
		});
		btnUserInfo.setBounds(160, 139, 183, 23);
		contentPane.add(btnUserInfo);
		
		JLabel lblLogo = new JLabel("KinoLee ");
		lblLogo.setForeground(new Color(148, 14, 34));
		lblLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 33));
		lblLogo.setBounds(12, 10, 271, 80);
		contentPane.add(lblLogo);
	}
}
