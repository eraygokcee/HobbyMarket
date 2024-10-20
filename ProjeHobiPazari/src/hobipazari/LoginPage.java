package hobipazari;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField passwordField;
	static int result=-1;
	static String ad;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public LoginPage() {

		setTitle("Giriş Sayfası");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 982, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HOBİ PAZARI");
		lblNewLabel.setBounds(357, 19, 254, 64);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));
		contentPane.add(lblNewLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameField.setBounds(357, 144, 254, 37);
		usernameField.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JButton registerButton = new JButton("Kayıt Ol");
		registerButton.setBounds(439, 390, 89, 21);
		registerButton.setBackground(new Color(224, 255, 255));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent kaydolmaSayfasınaGit) {///buraya databasee ekleme fonksyionu gelecvek
                
				
				try {
					SignUpPage.main(null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	     }); contentPane.add(registerButton);
		
		JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameLabel.setBounds(357, 115, 100, 19);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Şifre:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordLabel.setBounds(357, 219, 100, 19);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setColumns(10);
		passwordField.setAlignmentY(0.0f);
		passwordField.setBounds(357, 248, 254, 37);
		contentPane.add(passwordField);
		
		JButton loginButton = new JButton("Giriş Yap");
		loginButton.setBounds(357, 354, 254, 26);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent girisKontrol) {
				String kullanici_adi=usernameField.getText();
				String sifre=passwordField.getText();
				Connection conn;
				try {
					int check=0;
					conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
					
					
					PreparedStatement ps = conn.prepareStatement("SELECT kullanici_id, kullanici_adi FROM kullanici WHERE kullanici_adi=? AND sifre=?");
					ps.setString(1, kullanici_adi);
					ps.setString(2, sifre);
					
					
	        		ResultSet rs = ps.executeQuery();
	        		if(rs.next()) {
		        		result=(rs.getInt(1));
		        		ad=rs.getString(2);
		        		check=1;

	        		}
	        		
	        		if(check==1) {
						dispose();
	        			MainPage.main(result);
	        		}
	        		
	        		else {
						JFrame frame = new JFrame("Giriş Sayfası");
				        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				        frame.setSize(982, 515);
		                JOptionPane.showMessageDialog(frame, "Yanlış Giriş Yaptınız!");
					}
					
	        		conn.close();
	        		
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
              }
			
		});

		contentPane.add(loginButton);
		
	
	}
}
