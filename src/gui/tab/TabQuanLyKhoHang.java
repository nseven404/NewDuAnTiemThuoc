package gui.tab;

import gui.tab.subtab.kho.SubTabDanhSachThuoc;
import gui.tab.subtab.kho.SubTabDonViTinh;
import gui.tab.subtab.kho.SubTabLoThuoc;
import gui.tab.subtab.kho.SubTabNhomThuoc;
public class TabQuanLyKhoHang extends javax.swing.JPanel {


    public TabQuanLyKhoHang() {
        initComponents();
        
        initTabbed();
    }
    
    private void initTabbed(){
        
        tabbedKho.addHeader("danhsachthuoc", "Danh Sách Thuốc", 150, null);
        tabbedKho.addHeader("lothuoc", "Lô Thuốc", 110, null);
        tabbedKho.addHeader("donvitinh", "Đơn Vị Tính", 150, null);
        tabbedKho.addHeader("loaithuoc", "Loại Thuốc", 110, null);
        
        tabbedKho.addTab(new SubTabDanhSachThuoc(), "danhsachthuoc");
        tabbedKho.addTab(new SubTabDonViTinh(), "donvitinh");
        tabbedKho.addTab(new SubTabLoThuoc(), "lothuoc");
        tabbedKho.addTab(new SubTabNhomThuoc(), "loaithuoc");
        tabbedKho.setSelectTab("danhsachthuoc");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabContains = new javax.swing.JPanel();
        tabbedKho = new com.shady.scontrols.STabbed();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        tabContains.setBackground(new java.awt.Color(248, 248, 248));
        tabContains.setLayout(new java.awt.BorderLayout());

        tabbedKho.setHeaderHeight(40);
        tabbedKho.setHeaderInsets(new java.awt.Insets(0, 10, 0, 0));
        tabbedKho.setHeaderItemPosition(com.shady.scontrols.STabbed.HeaderAlignment.LEFT);
        tabbedKho.setItemBorderSize(3);
        tabbedKho.setItemFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedKho.setItemForeground(new java.awt.Color(102, 102, 102));
        tabbedKho.setItemHoverBorderColor(new java.awt.Color(248, 210, 175));
        tabbedKho.setItemHoverFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabbedKho.setItemHoverForeground(new java.awt.Color(102, 102, 102));
        tabbedKho.setItemSelectBorderColor(new java.awt.Color(255, 188, 126));
        tabbedKho.setItemSelectFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tabContains.add(tabbedKho, java.awt.BorderLayout.CENTER);

        add(tabContains, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/kho.png"))); // NOI18N
        jLabel1.setText("Quản Lý Kho");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(713, Short.MAX_VALUE))
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
    private javax.swing.JPanel tabContains;
    private com.shady.scontrols.STabbed tabbedKho;
    // End of variables declaration//GEN-END:variables
}
