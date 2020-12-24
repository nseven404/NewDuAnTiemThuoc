package gui.form.login;

import gui.form.main.frm_Main;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.dal.DALNhanVien;
import gui.form.message.DialogThongBao;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import gui.form.settingSql.frm_SettingSql;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.Timer;

public class frm_DangNhap extends javax.swing.JFrame {

    private final Preferences pref = Preferences.userNodeForPackage(frm_DangNhap.class);
    // khai báo biến
    private Point mousePosition = null;

    public frm_DangNhap(boolean dangXuat) {

        initComponents();
        setFormUI();

        loadDataForm();
    }

    private void loadDataForm() {

        String taikhoan = pref.get(Gobal.PrefKey.FormDangNhap.taikhoan, "");
        String matkhau = pref.get(Gobal.PrefKey.FormDangNhap.matkhau, "");
        boolean nhoDangNhap = pref.getBoolean(Gobal.PrefKey.FormDangNhap.nhodangnhap, false);

        if (nhoDangNhap) {
            txtTaiKhoan.setTextValue(taikhoan == null ? "": taikhoan);
            txtMatKhau.setTextValue(matkhau == null ? "": matkhau);
            
        } else {
            txtTaiKhoan.setTextValue("");
            txtMatKhau.setTextValue("");
        }

        chkLuuTạiKhoan.setSelected(nhoDangNhap);

    }

    private void DangNhap() {

        Timer timeDangNhap = new Timer(10, (ActionEvent e) -> {

            boolean ketNoiThanhCong = SqlHelper.ConnectionSQL();
            String maNv;
            String taikhoan;
            String matkhau;

            if (ketNoiThanhCong) {
                
                taikhoan = txtTaiKhoan.getTextValue();
                matkhau = txtMatKhau.getTextValue();

                maNv = kiemTraDangNhap(taikhoan, matkhau);

                if (!CheckValidation.checkStringTrong(maNv)) {
                    
                    if(DALNhanVien.LayTrangThaiLockNhanVien(maNv)){
                        ThongBao.taoThongBao(this, "Tài khoản của bạn bị khóa.", DialogThongBao.MessageIcon.WARNING);
                    }else{
                        luuThongTinUser();
                        moFormMain(maNv);
                    }
                    
                }

            } else {
                moFormCaiDatSQL();
            }

        });

        timeDangNhap.setRepeats(false);
        timeDangNhap.start();
    }

    private String kiemTraDangNhap(String taiKhoan, String pass) {

        // lấy thông tin nhân viên
        String maNhanVien = DALNhanVien.dangNhap(taiKhoan, pass);

        // nếu mã không null thì mở form main
        if (CheckValidation.checkStringTrong(maNhanVien)) {
            ThongBao.taoThongBao(this, "Tài khoản hoặc mật khẩu không chính xác", DialogThongBao.MessageIcon.WARNING);
        }

        return maNhanVien;
    }

    private void luuThongTinUser() {

        if (!chkLuuTạiKhoan.isSelected()) {
            pref.put(Gobal.PrefKey.FormDangNhap.taikhoan, "");
            pref.put(Gobal.PrefKey.FormDangNhap.matkhau, "");
        } else {
            pref.put(Gobal.PrefKey.FormDangNhap.taikhoan, txtTaiKhoan.getTextValue());
            pref.put(Gobal.PrefKey.FormDangNhap.matkhau, txtMatKhau.getTextValue());
        }
        pref.putBoolean(Gobal.PrefKey.FormDangNhap.nhodangnhap, chkLuuTạiKhoan.isSelected());

    }

    private void moFormMain(String maNv) {

        frm_Main frmMain = new frm_Main(maNv);    
        frmMain.setVisible(true);
        this.dispose();
    }

    private void moFormCaiDatSQL() {
        frm_SettingSql fst = new frm_SettingSql(this, true);
        fst.setVisible(true);
    }

    private void setFormUI() {

        // đặt màu nền form
        this.setBackground(new Color(0, 0, 0, 0));

        MouseEffect.addMouseEffect(bntThoat, new Color(255, 0, 51), Color.white, Color.white, new Color(102, 102, 102));
        MouseEffect.addMouseEffect(bntMiniSize, new Color(243, 241, 241), Color.white, Color.black, new Color(102, 102, 102));
        MouseEffect.addMouseEffect(bntSettingSql, new Color(243, 241, 241), Color.white, Color.black, Color.black);
    }

