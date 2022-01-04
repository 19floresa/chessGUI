import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyPanel extends JLayeredPane {
    //Color colour;
    MyPanel() {
        //colour = color;
        this.setPreferredSize(new Dimension(800, 800));
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i % 2 == 0 && j % 2 == 1) {
                    g2.setPaint(Color.MAGENTA);
                    g2.fillRect(j * 100, i * 100, 100, 100);
                    continue;
                }

                if (i % 2 == 1 && j % 2 == 0) {
                    g2.setPaint(Color.MAGENTA);
                    g2.fillRect(j * 100, i * 100, 100, 100);
                    continue;
                }


                g2.setPaint(Color.WHITE);
                g2.fillRect(j * 100, i * 100, 100, 100);
            }
        }
    }

    public void add(ImageIcon image) {
    }


}
