package hobipazari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserInfoPage {
    private JFrame frame;
    private JTextField yeniAdresTextField;
    private JTextField yeniAdTextField;
    private JTextField yeniSoyadTextField;
    private JTextField kullanıcıAdıTextField;
    private JTextField yeniSifreTextField;
    private JTextField yeniEmailTextField;
    private JTextField yeniTelefonTextField;
    JLabel KullaniciAdiLabel;
    JLabel yeniSifreLabel;
    JLabel yeniTelefonLabel;
    JLabel yeniEmailLabel;
    JLabel yeniAdresLabel;
    JLabel yeniSoyadLabel;
    JLabel yeniAdLabel;
    
    public static void main(int iD) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UpdateUserInfoPage window = new UpdateUserInfoPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UpdateUserInfoPage() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Kullanıcı Bilgi Güncelleme Sayfası");
        frame.setSize(982, 515);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        JOptionPane.showMessageDialog(frame, "Kullanıcı adı güncellenememektedir. Lütfen kendi kullanıcı adınızı girerek diğer bilgilerinizi güncelleyiniz.");
        
        KullaniciAdiLabel = new JLabel("Kullanıcı Adı:");
        KullaniciAdiLabel.setBounds(180, 209, 150, 19);
        panel.add(KullaniciAdiLabel);
        kullanıcıAdıTextField = new JTextField();
        kullanıcıAdıTextField.setBounds(300, 206, 96, 19);
        panel.add(kullanıcıAdıTextField);
        
        yeniSifreLabel = new JLabel("Yeni Şifre:");
        yeniSifreLabel.setBounds(430, 209, 100, 19);
        panel.add(yeniSifreLabel);
        yeniSifreTextField = new JTextField();
        yeniSifreTextField.setBounds(500, 206, 96, 19);
        panel.add(yeniSifreTextField);
        
        yeniEmailLabel = new JLabel("Yeni E-Mail:");
        yeniEmailLabel.setBounds(630, 209, 100, 19);
        panel.add(yeniEmailLabel);
        yeniEmailTextField = new JTextField();
        yeniEmailTextField.setBounds(715, 206, 160, 19);
        panel.add(yeniEmailTextField);

        yeniAdresLabel = new JLabel("Yeni Adres:");
        yeniAdresLabel.setBounds(180, 262, 160, 19);
        panel.add(yeniAdresLabel);
        yeniAdresTextField = new JTextField();
        yeniAdresTextField.setBounds(300, 259, 96, 19);
        panel.add(yeniAdresTextField);

         yeniAdLabel = new JLabel("Yeni Ad:");
        yeniAdLabel.setBounds(430, 262, 160, 19);
        panel.add(yeniAdLabel);
        yeniAdTextField = new JTextField();
        yeniAdTextField.setBounds(500, 259, 96, 19);
        panel.add(yeniAdTextField);

         yeniSoyadLabel = new JLabel("Yeni Soyad:");
        yeniSoyadLabel.setBounds(630, 262, 160, 19);
        panel.add(yeniSoyadLabel);
        yeniSoyadTextField = new JTextField();
        yeniSoyadTextField.setBounds(715, 259, 96, 19);
        panel.add(yeniSoyadTextField);
        
        yeniTelefonLabel = new JLabel("Telefon Numarası: ");
        yeniTelefonLabel.setBounds(306, 320, 160, 19);
        panel.add(yeniTelefonLabel);
        yeniTelefonTextField = new JTextField();
        yeniTelefonTextField.setBounds(430, 320, 120, 19);
        panel.add(yeniTelefonTextField);

        JButton btnGuncelle = new JButton("Bilgileri Güncelle");
        btnGuncelle.setBounds(358, 357, 291, 98);
        btnGuncelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bilgileriGuncelle();
            }
        });
        panel.add(btnGuncelle);
    }

    private void bilgileriGuncelle() {
        String kullaniciAdi = kullanıcıAdıTextField.getText();
        String yeniSifre = yeniSifreTextField.getText();
        String yeniEmail = yeniEmailTextField.getText();
        String yeniAdres = yeniAdresTextField.getText();
        String yeniAd = yeniAdTextField.getText();
        String yeniSoyad = yeniSoyadTextField.getText();
        String yeniTelefon = yeniTelefonTextField.getText();
        
        Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
			String updateQuery = "UPDATE kullanici SET sifre = '" + yeniSifre + "' , ad = '" + yeniAd + "', soyad = '" + yeniSoyad + "' , adres = '" + yeniAdres + "' ,telefon = '" + yeniTelefon + "', email = '" + yeniEmail + "' WHERE kullanici_adi = '" + kullaniciAdi + "'  ";
			
			Statement s = conn.createStatement();
    		int check = s.executeUpdate(updateQuery);
    		
			if(check!=0) {
                JOptionPane.showMessageDialog(frame, "Profil Başarıyla Güncellendi!");
                frame.dispose();
                MainPage.main(2); /////dikkkkkkKKKKKAATTT
			}	
    		conn.close();
    		}
		catch (SQLException e1) {
			JOptionPane.showMessageDialog(frame, "Hata! Telefon numarası sayılardan oluşmuyor olabilir. Lütfen tekrar deneyiniz!");
			e1.printStackTrace();
		}
    	
        }

}

