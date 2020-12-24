package gui.form.donvitinh;

import fpoly.tn.dal.DALDonViTinh;
import fpoly.tn.dto.DonViTinh;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.ThongBao;
import gui.form.message.DialogThongBao;
import java.awt.Color;

public class Frm_DonViTinh  extends javax.swing.JDialog {

    DonViTinh donViTinh = new DonViTinh();
    
    public Frm_DonViTinh(java.awt.Frame parent,String maDVT) {
        super(parent, true);
        initComponents();
        initForm();
        
        donViTinh.setMaDVT(maDVT);
        
        loadThongTinForm();
    }
    
    private void loadThongTinForm(){
        
        boolean themMoi = CheckValidation.checkStringTrong(donViTinh.getMaDVT());
        
        if(!themMoi){
            donViTinh = DALDonViTinh.loadThongTinDonViTinh(donViTinh.getMaDVT());
            txtMaDVT.setTextValue(donViTinh.getMaDVT());
            txtTenDVT.setTextValue(donViTinh.getTenDVT());
            txtGhiChu.setText(donViTinh.getGhiChu());
        }
        txtMaDVT.setEnabled(themMoi);
    }
    
    private void initForm(){
        setBackground(new Color(0, 0, 0, 0));
        MouseEffect.MouseMovingForm(panelTuaDe, this);
    }
    
    private void clickLuu(){
        boolean themMoi = CheckValidation.checkStringTrong(donViTinh.getMaDVT());
        boolean thanhCong;
        
        if(!checkValidation(themMoi)){
            return;
        }
        
        
        
        if(themMoi){
            thanhCong = themMoi();
        }else{
            thanhCong = suaThongTin();
        }
        
        if(thanhCong){
            ThongBao.taoThongBao(null,"Lưu thành công" , DialogThongBao.MessageIcon.CHECK);
            txtMaDVT.setEnabled(true);
        }else{
            ThongBao.taoThongBao(null,"Lỗi khi lưu" , DialogThongBao.MessageIcon.WARNING);
        }
    }
    
    private boolean themMoi(){
        donViTinh.setMaDVT(txtMaDVT.getTextValue());
        donViTinh.setTenDVT(txtTenDVT.getTextValue());
        donViTinh.setGhiChu(txtGhiChu.getText());
        return DALDonViTinh.themMoiDonViTinh(donViTinh);
    }
    
    private boolean suaThongTin(){
        donViTinh.setTenDVT(txtTenDVT.getTextValue());
        donViTinh.setGhiChu(txtGhiChu.getText());
        return DALDonViTinh.suaThongTinDonViTinh(donViTinh);
    }
    
    private boolean checkValidation(boolean themMoi){
        String maDVT = txtMaDVT.getTextValue();
        String tenDVT = txtTenDVT.getTextValue();
        
        if(CheckValidation.checkStringTrong(maDVT)){
            ThongBao.taoThongBao(null, "","Mã Đơn Vị Tính","không được để trống", DialogThongBao.MessageIcon.WARNING);
            return false;
        }else if(CheckValidation.checkStringTrong(tenDVT)){
            ThongBao.taoThongBao(null, "","Tên Đơn Vị Tính","không được để trống", DialogThongBao.MessageIcon.WARNING);
            return false;
        }else if(themMoi)
            if(DALDonViTinh.kiemTraMaDVTTonTai(maDVT)){
            ThongBao.taoThongBao(null, "Mã",maDVT,"đã sử dụng, nhập mã mới", DialogThongBao.MessageIcon.WARNING);
            return false;
        }
        
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel1 = new com.shady.scontrols.SPanel();
        panelTuaDe = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sButton3 = new com.shady.scontrols.SButton();
        sButton4 = new com.shady.scontrols.SButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        lbMaLo = new javax.swing.JLabel();
        txtMaDVT = new com.shady.scontrols.STextField();
        lbTenThuoc = new javax.swing.JLabel();
        txtTenDVT = new com.shady.scontrols.STextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_dropShadow(true);
        sPanel1.sets_shadowSize(7);

        panelTuaDe.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/lothuoc.png"))); // NOI18N
        jLabel3.setText("ĐƠN VỊ TÍNH");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("X");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuaDeLayout = new javax.swing.GroupLayout(panelTuaDe);
        panelTuaDe.setLayout(panelTuaDeLayout);
        panelTuaDeLayout.setHorizontalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTuaDeLayout.setVerticalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTuaDeLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        sButton3.setBackground(new java.awt.Color(0, 204, 0));
        sButton3.setText("Lưu");
        sButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton3.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton3.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton3.sets_Radius(15);
        sButton3.sets_drawBorder(false);
        sButton3.sets_hoverBackground(new java.awt.Color(1, 196, 1));
        sButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton3ActionPerformed(evt);
            }
        });

        sButton4.setBackground(new java.awt.Color(255, 153, 153));
        sButton4.setText("Thoát");
        sButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton4.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton4.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton4.sets_Radius(15);
        sButton4.sets_drawBorder(false);
        sButton4.sets_hoverBackground(new java.awt.Color(243, 152, 152));
        sButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton4ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Ghi Chú");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        lbMaLo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbMaLo.setForeground(new java.awt.Color(102, 102, 102));
        lbMaLo.setText("Mã");

        txtMaDVT.sets_maxTextLength(6);
        txtMaDVT.sets_placeholder("Mã...");
        txtMaDVT.sets_radius(15);

        lbTenThuoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTenThuoc.setForeground(new java.awt.Color(102, 102, 102));
        lbTenThuoc.setText("Tên");

        txtTenDVT.sets_placeholder("Tên ĐVT...");
        txtTenDVT.sets_radius(15);

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(sButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbMaLo)
                    .addComponent(txtMaDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTenThuoc)
                    .addComponent(txtTenDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbMaLo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTenThuoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_sButton4ActionPerformed

    private void sButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton3ActionPerformed
        clickLuu();
    }//GEN-LAST:event_sButton3ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_DonViTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_DonViTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_DonViTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_DonViTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Frm_DonViTinh(null,"").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbMaLo;
    private javax.swing.JLabel lbTenThuoc;
    private javax.swing.JPanel panelTuaDe;
    private com.shady.scontrols.SButton sButton3;
    private com.shady.scontrols.SButton sButton4;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtMaDVT;
    private com.shady.scontrols.STextField txtTenDVT;
    // End of variables declaration//GEN-END:variables
}
