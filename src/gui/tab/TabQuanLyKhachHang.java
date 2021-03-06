package gui.tab;

import gui.tab.subtab.khachhang.SubTabKhachHang;

public class TabQuanLyKhachHang extends javax.swing.JPanel {


    public TabQuanLyKhachHang() {
        initComponents();
        
        tabbedKhachHang.addHeader("danhsachKH", "Danh Sách KH", 150, null);
        
        tabbedKhachHang.addTab(new SubTabKhachHang(), "danhsachKH");
        tabbedKhachHang.setSelectTab("danhsachKH");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedKhachHang = new com.shady.scontrols.STabbed();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        tabbedKhachHang.setHeaderHeight(40);
        tabbedKhachHang.setHeaderInsets(new java.awt.Insets(0, 10, 0, 0));
        tabbedKhachHang.setHeaderItemPosition(com.shady.scontrols.STabbed.HeaderAlignment.LEFT);
        tabbedKhachHang.setItemBorderSize(3);
        tabbedKhachHang.setItemFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedKhachHang.setItemForeground(new java.awt.Color(102, 102, 102));
        tabbedKhachHang.setItemHoverBorderColor(new java.awt.Color(248, 210, 175));
        tabbedKhachHang.setItemHoverFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedKhachHang.setItemHoverForeground(new java.awt.Color(102, 102, 102));
        tabbedKhachHang.setItemSelectBorderColor(new java.awt.Color(255, 188, 126));
        tabbedKhachHang.setItemSelectFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        add(tabbedKhachHang, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/khachhang.png"))); // NOI18N
        jLabel1.setText("Quản Lý Khách Hàng");
        jLabel1.setIconTextGap(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(760, Short.MAX_VALUE))
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
    private com.shady.scontrols.STabbed tabbedKhachHang;
    // End of variables declaration//GEN-END:variables
}
