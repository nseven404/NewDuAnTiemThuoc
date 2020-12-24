/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tab;

import java.awt.Color;
import javax.swing.JPanel;


public class TabCaiDat extends javax.swing.JPanel {

 
    public TabCaiDat() {
        initComponents();
        tabbed.addHeader("setting1", "Setting 1", 90, null);
        tabbed.addHeader("setting2", "Setting 2", 90, null);
        tabbed.addHeader("setting3", "Setting 3", 90, null);
        tabbed.addHeader("setting4", "Setting 4", 90, null);
        tabbed.addHeader("setting5", "Setting 5", 90, null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbed = new com.shady.scontrols.STabbed();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        tabbed.setBackground(new java.awt.Color(247, 247, 247));
        tabbed.setHeaderHeight(50);
        tabbed.setItemBorderSize(4);
        tabbed.setItemHoverBorderColor(new java.awt.Color(204, 204, 204));
        tabbed.setItemHoverFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbed.setItemSelectBorderColor(new java.awt.Color(255, 153, 153));
        tabbed.setItemSelectFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        add(tabbed, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/caidat.png"))); // NOI18N
        jLabel1.setText("Cài Đặt");
        jLabel1.setIconTextGap(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(754, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.shady.scontrols.STabbed tabbed;
    // End of variables declaration//GEN-END:variables
}
