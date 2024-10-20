package hobipazari;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class UpdateProduct {
	private static Connection conn;
    private JFrame frame;
    private JTextField txtUrunAdi;
    private JTextField txtFiyat;
    private JTextField txtStokAdedi;

    
    public static void main(int productID) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
                	new UpdateProduct(productID);
                }
                 catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UpdateProduct(int urunID) {
        arayüzGöster(urunID);
    }

    private void arayüzGöster(int urunID) {
        frame = new JFrame();
        frame.setTitle("Ürün Güncelleme Sayfası");
        frame.setSize(982, 515);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblUrunAdi = new JLabel("Yeni Ürün Adı:");
        lblUrunAdi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblUrunAdi);

        txtUrunAdi = new JTextField();
        txtUrunAdi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtUrunAdi);
        txtUrunAdi.setColumns(10);

        JLabel lblFiyat = new JLabel("Yeni Fiyat:");
        lblFiyat.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblFiyat);

        txtFiyat = new JTextField();
        txtFiyat.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtFiyat);
        txtFiyat.setColumns(10);

        JLabel lblStokAdedi = new JLabel("Yeni Stok Adedi:");
        lblStokAdedi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(lblStokAdedi);

        txtStokAdedi = new JTextField();
        txtStokAdedi.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(txtStokAdedi);
        txtStokAdedi.setColumns(10);

        JButton btnGuncelle = new JButton("Ürünü Güncelle");
        btnGuncelle.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnGuncelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					urunGuncelle(urunID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        panel.add(btnGuncelle);
        
        JButton btnÜrünSil = new JButton("Ürünü Sil");
        btnÜrünSil.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnÜrünSil);
        
        JButton btnNewButton = new JButton("Mağaza Sayfası");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Ana Sayfa");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(btnNewButton_1);
        frame.setVisible(true);
    }

    private void urunGuncelle(int urunID) throws SQLException {
        // Burada ürün güncelleme işlemlerini gerçekleştirin
        String urunAdi = txtUrunAdi.getText();
        String fiyat = txtFiyat.getText();
        String stokAdedi = txtStokAdedi.getText();
        BigDecimal Fiyat = BigDecimal.valueOf(Integer.valueOf(fiyat));
        int Stok = Integer.valueOf(stokAdedi);
        System.out.println(urunID);
    	CallableStatement cstm;
    	cstm = conn.prepareCall("{? = call updateproduct(?,?,?,?)}");
    	cstm.registerOutParameter(1, Types.INTEGER);
    	cstm.setInt(2, urunID);
    	cstm.setString(3, urunAdi);
    	cstm.setBigDecimal(4, Fiyat);
    	cstm.setInt(5, Stok);
    


		boolean rowcount = cstm.execute();
		System.out.print(rowcount);
    }
}