    private void Thoat() {
        boolean xacNhan = ThongBao.taoThongBaoXacNhan(this, "Bạn có muốn thoát", "", "", DialogThongBao.MessageIcon.QUESTION);
        if (xacNhan) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        khungform = new javax.swing.JPanel();
        khungTieuDe = new javax.swing.JPanel();
        bntThoat = new javax.swing.JLabel();
        bntMiniSize = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        bntSettingSql = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        chkLuuTạiKhoan = new javax.swing.JCheckBox();
        txtTaiKhoan = new com.shady.scontrols.STextField();
        txtMatKhau = new com.shady.scontrols.STextField();
        btnDangNhap = new com.shady.scontrols.SButton();
        lblversion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTextMatKhau = new javax.swing.JLabel();
        lblAnhNen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setUndecorated(true);
        setResizable(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        khungform.setBackground(new java.awt.Color(255, 255, 255));
        khungform.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        khungform.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        khungTieuDe.setOpaque(false);
        khungTieuDe.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                khungTieuDeMouseDragged(evt);
            }
        });
        khungTieuDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                khungTieuDeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                khungTieuDeMouseReleased(evt);
            }
        });
        khungTieuDe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bntThoat.setBackground(new java.awt.Color(255, 255, 255));
        bntThoat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bntThoat.setForeground(new java.awt.Color(102, 102, 102));
        bntThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bntThoat.setText("x");
        bntThoat.setToolTipText("Thoát");
        bntThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThoat.setName(""); // NOI18N
        bntThoat.setOpaque(true);
        bntThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntThoatMouseClicked(evt);
            }
        });
        khungTieuDe.add(bntThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 30, 30));

        bntMiniSize.setBackground(new java.awt.Color(255, 255, 255));
        bntMiniSize.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bntMiniSize.setForeground(new java.awt.Color(102, 102, 102));
        bntMiniSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bntMiniSize.setText("_");
        bntMiniSize.setToolTipText("Thu nhỏ");
        bntMiniSize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntMiniSize.setOpaque(true);
        bntMiniSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntMiniSizeMouseClicked(evt);
            }
        });
        khungTieuDe.add(bntMiniSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 30, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("VIETIT");
        khungTieuDe.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 24));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Giải pháp phần mềm IOT");
        khungTieuDe.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, 20));

        khungform.add(khungTieuDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 50));
        khungform.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 330, 10));

        bntSettingSql.setBackground(new java.awt.Color(255, 255, 255));
        bntSettingSql.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bntSettingSql.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_sqlSetting.png"))); // NOI18N
        bntSettingSql.setToolTipText("Cái đặt kết nối");
        bntSettingSql.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntSettingSql.setOpaque(true);
        bntSettingSql.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bntSettingSql.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntSettingSqlMouseClicked(evt);
            }
        });
        khungform.add(bntSettingSql, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 29, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ĐĂNG NHẬP");
        khungform.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 380, 30));

        chkLuuTạiKhoan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkLuuTạiKhoan.setForeground(new java.awt.Color(102, 102, 102));
        chkLuuTạiKhoan.setText("Nhớ tài khoản");
        chkLuuTạiKhoan.setOpaque(false);
        khungform.add(chkLuuTạiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 230, 20));

        txtTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTaiKhoan.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_user.png"))); // NOI18N
        txtTaiKhoan.sets_iconLeftPadding(10);
        txtTaiKhoan.sets_placeholder("Tài khoản...");
        txtTaiKhoan.sets_radius(20);
        txtTaiKhoan.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);
        txtTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaiKhoanActionPerformed(evt);
            }
        });
        khungform.add(txtTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 290, 40));

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMatKhau.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_password.png"))); // NOI18N
        txtMatKhau.sets_iconLeftPadding(10);
        txtMatKhau.sets_placeholder("Mật khẩu...");
        txtMatKhau.sets_radius(20);
        txtMatKhau.sets_textType(com.shady.scontrols.STextField.textTypeEnum.PASSWORD);
        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });
        khungform.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 290, 40));

        btnDangNhap.setBackground(new java.awt.Color(102, 102, 255));
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDangNhap.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangNhap.sets_ForeColor(new java.awt.Color(255, 255, 255));
        btnDangNhap.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        btnDangNhap.sets_Radius(20);
        btnDangNhap.sets_drawBorder(false);
        btnDangNhap.sets_hoverBackground(new java.awt.Color(51, 51, 255));
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        khungform.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 290, 40));

        lblversion.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblversion.setText("Nhóm G5");
        khungform.add(lblversion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 50, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tên đăng nhập");
        khungform.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 110, -1));

        lblTextMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTextMatKhau.setForeground(new java.awt.Color(102, 102, 102));
        lblTextMatKhau.setText("Mật khẩu");
        khungform.add(lblTextMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 190, -1));

        lblAnhNen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/backgoundLogin.jpg"))); // NOI18N
        khungform.add(lblAnhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 380, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(khungform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(khungform, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bntThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntThoatMouseClicked

        Thoat();
    }//GEN-LAST:event_bntThoatMouseClicked

    private void khungTieuDeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khungTieuDeMouseDragged
        Point currCoords = evt.getLocationOnScreen();
        this.setLocation(currCoords.x - mousePosition.x, currCoords.y - mousePosition.y);
    }//GEN-LAST:event_khungTieuDeMouseDragged

    private void khungTieuDeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khungTieuDeMousePressed
        mousePosition = evt.getPoint();
    }//GEN-LAST:event_khungTieuDeMousePressed

    private void khungTieuDeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khungTieuDeMouseReleased
        mousePosition = null;
    }//GEN-LAST:event_khungTieuDeMouseReleased

    private void bntMiniSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntMiniSizeMouseClicked

        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_bntMiniSizeMouseClicked

    private void bntSettingSqlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntSettingSqlMouseClicked
        moFormCaiDatSQL();
    }//GEN-LAST:event_bntSettingSqlMouseClicked

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        DangNhap();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void txtTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaiKhoanActionPerformed
        DangNhap();
    }//GEN-LAST:event_txtTaiKhoanActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        DangNhap();
    }//GEN-LAST:event_txtMatKhauActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frm_DangNhap(false).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bntMiniSize;
    private javax.swing.JLabel bntSettingSql;
    private javax.swing.JLabel bntThoat;
    private com.shady.scontrols.SButton btnDangNhap;
    private javax.swing.JCheckBox chkLuuTạiKhoan;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel khungTieuDe;
    private javax.swing.JPanel khungform;
    private javax.swing.JLabel lblAnhNen;
    private javax.swing.JLabel lblTextMatKhau;
    private javax.swing.JLabel lblversion;
    private com.shady.scontrols.STextField txtMatKhau;
    private com.shady.scontrols.STextField txtTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
