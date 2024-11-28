package com.kinolee.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.kinolee.controller.BookingDao;
import com.kinolee.controller.ScreeningsDao;
import com.kinolee.controller.SeatsStatusDao;
import com.kinolee.model.MyBooking;
import com.kinolee.model.Screening;
import com.kinolee.model.User;
import com.kinolee.view.UserInfoFrame.UpdateNotify;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BookingInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parentComponent;
	private User user;
	private BookingUpdateNotify app;
	private JTable table;
	private JButton btnCancel;
	private DefaultTableModel model;
	private static final String[] COL_BOOKING_INFO= {"Booking Number","title","Date","Time","Screen","Seat","Booked On"};
	private BookingDao bookingDao=BookingDao.INSTANCE;
	private SeatsStatusDao seatsStatusDao=SeatsStatusDao.INSTANCE;
	private ScreeningsDao screeningDao=ScreeningsDao.INSTANCE;
	private JLabel lblBookingInfo;
	private JButton btnChangeSeats;
	private List<MyBooking> bookingInfoList;
	
	public interface BookingUpdateNotify{
		void bookingUpdateSuccess();
	}
	/**
	 * Launch the application.
	 */
	public static void showBookingInfoFrame(Component parentComponent,User user, BookingUpdateNotify app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingInfoFrame frame = new BookingInfoFrame(parentComponent,user, app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private BookingInfoFrame(Component parentComponent,User user, BookingUpdateNotify app) {
		setTitle("Booking History");
		this.parentComponent=parentComponent;
		this.user=user;
		this.app=app;
		initialize();
		initializeTable();
	}
	
	private void initializeTable() {
		model=new DefaultTableModel(null,COL_BOOKING_INFO);
		bookingInfoList=bookingDao.getBookingInfoList(user.getUserId());
		for(MyBooking m : bookingInfoList) {
			Object[] rowData = {
					m.getBookingId(),m.getMovieTitle(),m.getScreeningDate(),m.getScreeningTime(),m.getScreenName(),
					m.getSeatId(),m.getBookingTime()
			};
			model.addRow(rowData);
		}
		table.setModel(model);
		
	}
	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 893, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 65, 841, 125);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnCancel = new JButton("Cancel Booking");
		btnCancel.setForeground(new Color(148, 14, 34));
		btnCancel.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnCancel.addActionListener(e->cancelBooking());
		btnCancel.setBounds(26, 216, 123, 23);
		contentPane.add(btnCancel);
		
		btnChangeSeats = new JButton("Change Seat");
		btnChangeSeats.setForeground(new Color(148, 14, 34));
		btnChangeSeats.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnChangeSeats.addActionListener(e->changeSeat());
		btnChangeSeats.setBounds(167, 216, 123, 23);
		contentPane.add(btnChangeSeats);
		
		lblBookingInfo = new JLabel("Booking History");
		lblBookingInfo.setForeground(new Color(148, 14, 34));
		lblBookingInfo.setFont(new Font("Alkatra", Font.PLAIN, 20));
		lblBookingInfo.setBounds(26, 32, 149, 23);
		contentPane.add(lblBookingInfo);
		
		JLabel lblLogo = new JLabel("KinoLee");
		lblLogo.setForeground(new Color(148, 14, 34));
		lblLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 44));
		lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogo.setBounds(579, 10, 288, 45);
		contentPane.add(lblLogo);
	}
	
	private void changeSeat() {
		int index=checkSelectedRow();
		if(index==-1) return;
		MyBooking myBooking=bookingInfoList.get(index);
		String movieTitle=myBooking.getMovieTitle();
		String movieDate=myBooking.getScreeningDate();
		String movieTime=myBooking.getScreeningTime();
		String seatId=myBooking.getSeatId();
		int confirm=JOptionPane.showConfirmDialog(parentComponent,movieTitle+" "+movieDate+" "+movieTime+" "+seatId+" 좌석을 변경하시겠습니까?");
		if(confirm==JOptionPane.YES_OPTION) {
			String bookingId=myBooking.getBookingId();
			String screeningId=myBooking.getScreeningId();
			Screening screening=getScreeningInstance(screeningId);
			BookingFrame_seat.showBookingFrame_seat(parentComponent, screening, 1, user, true, bookingId, seatId);
		}
		
	}
	
	private Screening getScreeningInstance(String screeningId) {
		return screeningDao.read(screeningId);
	}
	private int checkSelectedRow() {
		int index=table.getSelectedRow();
		if(index==-1) {
			JOptionPane.showMessageDialog(parentComponent, "예매내역을 먼저 선택하세요");
		}
		return index;
		
	}
	private void cancelBooking() {
		int index=checkSelectedRow();
		if(index==-1) return;
		MyBooking myBooking=bookingInfoList.get(index);
		String movieTitle=myBooking.getMovieTitle();
		String movieDate=myBooking.getScreeningDate();
		String movieTime=myBooking.getScreeningTime();
		String seatId=myBooking.getSeatId();
		String screenId=myBooking.getScreenId();
		int confirm=JOptionPane.showConfirmDialog(parentComponent, movieTitle+" "+movieDate+" "+movieTime+" "+seatId+" 예매 취소하겠습니까?");
		if(confirm==JOptionPane.YES_OPTION) {
			String bookingId=myBooking.getBookingId();
			String screeningId=myBooking.getScreeningId();
			int result1=seatsStatusDao.deleteSeatHistory(screeningId,seatId);
			if(result1==-1) {
				JOptionPane.showMessageDialog(parentComponent, "예매 취소 실패","error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int result2=bookingDao.deleteBookingHistory(bookingId);
			if(result2==1) {
				app.bookingUpdateSuccess();
			}
			else{
				JOptionPane.showMessageDialog(parentComponent, "예매 취소 실패","error",JOptionPane.ERROR_MESSAGE);
				int result3=seatsStatusDao.insertToSeatsStatusTable(screeningId, screenId, seatId);
				if(result3==-1) {
					JOptionPane.showMessageDialog(parentComponent, "SeatsStatus테이블 업데이트 필요","error",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
