package hobipazari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

public class MarketPage {
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	public static String Category[] = {"A","B","C","D"};
	private static JTextField textField_3;
	private static JTextField textField_4;
	
	public static void main(int iD) {
	
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

	model.addColumn("Ürün adı");
	model.addColumn("Kategori");
	model.addColumn("Fiyat");
	model.addColumn("Satıcı adı");
	model.addColumn("Stok adedi");

	model.addRow(new Object[] {1, "Ahmet", "Tıpçı", "Türkiye", "ahmet.tipci@example.com"});
	model.addRow(new Object[] {2, "Mustafa", "Kemal", "Türkiye", "mustafa.kemal@example.com"});
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
	JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setBounds(0, 17, 759, 416);
	panel.add(scrollPane);
	
	JButton btnSatnAl = new JButton("SATIN AL");
	btnSatnAl.setBounds(0, 433, 759, 45);
	btnSatnAl.setFont(new Font("Tahoma", Font.BOLD, 30));
	btnSatnAl.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        // Butona tıklandığında gerçekleşecek işlemleri uygulayın.
	    }
	});

	panel.add(btnSatnAl);
	
	frame.getContentPane().add(panel);
	
	textField = new JTextField();
	textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textField.setBounds(790, 36, 141, 26);
	panel.add(textField);
	textField.setColumns(10);
	
	JLabel lblNewLabel = new JLabel("Ürün ismi");
	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblNewLabel.setBounds(790, 10, 75, 26);
	panel.add(lblNewLabel);
	
	JLabel lblFiyatAral = new JLabel("Fiyat");
	lblFiyatAral.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblFiyatAral.setBounds(790, 88, 105, 26);
	panel.add(lblFiyatAral);
	
	textField_1 = new JTextField();
	textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textField_1.setColumns(10);
	textField_1.setBounds(790, 114, 56, 26);
	panel.add(textField_1);
	
	textField_2 = new JTextField();
	textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textField_2.setColumns(10);
	textField_2.setBounds(875, 114, 56, 26);
	panel.add(textField_2);
	
	JLabel lblNewLabel_1 = new JLabel("-");
	lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
	lblNewLabel_1.setBounds(856, 111, 24, 26);
	panel.add(lblNewLabel_1);
	
	JComboBox comboBox = new JComboBox(Category);
	comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
	comboBox.setBounds(790, 192, 141, 26);
	panel.add(comboBox);
	
	JLabel lblNewLabel_2 = new JLabel("Kategori");
	lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblNewLabel_2.setBounds(790, 166, 90, 21);
	panel.add(lblNewLabel_2);
	
	textField_3 = new JTextField();
	textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textField_3.setColumns(10);
	textField_3.setBounds(790, 270, 141, 26);
	panel.add(textField_3);
	
	JLabel lblSatcAd = new JLabel("Satıcı Adı");
	lblSatcAd.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblSatcAd.setBounds(790, 244, 75, 26);
	panel.add(lblSatcAd);
	
	textField_4 = new JTextField();
	textField_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
	textField_4.setColumns(10);
	textField_4.setBounds(790, 348, 141, 26);
	panel.add(textField_4);
	
	JLabel lblStokSays = new JLabel("Stok Sayısı");
	lblStokSays.setFont(new Font("Tahoma", Font.PLAIN, 16));
	lblStokSays.setBounds(790, 322, 105, 26);
	panel.add(lblStokSays);
	
	JButton btnNewButton = new JButton("FİLTRELE");
	btnNewButton.setBackground(SystemColor.activeCaption);
	btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
	btnNewButton.setBounds(792, 399, 139, 69);
	panel.add(btnNewButton);
	
	frame.setVisible(true);
}	
}
