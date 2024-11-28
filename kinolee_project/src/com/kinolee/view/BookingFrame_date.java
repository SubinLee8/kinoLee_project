package com.kinolee.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.kinolee.controller.ScreeningsDao;
import com.kinolee.controller.SeatsStatusDao;
import com.kinolee.model.Movie;
import com.kinolee.model.Screening;
import com.kinolee.model.SeatsS1Status;
import com.kinolee.model.User;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BookingFrame_date extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Component parentComponent;
	private Movie movie;
	private JLabel lblPoster;
	private JLabel lblHeadCount;
	private JComboBox comboBox_HeadCount;
	private JComboBox comboBoxTime;
	private JComboBox comboBoxDate;
	private JLabel lblTime;
	private JLabel lblDate;
	private JPanel panelDateTime;
	List<String> dates;
	List<String> times;
	private String[] headCounts= {"1","2","3","4","5","6","7","8"};
	private String[] newHeadCounts;
	private User user;
	private int selectedHeadCounts;
	private int numberOfTotalSeats=21;
	
	List<Screening> screenings;
	private ScreeningsDao screeningDao=ScreeningsDao.INSTANCE;
	private SeatsStatusDao seatsStatusDao=SeatsStatusDao.INSTANCE;
	private JButton btnSeats;
	private Screening screening; 
	private JLabel lblLogo;
	
	/**
	 * Launch the application.
	 */
	public static void showBookingFrame_date(Component parentComponent, Movie movie,User user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingFrame_date frame = new BookingFrame_date(parentComponent,movie,user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private BookingFrame_date(Component pareComponent, Movie movie,User user) {
		setTitle("Booking");
		this.user=user;
		this.parentComponent=pareComponent;
		this.movie=movie;
		initialize();
	}
	
	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 430);
		setLocationRelativeTo(parentComponent);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPoster = new JLabel("");
		lblPoster.setBounds(12, 23, 229, 344);
		lblPoster.setIcon(new ImageIcon(movie.getMoviePoster()));
		contentPane.add(lblPoster);
		
		panelDateTime = new JPanel();
		panelDateTime.setBackground(new Color(255, 255, 255));
		panelDateTime.setBounds(265, 165, 210, 169);
		contentPane.add(panelDateTime);
		panelDateTime.setLayout(null);
		
		lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Alkatra", Font.PLAIN, 18));
		lblDate.setBounds(-2, 26, 64, 42);
		panelDateTime.add(lblDate);
		
		lblTime = new JLabel("Time");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setFont(new Font("Alkatra", Font.PLAIN, 18));
		lblTime.setBounds(-14, 67, 80, 42);
		panelDateTime.add(lblTime);
		
		comboBoxDate = new JComboBox();
		comboBoxDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setComboBoxTime();
				setHeadCounts();
			}
		});
		comboBoxDate.setBounds(74, 37, 113, 23);
		setComboBoxDate();
		panelDateTime.add(comboBoxDate);
		
		comboBoxTime = new JComboBox();
		comboBoxTime.addActionListener(e->setHeadCounts());
		comboBoxTime.setBounds(74, 78, 113, 23);
		panelDateTime.add(comboBoxTime);
		setComboBoxTime();
		
		lblHeadCount = new JLabel("Tickets");
		lblHeadCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeadCount.setFont(new Font("Alkatra", Font.PLAIN, 18));
		lblHeadCount.setBounds(-14, 106, 80, 42);
		panelDateTime.add(lblHeadCount);
		
		comboBox_HeadCount = new JComboBox();
		comboBox_HeadCount.setBounds(74, 117, 113, 23);
		panelDateTime.add(comboBox_HeadCount);
		setHeadCounts();
		btnSeats = new JButton("Select Seats");
		btnSeats.setForeground(new Color(165, 20, 16));
		btnSeats.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnSeats.addActionListener(e->checkFinal());
		btnSeats.setBounds(340, 344, 112, 23);
		contentPane.add(btnSeats);
		
		lblLogo = new JLabel("KinoLee");
		lblLogo.setForeground(new Color(165, 20, 16));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 45));
		lblLogo.setBounds(261, 54, 214, 75);
		contentPane.add(lblLogo);
	}
	private void checkFinal() {
		getSelectedHeadCounts();
		BookingFrame_seat.showBookingFrame_seat(parentComponent,screening,selectedHeadCounts,user,false,null,null);
	}
	
	private void getSelectedHeadCounts() {
		int index=comboBox_HeadCount.getSelectedIndex();
		selectedHeadCounts=Integer.parseInt(newHeadCounts[index]);
	}

	private void setHeadCounts() {
		int indexDate=comboBoxDate.getSelectedIndex();
		int indexTime=comboBoxTime.getSelectedIndex();
		String date=dates.get(indexDate);
		String time=times.get(indexTime);
		screening=screeningDao.read(movie.getMovieId(), date, time);
		List<SeatsS1Status> reservedSeats = seatsStatusDao.read(screening.getScreeningId());
		int leftSeats=numberOfTotalSeats-reservedSeats.size();
		int availableSeats=headCounts.length;
		if(8>leftSeats) {
			availableSeats=leftSeats;
		}
		newHeadCounts= new String[availableSeats];
		for(int i=0;i<availableSeats;i++) {
			newHeadCounts[i]=headCounts[i];
		}
		comboBox_HeadCount.setModel(new DefaultComboBoxModel<>(newHeadCounts));
	}
	
	private void setComboBoxTime() {
		int index=comboBoxDate.getSelectedIndex();
		times=new ArrayList<String>();
		String date=dates.get(index);
		for(Screening s: screenings) {
			if(s.getScreeningDate().equals(date)) {
				String time=s.getScreeningTime();
				times.add(time);
			}
		}
		comboBoxTime.setModel(new DefaultComboBoxModel<>(times.toArray()));
	}

	private void setComboBoxDate() {
		dates=new ArrayList<String>();
		String movieId=movie.getMovieId();
		screenings=screeningDao.getScreeningByMovieId(movieId);
		int index=0;
		for(Screening s : screenings) {
			String date=s.getScreeningDate();
			if(index==0) {
				dates.add(date);
				index++;}
			else {
				if(!date.equals(dates.get(index-1))) {
					dates.add(date);
					index++;
				}
				}
		}
		comboBoxDate.setModel(new DefaultComboBoxModel<>(dates.toArray()));
	}
}
