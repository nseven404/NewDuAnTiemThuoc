package gui.tab.subtab.nhanvien;

import com.shady.event.PagiEvent;
import com.shady.event.PaginationEvent;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALNhanVien;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.message.DialogThongBao;
import gui.form.nhanvien.frm_thongtinnhanvien;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SubTabDanhSachNhanVien extends javax.swing.JPanel {

    final int SoHangCuaTrang = 10;

    String maNvDangNhap;

    public SubTabDanhSachNhanVien(String maNvDangNhap) {
        initComponents();

        this.maNvDangNhap = maNvDangNhap;
        
        initTableNhanVien();
        initChiaTrang();
        loadDataForm();

    }
    
    private void initChiaTrang(){
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            timKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadDataForm() {
        timKiem(1, SoHangCuaTrang);
    }

    private void timKiem(int SoTrang, int SoHangCuaTrang) {
        laySoTrangTimKiem(SoHangCuaTrang);
        loadDataTimKiem(SoTrang, SoHangCuaTrang);
    }

    private void initTableNhanVien() {

        tableDSNhanVien.addColum("Họ Và Tên", 200);
        tableDSNhanVien.addColum("Giới tính", 70);
        tableDSNhanVien.addColum("SĐT", 150);
        tableDSNhanVien.addColum("Địa Chỉ", 250);
        tableDSNhanVien.addColum("Chức Vụ", 100);
        tableDSNhanVien.addColum("Thao Tác", 0);

    }

    private void loadItemTable(String MaNv, String Ten, boolean GioiTinh, String SDT, String DiaChi, boolean lock, String ChucVu) {
        TableWebItem item = new TableWebItem();

        item.setId(MaNv);

        JLabel lbTen = new JLabel(Ten);
        JLabel lbGioiTinh = new JLabel(xuLyHinhAnh.LayAnhTuSource(item, GioiTinh ? "/fpoly/icon/nam.png" : "/fpoly/icon/nu.png"));

        JLabel lbSDT = new JLabel(SDT);
        JLabel lbDiaChi = new JLabel(DiaChi);
        JLabel lbChucVu = new JLabel(ChucVu);

        lbTen.setHorizontalAlignment(SwingConstants.CENTER);
        lbSDT.setHorizontalAlignment(SwingConstants.CENTER);
        lbChucVu.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Tahoma", 1, 12);
        lbTen.setFont(font);

        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setOpaque(false);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSNhanVien.getItemHeight() - 20) / 2);
        fLayout.setHgap(10);

        panelThaoTac.setLayout(fLayout);

        JLabel lbLock = new JLabel(xuLyHinhAnh.LayAnhTuSource(this, lock ? "/fpoly/icon/lockuser.png" : "/fpoly/icon/unlockcuser.png"));
        JLabel edit = new JLabel(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        JLabel remove = new JLabel(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        lbLock.setCursor(handCursor);
        edit.setCursor(handCursor);
        remove.setCursor(handCursor);

        lbLock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                boolean isLock = DALNhanVien.LayTrangThaiLockNhanVien(MaNv);
                isLock = !isLock;

                boolean thanhCong = clickKhoaNhanVien(MaNv, isLock);

                if (thanhCong) {
                    lbLock.setIcon(xuLyHinhAnh.LayAnhTuSource(item, isLock ? "/fpoly/icon/lockuser.png" : "/fpoly/icon/unlockcuser.png"));
                }

            }
        });

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickEdit(MaNv);
            }

        });

        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickXoaNhanVien();
            }

        });

        panelThaoTac.add(lbLock);
        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(lbTen);
        item.add(lbGioiTinh);
        item.add(lbSDT);
        item.add(lbDiaChi);
        item.add(lbChucVu);
        item.add(panelThaoTac);

        tableDSNhanVien.addItem(item);
    }

    private void clickThemMoi() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frm_thongtinnhanvien frNv = new frm_thongtinnhanvien(topFrame,"", true, true);
        frNv.setVisible(true);
        loadDataForm();
    }

    private void clickEdit(String maNv) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frm_thongtinnhanvien frNv = new frm_thongtinnhanvien(topFrame,maNv, false, true);
        frNv.setVisible(true);
        loadDataForm();
    }

    private void clickXoaNhanVien() {

    }

    private boolean clickKhoaNhanVien(String maNv, boolean lock) {
        boolean thanhCong = DALNhanVien.khoaNhanVien(maNv, lock);
        String hanhDong = lock ? "Khóa" : "Mở khóa";
        ThongBao.taoThongBao(null, thanhCong ? hanhDong + " thành công" : "Lỗi khi " + hanhDong, thanhCong ? DialogThongBao.MessageIcon.CHECK : DialogThongBao.MessageIcon.WARNING);

        return thanhCong;
    }

    private void laySoTrangTimKiem(int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        int soTrang = DALNhanVien.laySoTrangTimKiem(search, SoHangCuaTrang);

        chiaTrang.setPageNumber(soTrang);
    }

    private void loadDataTimKiem(int SoTrang, int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        tableDSNhanVien.removeAllItem();
        ResultSet rs = DALNhanVien.timKiemNhanVien(search, SoTrang, SoHangCuaTrang);
        try {
            while (rs.next()) {

                String MaNv = rs.getString("ma");
                String Ten = rs.getString("ten");
                boolean GioiTinh = rs.getBoolean("gioitinh");
                String SDT = rs.getString("sodienthoai");
                String DiaChi = rs.getString("diachi");
                String TenChucVu = rs.getString("tenchucvu");
                boolean lock = rs.getBoolean("lock");

                loadItemTable(MaNv, Ten, GioiTinh, SDT, DiaChi, lock, TenChucVu);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            SqlHelper.closeConnection();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuCon = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        sPanel1 = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSNhanVien = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));
        setName("tabNhanVien"); // NOI18N
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm nhân viên");
        sButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton1.setPreferredSize(new java.awt.Dimension(150, 30));
        sButton1.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sButton1.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_Radius(15);
        sButton1.sets_drawBorder(false);
        sButton1.sets_hoverBackground(new java.awt.Color(2, 144, 49));
        sButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel1);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.setPreferredSize(new java.awt.Dimension(1000, 40));
        sPanel1.sets_borderColor(new java.awt.Color(228, 228, 229));
        sPanel1.sets_cornerRadius(15);
        sPanel1.sets_drawborder(true);
        sPanel1.sets_dropShadow(false);
        sPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 5));

        txtTimKiem.setPreferredSize(new java.awt.Dimension(290, 30));
        txtTimKiem.sets_drawborder(false);
        txtTimKiem.sets_iconRight(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconSearch.png"))); // NOI18N
        txtTimKiem.sets_placeholder("Tìm kiếm");
        txtTimKiem.sets_radius(10);
        txtTimKiem.setSelectionColor(new java.awt.Color(153, 153, 153));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        sPanel1.add(txtTimKiem);

        chiaTrang.setBackground(new java.awt.Color(255, 255, 255));
        chiaTrang.setArrowButtonBackground(new java.awt.Color(255, 255, 255));
        chiaTrang.setArrowButtonHoverBackground(new java.awt.Color(255, 255, 255));
        chiaTrang.setArrowButtonSize(25);
        chiaTrang.setButtonBackground(new java.awt.Color(255, 255, 255));
        chiaTrang.setButtonHoveForeground(new java.awt.Color(255, 255, 255));
        chiaTrang.setButtonHoverBackgroundColor(new java.awt.Color(82, 130, 216));
        chiaTrang.setButtonHoverBorder(false);
        chiaTrang.setButtonRound(15);
        chiaTrang.setButtonSize(25);
        chiaTrang.setOpaque(false);
        chiaTrang.setPreferredSize(new java.awt.Dimension(350, 30));
        sPanel1.add(chiaTrang);

        add(sPanel1);

        tableDSNhanVien.setBackground(new java.awt.Color(249, 248, 248));
        tableDSNhanVien.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSNhanVien.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSNhanVien.setHeaderHeight(40);
        tableDSNhanVien.setHeaderRound(15);
        tableDSNhanVien.setItemHeight(45);
        tableDSNhanVien.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSNhanVien.setItemRound(2);
        tableDSNhanVien.setItemSpace(2);
        tableDSNhanVien.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSNhanVien.setPreferredSize(new java.awt.Dimension(1000, 700));
        add(tableDSNhanVien);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanVienActionPerformed

    }//GEN-LAST:event_btnThemNhanVienActionPerformed

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        clickThemMoi();
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if (txtTimKiem.getTextValue().trim().length() == 0) {
            timKiem(1, SoHangCuaTrang);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static com.shady.scontrols.SPagination chiaTrang;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPopupMenu menuCon;
    public static com.shady.scontrols.SButton sButton1;
    public static com.shady.scontrols.SPanel sPanel1;
    public static com.shady.scontrols.STableWeb tableDSNhanVien;
    public static com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
