package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Hugo
 */

public class BotaoAcoes extends JButton {

    private boolean click;

    public BotaoAcoes() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                click = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                click = false;
            }
        });
    }
}