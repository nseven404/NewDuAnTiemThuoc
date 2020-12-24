/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.helper;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class tableUI {

    // override header table
    public static TableCellRenderer setHeaderStyle() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable arg0, Object ob,
                    boolean arg2, boolean arg3, int arg4, int arg5) {
                JLabel t = new JLabel(ob.toString());
                t.setOpaque(false);
                t.setForeground(Color.WHITE);
                t.setFont(new java.awt.Font("Tahoma", 1, 12));

                return t;
            }
        };
    }

    // overrider cell Renderer
    public static class TableCellWithImage extends DefaultTableCellRenderer {

        private Color selectBackgroundcolor;

        public Color getSelectBackgroundcolor() {
            return selectBackgroundcolor;
        }

        public void setSelectBackgroundcolor(Color selectBackgroundcolor) {
            this.selectBackgroundcolor = selectBackgroundcolor;
        }

        public TableCellWithImage() {

            selectBackgroundcolor = Color.white;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(noFocusBorder);

            if (isSelected) {
                comp.setBackground(selectBackgroundcolor);
            } else {
                comp.setBackground(Color.white);
            }

            return comp;
        }

        @Override
        protected void setValue(Object value) {
            if (value instanceof DataWithIcon) {

                try {
                    DataWithIcon d = (DataWithIcon) value;
                    Object dataValue = d.getData();

                    setText(dataValue == null ? "" : dataValue.toString());
                    setIcon(d.getIcon());
                    setHorizontalTextPosition(SwingConstants.RIGHT);
                    setVerticalTextPosition(SwingConstants.CENTER);
                    setHorizontalAlignment(SwingConstants.LEFT);
                    setVerticalAlignment(SwingConstants.CENTER);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            } else {
                super.setValue(value);
            }
        }

    }

}
