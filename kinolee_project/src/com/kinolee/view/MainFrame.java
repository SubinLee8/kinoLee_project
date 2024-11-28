package com.kinolee.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.kinolee.controller.BookingDao;
import com.kinolee.controller.MoviesDao;
import com.kinolee.model.Movie;
import com.kinolee.model.User;
import com.kinolee.view.BookingInfoFrame.BookingUpdateNotify;
import com.kinolee.view.UserInfoFrame.UpdateNotify;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MainFrame implements UpdateNotify,BookingUpdateNotify {

	private JFrame frmKinolee;
	private User user;
	private JButton btnBooking;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField textField;
	private JButton btnSearch;
	private JLabel panelLogo;
	private JPanel panelPosters;
	private JLabel lblPoster;
	private JPanel panelSearch;
	private JButton btnToRight;
	private JButton btnToLeft;
	private JButton btnPlannedPosters;
	private JButton btnCurrentPosters;
	private DefaultTableModel model;
	private MoviesDao movieDao=MoviesDao.INSTANCE;
	private List<Movie> currentMovies;
	private List<Movie> futureMovies;
	private String[] posters;
	private int index;
	
	private static final String[] SEARCH_TYPE= {"Title", "Director"};
	private static final String[] MOVIES_COLUMNS= {"Title","Director","Release Date"};
	private JComboBox comboBox;
	

	/**
	 * Launch the application.
	 */
	public static void showMainFrame(User user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame(user);
					window.frmKinolee.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame(User user) {
		this.user=user;
		initialize();
		initializeCurrentMovieTable();
		getFutureMovieTable();
	}

	private void getFutureMovieTable() {
		futureMovies=movieDao.readFutureMovies();
	}

	private void initializeCurrentMovieTable() {
		currentMovies=movieDao.readCurrentMovies();
		resetTableModel(currentMovies);
		setPosters(currentMovies);
		
	}
	
	private void setPosters(List<Movie> movies) {
		posters=getPostersPath(movies);
		index=0;
		lblPoster.setIcon(new ImageIcon(posters[index]));
		lblPoster.setBounds(57, 22, 229, 344);
		panelPosters.add(lblPoster);
	}

	private String[] getPostersPath(List<Movie> movies) {
		String[] posters=new String[movies.size()];
		for(int i=0;i<movies.size();i++) {
			posters[i]=movies.get(i).getMoviePoster();
		}
		return posters;
	}

	private void resetTableModel(List<Movie> currentMovies) {
		model = new DefaultTableModel(null, MOVIES_COLUMNS);
		for(Movie m : currentMovies) {
			Object[] rowData = {
					m.getMovieTitle(),m.getMovieDirector(),m.getMovieRelease()
			};
			model.addRow(rowData);
		}
		table.setModel(model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKinolee = new JFrame();
		frmKinolee.setTitle("KinoLee");
		frmKinolee.getContentPane().setBackground(new Color(255, 255, 255));
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmKinolee.setBounds(100, 25, 786, 613);
		frmKinolee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKinolee.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(362, 69, 393, 442);
		frmKinolee.getContentPane().add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel(null,MOVIES_COLUMNS);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		panelSearch = new JPanel();
		panelSearch.setBackground(new Color(255, 255, 255));
		panelSearch.setBounds(362, 10, 393, 49);
		frmKinolee.getContentPane().add(panelSearch);
		
		comboBox = new JComboBox();
		comboBox.setBounds(0, 10, 68, 21);
		DefaultComboBoxModel<String> comboboxModel=new DefaultComboBoxModel<>(SEARCH_TYPE);
		panelSearch.setLayout(null);
		comboBox.setModel(comboboxModel);
		panelSearch.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(74, 10, 250, 21);
		panelSearch.add(textField);
		textField.setColumns(20);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(326, 6, 67, 31);
		btnSearch.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnSearch.addActionListener(e->searchByKeyword());
		panelSearch.add(btnSearch);
		
		panelPosters = new JPanel();
		panelPosters.setBackground(new Color(255, 255, 255));
		panelPosters.setBounds(12, 141, 338, 409);
		frmKinolee.getContentPane().add(panelPosters);
		panelPosters.setLayout(null);
		
		lblPoster = new JLabel("");
		
		btnToRight = new JButton(">");
		btnToRight.addActionListener(e->showNextPoster());
		btnToRight.setFont(new Font("굴림", Font.PLAIN, 5));
		btnToRight.setBounds(289, 164, 37, 23);
		panelPosters.add(btnToRight);
		
		btnToLeft = new JButton("<");
		btnToLeft.addActionListener(e->showPreviousPoster());
		btnToLeft.setFont(new Font("굴림", Font.PLAIN, 5));
		btnToLeft.setBounds(12, 164, 37, 23);
		panelPosters.add(btnToLeft);
		
		btnPlannedPosters = new JButton("Coming Soon");
		btnPlannedPosters.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnPlannedPosters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBooking.setEnabled(false);
				setPosters(futureMovies);
				resetTableModel(futureMovies);
			}
		});
		btnPlannedPosters.setBounds(174, 376, 125, 23);
		panelPosters.add(btnPlannedPosters);
		
		btnCurrentPosters = new JButton("Now Showing");
		btnCurrentPosters.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnCurrentPosters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBooking.setEnabled(true);
				setPosters(currentMovies);
				resetTableModel(currentMovies);
			}
		});
		btnCurrentPosters.setBounds(37, 376, 125, 23);
		panelPosters.add(btnCurrentPosters);
		
		panelLogo = new JLabel();
		panelLogo.setFont(new Font("Edu AU VIC WA NT Pre", Font.PLAIN, 60));
		panelLogo.setForeground(new Color(165, 20, 16));
		panelLogo.setText("KinoLee");
		panelLogo.setIcon(null);
		panelLogo.setBounds(73, 10, 215, 88);
		frmKinolee.getContentPane().add(panelLogo);
		
		btnBooking = new JButton("Tickets");
		btnBooking.setForeground(new Color(165, 20, 16));
		btnBooking.setFont(new Font("Alkatra", Font.PLAIN, 18));
		btnBooking.addActionListener(e->checkForBooking());
		btnBooking.setBounds(666, 521, 89, 29);
		frmKinolee.getContentPane().add(btnBooking);
		
		JButton btnMyPage = new JButton("MyPage");
		btnMyPage.setForeground(new Color(0, 0, 0));
		btnMyPage.setFont(new Font("Alkatra", Font.PLAIN, 12));
		btnMyPage.setBounds(142, 95, 77, 23);
		frmKinolee.getContentPane().add(btnMyPage);
		btnMyPage.addActionListener(e->MyPageFrame.showMyPage(btnMyPage, user, MainFrame.this));
	}

	private void searchByKeyword() {
		String keyword=textField.getText();
		if(keyword.equals("")) {
			JOptionPane.showMessageDialog(frmKinolee, "입력된 검색어가 없습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		int type=comboBox.getSelectedIndex();
		List<Movie> searchedMovies=movieDao.searchMovieByKeyword(type,keyword);
		resetTableModel(searchedMovies);
	}

	private void showPreviousPoster() {
		if(index>0) {
			index--;
		}else {
			index=posters.length-1;
		}
		lblPoster.setIcon(new ImageIcon(posters[index]));
	}

	private void showNextPoster() {
		if(index<posters.length-1) {
			index++;
		}else {
			index=0;
		}
		lblPoster.setIcon(new ImageIcon(posters[index]));
	}

	private void checkForBooking() {
		int selectedRow=table.getSelectedRow();
		if(selectedRow==-1) {
			JOptionPane.showMessageDialog(frmKinolee, "선택된 영화가 없습니다","error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		Movie selectedMovie=buildMovieFromSelectedRow(selectedRow);
		BookingFrame_date.showBookingFrame_date(frmKinolee,selectedMovie,user);
		
	}

	private Movie buildMovieFromSelectedRow(int selectedRow) {
		String movieDirector=currentMovies.get(selectedRow).getMovieDirector();
		String movieId=currentMovies.get(selectedRow).getMovieId();
		String movieTitle=currentMovies.get(selectedRow).getMovieTitle();
		String movieRelease=currentMovies.get(selectedRow).getMovieRelease();
		String moviePoster=currentMovies.get(selectedRow).getMoviePoster();
		return Movie.builder().movieDirector(movieDirector).movieId(movieId).movieTitle(movieTitle).movieRelease(movieRelease).moviePoster(moviePoster).build();
	}

	@Override
	public void notifyUpdateSuccess() {
		JOptionPane.showMessageDialog(frmKinolee, "회원정보 업데이트 성공!");
		JOptionPane.showMessageDialog(frmKinolee,"자동으로 로그아웃됩니다");
		frmKinolee.dispose();
		String[] arqs= {"new","start"};
		LoginFrame.main(arqs);
		
	}

	@Override
	public void bookingUpdateSuccess() {
		JOptionPane.showMessageDialog(frmKinolee, "예매 취소 성공!");
	}
}
