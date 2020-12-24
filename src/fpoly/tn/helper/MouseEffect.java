package fpoly.tn.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseEffect {

    static Point mouseP = null;

    public static void addMouseEffect(Component comp, Color backgroundIn, Color backgroundOut, Color foregroundIn, Color foregroundOut) {

        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                comp.setBackground(backgroundIn);
                comp.setForeground(foregroundIn);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comp.setBackground(backgroundOut);
                comp.setForeground(foregroundOut);
            }

        });
    }

    public static void MouseMovingForm(Component comp, Component frame) {

        comp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Determine how much the mouse moved since the initial click
                if(mouseP == null)
                    return;
                int xMoved = e.getX() - mouseP.x;
                int yMoved = e.getY() - mouseP.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }

        });
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseP = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseP = null;
            }

        });
    }
}
