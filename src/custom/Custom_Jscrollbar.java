package custom;

import javax.swing.*;
import java.awt.*;

public class Custom_Jscrollbar extends JScrollBar{
    
    public Custom_Jscrollbar(){
        setUI(new SourceCode_Custom_Jscrollbar());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(48, 144, 216));
        setBackground(Color.WHITE);
        
    }
    
}
