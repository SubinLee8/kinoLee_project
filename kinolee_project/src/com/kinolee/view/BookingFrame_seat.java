package com.kinolee.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.kinolee.controller.BookingDao;
import com.kinolee.controller.SeatS1Dao;
import com.kinolee.controller.SeatsStatusDao;
import com.kinolee.model.Screening;
import com.kinolee.model.SeatsS1Status;
import com.kinolee.model.SeatS1;
import com.kinolee.model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class BookingFrame_seat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelSeats;
	private JPanel panelBooking;
	private JLabel lblScreen;
	private JToggleButton tglA2;
	private JToggleButton tglA1;
	private JToggleButton tglA3;
	private JToggleButton tglA4;
	private JToggleButton tglA5;
	private JToggleButton tglA7;
	private JToggleButton tglA6;
	private JToggleButton tglB1;
	private JToggleButton tglB2;
	private JToggleButton tglB4;
	private JToggleButton tglB3;
	private JToggleButton tglB5;
	private JToggleButton tglB6;
	private JToggleButton tglB7;
	private JToggleButton tglC1;
	private JToggleButton tglC2;
	private JToggleButton tglC3;
	private JToggleButton tglC4;
	private JToggleButton tglC5;
	private JToggleButton tglC6;
	private JToggleButton tglC7;
	
	private Component parentComponent;
	private Screening screening;
	private int selectedHeadCounts;
	private User user;
	private JLabel lblTotalHeadCounts;
	private JLabel lblPrice;
	private JLabel lblTotalPrice;
	private JLabel lblHeadCounts;
	private JButton btnBooking;
	private JButton btnChangingSeat;
	private List<String> clickedButtonId;
	private int pricePerTicket = 8500;
	private SeatsStatusDao seatsStatusDao = SeatsStatusDao.INSTANCE;
	private BookingDao bookingDao = BookingDao.INSTANCE;

	private boolean isFromBookingInfoFrame;
	private String seatId;
	private String bookingId;

	/**
	 * Launch the application.
	 */
	public static void showBookingFrame_seat(Component parentComponent, Screening screening, int selectedHeadCounts,
			User user,boolean isFromBookingInfoFrame,String bookingId,String seatId) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingFrame_seat frame = new BookingFrame_seat(parentComponent, screening, selectedHeadCounts,
							user,isFromBookingInfoFrame,bookingId,seatId);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private BookingFrame_seat(Component parentComponent, Screening screening, int selectedHeadCounts, User user, boolean isFromBookingInfoFrame,
			String bookingId,String seatId) {
		setTitle("Choose Seats");
		this.parentComponent = parentComponent;
		this.screening = screening;
		this.selectedHeadCounts = selectedHeadCounts;
		this.user = user;
		this.isFromBookingInfoFrame=isFromBookingInfoFrame;
		this.seatId=seatId;
		this.bookingId=bookingId;
		initialize();
		initializeSeats();
	}

	private void initializeSeats() {
		JToggleButton[] Abuttons = { tglA1, tglA2, tglA3, tglA4, tglA5, tglA6, tglA7 };
		JToggleButton[] Bbuttons = { tglB1, tglB2, tglB3, tglB4, tglB5, tglB6, tglB7 };
		JToggleButton[] Cbuttons = { tglC1, tglC2, tglC3, tglC4, tglC5, tglC6, tglC7 };
		JToggleButton[][] buttons = { Abuttons, Bbuttons, Cbuttons };
		List<String> bookedseatsList = seatsStatusDao.getSeatsIdList(screening);
		for (String s : bookedseatsList) {
			int number = checkSeatNumber(s);
			if (s.contains("a")) {
				Abuttons[number - 1].setEnabled(false);
			} else if (s.contains("b")) {
				Bbuttons[number - 1].setEnabled(false);
			} else if (s.contains("c")) {
				Cbuttons[number - 1].setEnabled(false);
			}
		}

	}

	private int checkSeatNumber(String s) {
		int number = Character.getNumericValue(s.charAt(1));
		return number;
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 491, 391);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(170, 170, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelSeats = new JPanel();
		panelSeats.setBackground(new Color(170, 170, 170));
		panelSeats.setBounds(12, 38, 456, 233);
		contentPane.add(panelSeats);
		panelSeats.setLayout(null);

		lblScreen = new JLabel("Screen");
		lblScreen.setHorizontalAlignment(SwingConstants.CENTER);
		lblScreen.setForeground(new Color(255, 255, 255));
		lblScreen.setFont(new Font("Alkatra", Font.PLAIN, 19));
		lblScreen.setOpaque(true);
		lblScreen.setBackground(new Color(148, 14, 34));
		lblScreen.setBounds(94, 10, 292, 38);
		panelSeats.add(lblScreen);

		tglA1 = new JToggleButton("A1");
		tglA1.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA1.setBounds(12, 75, 47, 32);
		panelSeats.add(tglA1);

		tglA2 = new JToggleButton("A2");
		tglA2.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA2.setBounds(64, 75, 47, 32);
		panelSeats.add(tglA2);

		tglA4 = new JToggleButton("A4");
		tglA4.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA4.setBounds(233, 75, 47, 32);
		panelSeats.add(tglA4);

		tglA5 = new JToggleButton("A5");
		tglA5.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA5.setBounds(286, 75, 47, 32);
		panelSeats.add(tglA5);

		tglA6 = new JToggleButton("A6");
		tglA6.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA6.setBounds(345, 75, 47, 32);
		panelSeats.add(tglA6);

		tglA3 = new JToggleButton("A3");
		tglA3.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA3.setBounds(118, 75, 47, 32);
		panelSeats.add(tglA3);

		tglA7 = new JToggleButton("A7");
		tglA7.setFont(new Font("굴림", Font.PLAIN, 9));
		tglA7.setBounds(397, 75, 47, 32);
		panelSeats.add(tglA7);

		tglB1 = new JToggleButton("B1");
		tglB1.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB1.setBounds(12, 128, 47, 32);
		panelSeats.add(tglB1);

		tglB2 = new JToggleButton("B2");
		tglB2.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB2.setBounds(64, 128, 47, 32);
		panelSeats.add(tglB2);

		tglB3 = new JToggleButton("B3");
		tglB3.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB3.setBounds(118, 128, 47, 32);
		panelSeats.add(tglB3);

		tglB4 = new JToggleButton("B4");
		tglB4.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB4.setBounds(233, 128, 47, 32);
		panelSeats.add(tglB4);

		tglB5 = new JToggleButton("B5");
		tglB5.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB5.setBounds(286, 128, 47, 32);
		panelSeats.add(tglB5);

		tglB6 = new JToggleButton("B6");
		tglB6.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB6.setBounds(345, 128, 47, 32);
		panelSeats.add(tglB6);

		tglB7 = new JToggleButton("B7");
		tglB7.setFont(new Font("굴림", Font.PLAIN, 9));
		tglB7.setBounds(397, 128, 47, 32);
		panelSeats.add(tglB7);

		tglC1 = new JToggleButton("C1");
		tglC1.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC1.setBounds(12, 178, 47, 32);
		panelSeats.add(tglC1);

		tglC2 = new JToggleButton("C2");
		tglC2.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC2.setBounds(64, 178, 47, 32);
		panelSeats.add(tglC2);

		tglC3 = new JToggleButton("C3");
		tglC3.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC3.setBounds(118, 178, 47, 32);
		panelSeats.add(tglC3);

		tglC4 = new JToggleButton("C4");
		tglC4.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC4.setBounds(233, 178, 47, 32);
		panelSeats.add(tglC4);

		tglC5 = new JToggleButton("C5");
		tglC5.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC5.setBounds(286, 178, 47, 32);
		panelSeats.add(tglC5);

		tglC6 = new JToggleButton("C6");
		tglC6.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC6.setBounds(345, 178, 47, 32);
		panelSeats.add(tglC6);

		tglC7 = new JToggleButton("C7");
		tglC7.setFont(new Font("굴림", Font.PLAIN, 9));
		tglC7.setBounds(397, 178, 47, 32);
		panelSeats.add(tglC7);

		panelBooking = new JPanel();
		panelBooking.setBackground(new Color(170, 170, 170));
		panelBooking.setBounds(12, 281, 456, 63);
		contentPane.add(panelBooking);
		panelBooking.setLayout(null);

		btnBooking = new JButton("Book Tickets");
		btnBooking.setForeground(new Color(148, 14, 34));
		btnBooking.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnBooking.addActionListener(e -> checkHeadCount());
		btnBooking.setBounds(320, 31, 124, 23);
		panelBooking.add(btnBooking);
		
		btnChangingSeat = new JButton("Change Seats");
		btnChangingSeat.setBounds(349, 41, 95, 23);
		btnChangingSeat.addActionListener(e -> changeSeat());
		btnChangingSeat.setForeground(new Color(148, 14, 34));
		btnChangingSeat.setFont(new Font("Alkatra", Font.PLAIN, 12));
		
		
		if(isFromBookingInfoFrame==false) {
			panelBooking.add(btnBooking);
		}
		else panelBooking.add(btnChangingSeat);

		lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblPrice.setBounds(25, 35, 94, 15);
		panelBooking.add(lblPrice);

		lblTotalPrice = new JLabel(Integer.toString(pricePerTicket * selectedHeadCounts));
		lblTotalPrice.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblTotalPrice.setBounds(131, 35, 52, 15);
		panelBooking.add(lblTotalPrice);

		lblTotalHeadCounts = new JLabel(Integer.toString(selectedHeadCounts));
		lblTotalHeadCounts.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblTotalHeadCounts.setBounds(131, 10, 52, 15);
		panelBooking.add(lblTotalHeadCounts);

		lblHeadCounts = new JLabel("Tickets");
		lblHeadCounts.setFont(new Font("Alkatra", Font.PLAIN, 15));
		lblHeadCounts.setBounds(25, 10, 94, 15);
		panelBooking.add(lblHeadCounts);

	}

	private void changeSeat() {
		getClickedButtonList();
		String newSeatId=clickedButtonId.get(0);
		if (clickedButtonId.size() != selectedHeadCounts) {
			JOptionPane.showMessageDialog(parentComponent, "1개의 좌석을 선택해야합니다.", "error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			int confirm=JOptionPane.showConfirmDialog(parentComponent, "선택한 좌석:" + clickedButtonId + " " + "예매하시겠습니까?");
			if(confirm==JOptionPane.YES_OPTION) {
				int result1=seatsStatusDao.changeHistory(screening,seatId,newSeatId);
				if(result1==-1) {
					JOptionPane.showMessageDialog(parentComponent, "좌석 변경 실패","error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				int result2=bookingDao.changeSeat(bookingId,newSeatId);
				if(result2==1) {
					JOptionPane.showMessageDialog(parentComponent, "좌석 변경 성공!");
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(parentComponent, "좌석 변경 실패","error",JOptionPane.ERROR_MESSAGE);
					seatsStatusDao.changeHistory(screening, newSeatId,seatId);
				}
			}
		}
	}

	private void getClickedButtonList() {
		JToggleButton[] Abuttons = { tglA1, tglA2, tglA3, tglA4, tglA5, tglA6, tglA7 };
		JToggleButton[] Bbuttons = { tglB1, tglB2, tglB3, tglB4, tglB5, tglB6, tglB7 };
		JToggleButton[] Cbuttons = { tglC1, tglC2, tglC3, tglC4, tglC5, tglC6, tglC7 };
		JToggleButton[][] buttons = { Abuttons, Bbuttons, Cbuttons };
		clickedButtonId = new ArrayList<>();
		for (JToggleButton[] group : buttons) {
			for (JToggleButton eachbutton : group) {
				if (eachbutton.isSelected()) {
					clickedButtonId.add(eachbutton.getText());
				}
			}
		}
	}

	private void checkHeadCount() {
		getClickedButtonList();
		if (clickedButtonId.size() != selectedHeadCounts) {
			JOptionPane.showMessageDialog(parentComponent, "인원수에 맞게 좌석을 선택해야합니다.", "error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			int confirm=JOptionPane.showConfirmDialog(parentComponent, "선택한 좌석:" + clickedButtonId + " " + "예매하시겠습니까?");
			if(confirm==JOptionPane.YES_OPTION) {
				for (String s : clickedButtonId) {
					int result1 = bookingDao.insertToBookingTable(screening, user, s.toLowerCase());
					//s1status에도 올리기!!
					if (result1 == -1) {
						JOptionPane.showMessageDialog(parentComponent, "예매 실패!", "error", JOptionPane.ERROR_MESSAGE);
						dispose();
						return;
					}
					int result2=seatsStatusDao.insertToSeatsStatusTable(screening,s.toLowerCase());
					if (result2 == -1) {
						JOptionPane.showMessageDialog(parentComponent, "예매 실패!", "error", JOptionPane.ERROR_MESSAGE);
						dispose();
						return;
					}
				}
				JOptionPane.showMessageDialog(parentComponent, "예매되었습니다. 마이페이지에서 확인 가능합니다.");
				dispose();
			}
		}
	}
}
