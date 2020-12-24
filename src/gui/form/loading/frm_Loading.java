package gui.form.loading;

import com.alee.managers.UIManagers;
import fpoly.tn.dal.DALNhanVien;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import gui.form.login.frm_DangNhap;
import gui.form.main.frm_Main;
import gui.form.message.DialogThongBao;
import gui.form.settingSql.frm_SettingSql;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class frm_Loading extends javax.swing.JFrame {

    private final Preferences pref = Preferences.userNodeForPackage(frm_DangNhap.class);

    public frm_Loading() {
        setAppUI();
        initComponents();

        XuLyThongTin();

    }

    // hàm xử lý
    private void setAppUI() {

        UIDefaults UI = UIManager.getDefaults();
        // popup
        UI.put("PopupMenuUI", "com.alee.laf.menu.WebPopupMenuUI");
        UI.put("PopupUI", "com.alee.extended.window.WebPopupUI");
        UI.put("MenuUI", "com.alee.laf.menu.WebMenuUI");
        UI.put("MenuItemUI", "com.alee.laf.menu.WebMenuItemUI");

        //combobox
        UI.put("ComboBoxUI", "com.alee.laf.combobox.WebComboBoxUI");

        //scrollbar
        UI.put("ScrollPaneUI", "com.alee.laf.scroll.WebScrollPaneUI");
        UI.put("ViewportUI", "com.alee.laf.viewport.WebViewportUI");
        UI.put("ScrollBarUI", "com.alee.laf.scroll.WebScrollBarUI");
        UI.put("TreeUI", "com.alee.laf.tree.WebTreeUI");

        // checkbox
        UI.put("CheckBoxUI", "com.alee.laf.checkbox.WebCheckBoxUI");
        //radio button
        UI.put("RadioButtonUI", "com.alee.laf.radiobutton.WebRadioButtonUI");
        
    }

    private void XuLyThongTin() {

        Timer timeMoformDangNhap = new Timer(1000, (ActionEvent e) -> {
            if (kiemTraKetNoiSql()) {
                DangNhapTuDong();
            } else {
                
                boolean xacNhan = ThongBao.taoThongBaoXacNhan(this, "Không thể kết nối cơ sở dữ liệu, bạn muốn cài đặt kết nối dữ liệu?", DialogThongBao.MessageIcon.QUESTION);
                if(xacNhan)
                    moFormCaiDatSQL();
                else{
                    System.exit(0);
                }
                
                // đệ quy
                XuLyThongTin();
            }
        });
        timeMoformDangNhap.setRepeats(false);
        timeMoformDangNhap.start();

    }

    private void moFormCaiDatSQL() {
        frm_SettingSql fst = new frm_SettingSql(this, true);
        fst.setVisible(true);
    }

    private boolean kiemTraKetNoiSql() {
        loadSettingSql();
        return SqlHelper.ConnectionSQL();
    }
    
    private void loadSettingSql(){
        Preferences pref_Sql = Preferences.userNodeForPackage(frm_SettingSql.class);
        
        SqlHelper.DB_DatabaseName = pref_Sql.get(Gobal.PrefKey.FormSettingSql.DatabaseName, "");
        SqlHelper.DB_ID = pref_Sql.get(Gobal.PrefKey.FormSettingSql.ID, "");
        SqlHelper.DB_PASS = pref_Sql.get(Gobal.PrefKey.FormSettingSql.PASS, "");
        SqlHelper.DB_TCPPort = pref_Sql.get(Gobal.PrefKey.FormSettingSql.TCPPort, "");
    }

    private void moFormDangNhap() {
        frm_DangNhap frmDN = new frm_DangNhap(false);
        frmDN.setVisible(true);
        this.dispose();
    }

    private void DangNhapTuDong() {

        boolean auto = pref.getBoolean(Gobal.PrefKey.FormDangNhap.nhodangnhap, false);
        if (auto) {
            DangNhap(auto);
        } else {
            moFormDangNhap();
        }

    }

    private void DangNhap(boolean auto) {

        Timer timeDangNhap = new Timer(10, (ActionEvent e) -> {

            String maNv;
            String taikhoan;
            String matkhau;

            if (auto) {
                taikhoan = pref.get(Gobal.PrefKey.FormDangNhap.taikhoan, "null");
                matkhau = pref.get(Gobal.PrefKey.FormDangNhap.matkhau, "null");
                maNv = kiemTraDangNhap(taikhoan, matkhau);

                if (CheckValidation.checkStringTrong(maNv)) {
                    moFormDangNhap();
                }else{
                    boolean lock = DALNhanVien.LayTrangThaiLockNhanVien(maNv);
                    if(!lock)
                        moFormMain(maNv);
                    else{
                        ThongBao.taoThongBao(null, "Tài khoản ", maNv," bị khóa!", DialogThongBao.MessageIcon.QUESTION);
                        moFormDangNhap();
                    }
                }
            }

        });

        timeDangNhap.setRepeats(false);
        timeDangNhap.start();
    }
    
    private void moFormMain(String maNv) {

        frm_Main frmMain = new frm_Main(maNv);
        frmMain.setVisible(true);
        this.dispose();
    }

    private String kiemTraDangNhap(String taiKhoan, String pass) {
        return DALNhanVien.dangNhap(taiKhoan, pass);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đang xử lý . . .");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/loaddingAnim.gif"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 174, 239));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VIETIT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(frm_Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            UIManagers.initialize();
            new frm_Loading().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
