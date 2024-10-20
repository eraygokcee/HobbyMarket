package hobipazari;

import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class MyMarketPage{
	private static Connection conn;
	private static JTextField textUrunAd;
	private static JTextField textFiyat1;
	private static JTextField textFiyat2;
	private static JTextField textSatışSayısı;
	private static JTextField textStokSayısı;
	
	public static void main(int iD) throws SQLException {//args[0] -> kullanıcıID
	
	JFrame frame = new JFrame("Mağaza Sayfası");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(982, 515);

	JPanel panel = new JPanel();
	panel.setBorder(new CompoundBorder());
	
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	table.setShowHorizontalLines(false);
	table.setFillsViewportHeight(true);
	table.setFont(new Font("Tahoma", Font.PLAIN, 14));

	model.addColumn("Ürün ID");
	model.addColumn("Ürün İsmi");
	model.addColumn("Fiyat");
	model.addColumn("Satış sayısı");
	model.addColumn("Stok adedi");

	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HobiPazari", "postgres", "2001");
	String query = "SELECT urun_id,urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id = 20011000";	
	printTableFromQuery(model, query);


	panel.setLayout(null);
	DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Hücre içeriğine özel bir font atayabilirsiniz
            c.setFont(new Font("Tahoma", Font.PLAIN, 14));

            return c;
        }
    };
    table.getColumnModel().getColumn(0).setPreferredWidth(200);
    table.getColumnModel().getColumn(1).setPreferredWidth(400);
    table.getColumnModel().getColumn(2).setPreferredWidth(200);
    table.getColumnModel().getColumn(3).setPreferredWidth(100);
    table.getColumnModel().getColumn(4).setPreferredWidth(100);
    table.setRowHeight(30);
	JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setBounds(0, 17, 759, 416);
	panel.add(scrollPane);
	frame.getContentPane().add(panel);
	
	JButton btnGüncelle = new JButton("GÜNCELLE");
	btnGüncelle.setBounds(0, 433, 380, 45);
	btnGüncelle.setFont(new Font("Tahoma", Font.BOLD, 30));
	panel.add(btnGüncelle);

	
	textUrunAd = new JTextField();
	textUrunAd.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textUrunAd.setBounds(790, 114, 141, 26);
	panel.add(textUrunAd);
	textUrunAd.setColumns(10);
	
	
	JLabel lblNewLabel = new JLabel("Ürün İsmi");
	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblNewLabel.setBounds(790, 88, 75, 26);
	panel.add(lblNewLabel);
	
	JLabel lblFiyatAral = new JLabel("Fiyat");
	lblFiyatAral.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblFiyatAral.setBounds(790, 171, 105, 26);
	panel.add(lblFiyatAral);
	
	textFiyat1 = new JTextField();
	textFiyat1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textFiyat1.setColumns(10);
	textFiyat1.setBounds(790, 195, 56, 26);
	panel.add(textFiyat1);
	
	textFiyat2 = new JTextField();
	textFiyat2.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textFiyat2.setColumns(10);
	textFiyat2.setBounds(875, 195, 56, 26);
	panel.add(textFiyat2);
	
	JLabel lblNewLabel_1 = new JLabel("-");
	lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
	lblNewLabel_1.setBounds(856, 192, 24, 26);
	panel.add(lblNewLabel_1);
	
	textSatışSayısı = new JTextField();
	textSatışSayısı.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textSatışSayısı.setColumns(10);
	textSatışSayısı.setBounds(790, 270, 141, 26);
	panel.add(textSatışSayısı);
	
	JLabel lblSatcAd = new JLabel("Satış Sayısı");
	lblSatcAd.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblSatcAd.setBounds(790, 244, 90, 26);
	panel.add(lblSatcAd);
	
	textStokSayısı = new JTextField();
	textStokSayısı.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textStokSayısı.setColumns(10);
	textStokSayısı.setBounds(790, 348, 141, 26);
	panel.add(textStokSayısı);
	
	JLabel lblStokSays = new JLabel("Stok Sayısı");
	lblStokSays.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblStokSays.setBounds(790, 322, 105, 26);
	panel.add(lblStokSays);
	
	JButton btnFiltrele = new JButton("FİLTRELE");
	btnFiltrele.setBackground(SystemColor.activeCaption);
	btnFiltrele.setFont(new Font("Tahoma", Font.BOLD, 20));
	btnFiltrele.setBounds(792, 399, 139, 69);
	panel.add(btnFiltrele);
	
	JButton btnSil = new JButton("SİL");
	btnSil.setFont(new Font("Tahoma", Font.BOLD, 30));
	btnSil.setBounds(379, 433, 380, 45);
	panel.add(btnSil);
	
	JButton btnAnaSayfa = new JButton("Ana Sayfa");
	btnAnaSayfa.setBackground(new Color(192, 192, 192));
	btnAnaSayfa.setFont(new Font("Tahoma", Font.PLAIN, 16));
	btnAnaSayfa.setBounds(790, 19, 141, 26);
	panel.add(btnAnaSayfa);
	
	btnAnaSayfa.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	frame.setVisible(false);
			MainPage.main(iD);
	    }
	});
	
	btnFiltrele.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e1) {
			deleteAllRows(model);

			String filtreÜrünAdı = textUrunAd.getText();
			String filtreFiyat1 = textFiyat1.getText();
			String filtreFiyat2 = textFiyat2.getText();
			String filtreStokAdet = textStokSayısı.getText();
			String filteSatışSayısı = textSatışSayısı.getText();
			String query1 = "SELECT urun_id,urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id = 20011000"
					+ " AND urun_isim LIKE '%"+filtreÜrünAdı+"%' ;";
			try {
				printTableFromQuery(model, query1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	
	
	btnGüncelle.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	try {
				findSelectedRowCallUpdate(iD, table);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    }
	});
	
	btnSil.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	try {
	    		int urunID = 0;
	    		for (int i = 0; i < table.getRowCount(); i++) {
	    			if(table.isRowSelected(i) == true) {
	    				urunID = (int)table.getValueAt(i, 0);
	    			}
	    		}
	        	CallableStatement cstm; 
	        	cstm = conn.prepareCall("{? = call deleteproduct(?)}");
	        	cstm.registerOutParameter(1, Types.INTEGER);
	        	cstm.setInt(2, urunID);
	        	int a = cstm.executeUpdate();
	        	System.out.print(a);
	        	String query = "SELECT urun_id,urun_isim, fiyat, satis_sayisi,stok FROM urun WHERE satici_id = " + iD +"";    
	            printTableFromQuery(model, query); //vakit varsa bakılacak
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    	
	    }
	});

	
	frame.setVisible(true);
}

	public static void printTableFromQuery(DefaultTableModel model, String query) throws SQLException {
		Statement s;
		s = conn.createStatement();
		s.executeQuery(query);
		
		ResultSet result = s.getResultSet();
		while(result.next()) {
			String Ürün_ismi = result.getString("urun_isim");
			float fiyat = result.getFloat("fiyat");
			int satış_sayısı = result.getInt("satis_sayisi");
			int stok = result.getInt("stok");
			int urun_id = result.getInt("urun_id");
			model.addRow(new Object[] {urun_id, Ürün_ismi, fiyat + " TL", satış_sayısı, stok});
		}
	}

	public static void deleteAllRows(DefaultTableModel model) {
		int row_count = model.getRowCount();
		for(int i = 0; i < row_count;i++) {
			model.removeRow(0);	
		}
	}

	public static void findSelectedRowCallUpdate(int iD, JTable table) throws SQLException {

		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.isRowSelected(i) == true) {
				iD = (int) table.getValueAt(i, 0);
		    	UpdateProduct.main(iD);  	    			
			}
		}
	}	
}