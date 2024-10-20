package hobipazari;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class MainPage extends JFrame {
	
    public static void main(int ID) {
		new MainPage(ID);
}
	
	
	
    public MainPage(int iD) {
        setTitle("Ana Sayfa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(982, 515);
        getContentPane().setLayout(null);
        
        JButton urunEkleButton = new JButton("Ürün Ekle");
        urunEkleButton.setForeground(new Color(153, 0, 0));
        urunEkleButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        urunEkleButton.setBounds(492, 199, 160, 80);
        getContentPane().add(urunEkleButton);
                
        JButton kullaniciBilgiGuncelleButton = new JButton("Kullanıcı Bilgisi Güncelle");
        kullaniciBilgiGuncelleButton.setForeground(new Color(153, 0, 0));
        kullaniciBilgiGuncelleButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        kullaniciBilgiGuncelleButton.setBounds(675, 199, 283, 80);
        getContentPane().add(kullaniciBilgiGuncelleButton);
                        
        JButton magazayaGitButton = new JButton("Mağazaya Git");
        magazayaGitButton.setForeground(new Color(153, 0, 0));
        magazayaGitButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        magazayaGitButton.setBounds(307, 199, 160, 80);
        getContentPane().add(magazayaGitButton);
                                
        JButton satisUrunleriButton = new JButton("Satıştaki Ürünlerim");
        satisUrunleriButton.setForeground(new Color(153, 0, 0));
        satisUrunleriButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        satisUrunleriButton.setBounds(10, 199, 267, 80);
        getContentPane().add(satisUrunleriButton);
                                        
        JLabel lblNewLabel = new JLabel("Hoşgeldin " + iD); //kullanıcı ismini donduren fonksiyon
        lblNewLabel.setForeground(new Color(102, 0, 0));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel.setBounds(219, 10, 530, 138);
        getContentPane().add(lblNewLabel);
                                        
        JButton btnHesaptankYap = new JButton("Hesaptan Çıkış Yap");
        btnHesaptankYap.setForeground(new Color(153, 0, 0));
        btnHesaptankYap.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnHesaptankYap.setBounds(307, 342, 345, 80);
        getContentPane().add(btnHesaptankYap);
                                        
        urunEkleButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddProductPage.main(iD);
        		setVisible(false);
        	}
        });  
                                        
        btnHesaptankYap.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginPage.main(null);
        		setVisible(false);
        	}
        });        
        satisUrunleriButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					MyMarketPage.main(iD);
	        		setVisible(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        	}
        });
        magazayaGitButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Mağazaya gitmek için gerekli kodu ekleyin.
        		MarketPage.main(iD);
        		setVisible(false);
        	}
        });
        kullaniciBilgiGuncelleButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		UpdateUserInfoPage.main(iD);
        		setVisible(false);
        	}
        });
        
        	setVisible(true);
    }



	
    

}



