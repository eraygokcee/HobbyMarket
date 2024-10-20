package hobipazari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddProductPage {
    private JFrame frame;
    private JTextField txtUrunAdi;
    private JTextField txtFiyat;
    private JTextField txtStokAdedi;

    public static void main(int iD) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	AddProductPage window = new AddProductPage(iD);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddProductPage(int iD) {
        arayüzOluştur(iD);
    }

    private void arayüzOluştur(int iD) {
        frame = new JFrame();
        frame.setTitle("Ürün Ekleme Sayfası");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblUrunAdi = new JLabel("Ürün Adı:");
        lblUrunAdi.setBounds(139, 17, 66, 20);
        lblUrunAdi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblUrunAdi);

        txtUrunAdi = new JTextField();
        txtUrunAdi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtUrunAdi.setBounds(215, 18, 96, 19);
        panel.add(txtUrunAdi);
        txtUrunAdi.setColumns(10);

        JLabel lblFiyat = new JLabel("Fiyat:");
        lblFiyat.setBounds(165, 57, 40, 20);
        lblFiyat.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblFiyat);

        txtFiyat = new JTextField();
        txtFiyat.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFiyat.setBounds(215, 58, 96, 19);
        panel.add(txtFiyat);
        txtFiyat.setColumns(10);

        JLabel lblStokAdedi = new JLabel("Stok Adedi:");
        lblStokAdedi.setBounds(123, 97, 82, 20);
        lblStokAdedi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblStokAdedi);
        
        JLabel label = new JLabel("");
        label.setBounds(420, 15, 0, 0);
        panel.add(label);

        txtStokAdedi = new JTextField();
        txtStokAdedi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtStokAdedi.setBounds(215, 98, 96, 19);
        panel.add(txtStokAdedi);
        txtStokAdedi.setColumns(10);

        JButton btnUrunEkle = new JButton("Ürün Ekle");
        btnUrunEkle.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnUrunEkle.setBounds(139, 178, 172, 36);
        btnUrunEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					urunEkle(iD);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        JLabel label_1 = new JLabel("");
        label_1.setBounds(292, 18, 0, 0);
        panel.add(label_1);
        panel.add(btnUrunEkle);
        
        JLabel label_2 = new JLabel("");
        label_2.setBounds(311, 15, 0, 0);
        panel.add(label_2);
        
        JButton geriGit = new JButton("Ana Sayfa");
        geriGit.setBackground(new Color(119, 136, 153));
        geriGit.setFont(new Font("Tahoma", Font.PLAIN, 16));
        geriGit.setBounds(139, 225, 172, 28);
        panel.add(geriGit);
    }

    private void urunEkle(int iD) throws SQLException {
    	Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
        // Burada ürün ekleme işlemlerini gerçekleştirin
        String urunAdi = txtUrunAdi.getText();
        String fiyat = txtFiyat.getText();
        String stokAdedi = txtStokAdedi.getText();
		int stok = Integer.valueOf(stokAdedi);
		BigDecimal Fiyat = BigDecimal.valueOf(Integer.valueOf(fiyat));
        System.out.println(stokAdedi);
        String query = "INSERT INTO urun (urun_isim, fiyat, satici_id, stok) VALUES ('"+urunAdi+"', "+Fiyat+","
        + 20011000/*Integer.getInteger(args[0])*/+", "+stok+");";
		PreparedStatement ps;
		ps = conn.prepareStatement(query);
		int r = ps.executeUpdate();
        JOptionPane.showMessageDialog(null , "Ürün Başarıyla Eklendi.");
        frame.setVisible(false);
        MainPage.main(iD);
        conn.close();    }
}


