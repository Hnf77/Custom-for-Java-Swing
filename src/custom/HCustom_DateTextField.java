package custom;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class HCustom_DateTextField extends JFormattedTextField {

    /**
     * @return the labelText
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * @param labelText the labelText to set
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    /**
     * @return the lineBaseColor
     */
    public Color getLineBaseColor() {
        return lineBaseColor;
    }

    /**
     * @param lineBaseColor the lineBaseColor to set
     */
    public void setLineBaseColor(Color lineBaseColor) {
        this.lineBaseColor = lineBaseColor;
    }

    /**
     * @return the labelTextColor
     */
    public Color getLabelTextColor() {
        return labelTextColor;
    }

    /**
     * @param labelTextColor the labelTextColor to set
     */
    public void setLabelTextColor(Color labelTextColor) {
        this.labelTextColor = labelTextColor;
    }

    /**
     * @return the lineColor
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return the dateFormatter
     */
    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    /**
     * @param dateFormatter the dateFormatter to set
     */
    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    private static final long serialVersionUID = 1L;

    private final Animator animator;
    private boolean animateHintText = true;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private String labelText = "Label";
    private Color lineBaseColor = new Color(150, 150, 150);
    private Color labelTextColor = new Color(150, 150, 150);
    private Color lineColor = new Color(3, 155, 216);
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public HCustom_DateTextField() {
        super();

        try {
            MaskFormatter maskFormatter = new MaskFormatter("####/##/##");
            maskFormatter.setPlaceholderCharacter('_');
            setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(maskFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setBorder(new EmptyBorder(20, 3, 10, 3));
        setSelectionColor(new Color(76, 204, 255));

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHintText = getText().equals("");
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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                showing(true);
            }
        });
    }

    private void showing(boolean action) {
        if (animator != null && animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        if (animator != null) {
            animator.setStartFraction(1f - location);
            show = action;
            location = 1f - location;
            animator.start();
        }
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if (mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(lineBaseColor);
        }
        g2.fillRect(2, height - 1, width - 4, 1);
        createHintText(g2);
        createLineStyle(g2);
        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(labelTextColor);
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size;
        if (animateHintText) {
            if (show) {
                size = 18 * (1 - location);
            } else {
                size = 18 * location;
            }
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
            double size;
            if (show) {
                size = width * (1 - location);
            } else {
                size = width * location;
            }
            double x = (width - size) / 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

    @Override
    public void setText(String string) {
        if (!getText().equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }

    public Date getDate() {
        try {
            return dateFormatter.parse(getText());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDate(Date date) {
        setText(dateFormatter.format(date));
    }
}
