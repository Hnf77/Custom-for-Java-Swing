package custom;

import org.jdesktop.animation.timing.Animator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Custom_Jtextfield extends JTextField {

    private final Animator animator;
    private boolean animateHint = true;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private String labelText = "Label";
    private Color lineColor = new Color(3, 155, 216);

    public Custom_Jtextfield() {
        setBorder(new EmptyBorder(20, 3, 10, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
                repaint();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                showing(true);
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHint = getText().equals("");
            }

            @Override
            public void timingEvent(float fraction) {
                location = fraction;
                repaint();
            }
        };
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    private void showing(boolean action) {
        if (animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
        show = action;
        location = 1f - location;
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);

        int width = getWidth();
        int height = getHeight();

        if (mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(new Color(150, 150, 150));
        }

        int lineY = height - 1;
        g2.fillRect(2, lineY, width - 4, 1);

        createHintText(g2);
        createLineStyle(g2);

        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size;

        if (animateHint) {
            size = 18 * (show ? (1 - location) : location);
        } else {
            size = 18;
        }

        g2.drawString(labelText, in.right, (int) (in.top + textY + ft.getAscent() - size));
    }

    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size = width * (show ? (1 - location) : location);
            double x = (width - size) / 2;

            // Use RoundRectangle2D to create rounded corners
            RoundRectangle2D roundRect = new RoundRectangle2D.Double(x + 2, height - 2, size, 2, 5, 5);
            g2.fill(roundRect);
        }
    }

    @Override
    public void setText(String t) {
        if (!getText().equals(t)) {
            showing(t.equals(""));
        }
        super.setText(t);
    }

}
