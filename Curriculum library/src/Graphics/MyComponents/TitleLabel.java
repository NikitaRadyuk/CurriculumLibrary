package Graphics.MyComponents;

import javax.swing.*;
import java.awt.*;
/**
 *
 */
public class TitleLabel extends JLabel {
    public TitleLabel (String text,int styleFont, int fontSize){
        setText(text);
        Font font = new Font("Segoe UI", styleFont, fontSize);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
