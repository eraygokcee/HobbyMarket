package hobipazari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpPage extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel kullaniciAdiLabel;
	private static JLabel sifreLabel;
	private static JLabel adLabel;
	private static JLabel soyadLabel;
	private static JLabel adresLabel;
	private static JLabel telefonLabel;
	private static JLabel emailLabel;

    public static void main(String[] args) throws SQLException, IOException {
	
		
        JFrame frame = new JFrame("Kayıt Ol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(982, 515);

        JPanel panel = new JPanel();
                panel.setLayout(null);
        
        kullaniciAdiLabel = new JLabel("Kullanıcı Adı: ");
        kullaniciAdiLabel.setBounds(306, 10, 91, 35);
        panel.add(kullaniciAdiLabel);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(306, 40, 343, 30);
        panel.add(usernameTextField);
        
        
        sifreLabel = new JLabel("Şifre: ");
        sifreLabel.setBounds(306, 75, 91, 35);
        panel.add(sifreLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(306, 105, 343, 30);
        panel.add(passwordField);
        
        
        adLabel = new JLabel("Ad: ");
        adLabel.setBounds(306, 140, 91, 35);
        panel.add(adLabel);
        JTextField firstNameTextField = new JTextField();
        firstNameTextField.setBounds(306, 170, 343, 30);
        panel.add(firstNameTextField);
        
        
        soyadLabel = new JLabel("Soyad: ");
        soyadLabel.setBounds(306, 205, 91, 35);
        panel.add(soyadLabel);
        JTextField lastNameTextField = new JTextField();
        lastNameTextField.setBounds(306, 235, 343, 30);
        panel.add(lastNameTextField);
        
        
        adresLabel = new JLabel("Adres: ");
        adresLabel.setBounds(306, 270, 91, 35);
        panel.add(adresLabel);
        JTextField addressTextField = new JTextField();
        addressTextField.setBounds(306, 300, 343, 30);
        panel.add(addressTextField);
        
        
        telefonLabel = new JLabel("Telefon Numarası: ");
        telefonLabel.setBounds(306, 335, 160, 35);
        panel.add(telefonLabel);
        JTextField phoneNumberTextField = new JTextField();
        phoneNumberTextField.setBounds(306, 365, 343, 30);
        panel.add(phoneNumberTextField);

        emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(306, 400, 160, 35);
        panel.add(emailLabel);
        JTextField emailTextField = new JTextField();
        emailTextField.setBounds(306, 430, 343, 30);
        panel.add(emailTextField);

        
        JButton signUpButton = new JButton("KAYDOL");
        signUpButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        signUpButton.setBounds(725, 189, 138, 100);
                
        signUpButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	    String username= usernameTextField.getText();
        	    String password= passwordField.getText();
        	    String firstName= firstNameTextField.getText();
                String lastName= lastNameTextField.getText();
                String address= addressTextField.getText();
                String phoneNumber= phoneNumberTextField.getText();
                String email= emailTextField.getText();
         
                Connection conn;
				try {
					conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
					String registerQuery = "INSERT INTO kullanici(kullanici_adi, sifre , ad , soyad , adres ,telefon, email ) VALUES ('"+ username + "','" + password + "' ,'" + firstName + "','" + lastName + "','" + address + "','" + phoneNumber + "','" + email + "' )";
					
					Statement s = conn.createStatement();
	        		int check = s.executeUpdate(registerQuery);
	        		
					if(check!=0) {
		                JOptionPane.showMessageDialog(frame, "Başarıyla kaydoldunuz!");
		                frame.dispose();
		                LoginPage.main(null);
		                
					}
	        		
	        		conn.close();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Hata! Bu kullanıcı adı daha önce alınmış veya telefon numarası sayılardan oluşuyor olabilir. Lütfen tekrar deneyiniz!");
					e1.printStackTrace();
				}
              }
            });
            panel.add(signUpButton);
 

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}




