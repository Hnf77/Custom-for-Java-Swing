package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Custom_Jpanel extends JPanel {

    /**
     * @return the roundedTopLeft
     */
    public int getRoundedTopLeft() {
        return roundedTopLeft;
    }

    /**
     * @param roundedTopLeft the roundedTopLeft to set
     */
    public void setRoundedTopLeft(int roundedTopLeft) {
        this.roundedTopLeft = roundedTopLeft;
        repaint();
    }

    /**
     * @return the roundedTopRight
     */
    public int getRoundedTopRight() {
        return roundedTopRight;
    }

    /**
     * @param roundedTopRight the roundedTopRight to set
     */
    public void setRoundedTopRight(int roundedTopRight) {
        this.roundedTopRight = roundedTopRight;
        repaint();
    }

    /**
     * @return the roundedBottomLeft
     */
    public int getRoundedBottomLeft() {
        return roundedBottomLeft;
    }

    /**
     * @param roundedBottomLeft the roundedBottomLeft to set
     */
    public void setRoundedBottomLeft(int roundedBottomLeft) {
        this.roundedBottomLeft = roundedBottomLeft;
        repaint();
    }

    /**
     * @return the roundedBottomRight
     */
    public int getRoundedBottomRight() {
        return roundedBottomRight;
    }

    /**
     * @param roundedBottomRight the roundedBottomRight to set
     */
    public void setRoundedBottomRight(int roundedBottomRight) {
        this.roundedBottomRight = roundedBottomRight;
        repaint();
    }

    private int roundedTopLeft = 0;
    private int roundedTopRight = 0;
    private int roundedBottomLeft = 0;
    private int roundedBottomRight = 0;

    public Custom_Jpanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        Area area = new Area(createTopLeftRounded());
        if(roundedTopLeft > 0){
            area.intersect(new Area(createTopLeftRounded()));
        }
        if(roundedTopRight > 0){
            area.intersect(new Area(createTopRightRounded()));
        }
        if(roundedBottomLeft > 0){
            area.intersect(new Area(createBottomLeftRounded()));
        }
        if(roundedBottomRight > 0){
            area.intersect(new Area(createBottomRightRounded()));
        }
        g2.fill(area);
        g2.dispose();
    }
        
    private Shape createTopLeftRounded() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedTopLeft);
        int roundY = Math.min(height, roundedTopLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }
    
    private Shape createTopRightRounded() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedTopRight);
        int roundY = Math.min(height, roundedTopRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }
    
    private Shape createBottomLeftRounded() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedBottomLeft);
        int roundY = Math.min(height, roundedBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }
    
    private Shape createBottomRightRounded() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedBottomRight);
        int roundY = Math.min(height, roundedBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

}
