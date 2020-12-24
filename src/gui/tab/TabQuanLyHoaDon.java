
package gui.tab;

import gui.tab.subtab.hoadon.SubTabHoaDonNhap;


public class TabQuanLyHoaDon extends javax.swing.JPanel {

    String maNv;
 
    public TabQuanLyHoaDon(String maNv) {
        initComponents();
        
        this.maNv = maNv;
        
        initTabbed();
    }
    
    private void initTabbed(){
        
        tabbedHoaDon.addHeader("HDnhap", "Hóa Đơn Nhập", 150, null);
        tabbedHoaDon.addHeader("HDxuat", "Hóa Đơn Xuất", 150, null);
        tabbedHoaDon.addHeader("Nhà Cung Cấp", "", 90, null);
        tabbedHoaDon.addHeader("Nhà Sản Xuất", "", 90, null);
        
        SubTabHoaDonNhap tabHDN = new SubTabHoaDonNhap(maNv);
        tabbedHoaDon.addTab(tabHDN, "HDnhap");
        
        tabbedHoaDon.setSelectTab("HDnhap");
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabbedHoaDon = new com.shady.scontrols.STabbed();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/hoadon.png"))); // NOI18N
        jLabel1.setText("Quản Lý Hóa Đơn");
        jLabel1.setIconTextGap(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(902, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tabbedHoaDon.setHeaderHeight(40);
        tabbedHoaDon.setHeaderInsets(new java.awt.Insets(0, 10, 0, 0));
        tabbedHoaDon.setHeaderItemPosition(com.shady.scontrols.STabbed.HeaderAlignment.LEFT);
        tabbedHoaDon.setItemBorderSize(3);
        tabbedHoaDon.setItemFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedHoaDon.setItemForeground(new java.awt.Color(102, 102, 102));
        tabbedHoaDon.setItemHoverBorderColor(new java.awt.Color(248, 210, 175));
        tabbedHoaDon.setItemHoverFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedHoaDon.setItemHoverForeground(new java.awt.Color(102, 102, 102));
        tabbedHoaDon.setItemSelectBorderColor(new java.awt.Color(255, 188, 126));
        tabbedHoaDon.setItemSelectFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        add(tabbedHoaDon, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.shady.scontrols.STabbed tabbedHoaDon;
    // End of variables declaration//GEN-END:variables
}
