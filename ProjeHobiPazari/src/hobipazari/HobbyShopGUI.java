package hobipazari;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class HobbyShopGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Hobby Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 7));

        JButton loginButton = new JButton("Giriş Yap");
        JButton signUpButton = new JButton("Kaydol");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	LoginPage.main(null);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					SignUpPage.main(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        panel.add(loginButton);
        panel.add(signUpButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}