package gui.tab.subtab.khachhang;

import com.shady.event.PagiEvent;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALKhachHang;
import fpoly.tn.dto.KhachHang;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.khachhang.frm_ThemKhachHang;
import gui.form.message.DialogThongBao;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SubTabKhachHang extends javax.swing.JPanel {

    final int SoHangCuaTrang = 9;

    public SubTabKhachHang() {
        initComponents();

        initForm();
        
        loadDataForm();
        initEventComponent();
        initPagi();
    }

    private void initForm() {
        initTableDSLo();
    }

    private void initEventComponent() {

        txtTimKiem.addActionListener((ActionEvent e) -> {
            timKiem(1, SoHangCuaTrang);
        });
    }

    private void initTableDSLo() {

        tableDSKhachHang.addColum("Tên Khách hàng", 150);
        tableDSKhachHang.addColum("Giới tính", 100);
        tableDSKhachHang.addColum("Ngày sinh", 200);
        tableDSKhachHang.addColum("CMND", 100);
        tableDSKhachHang.addColum("Địa chỉ", 200);
        tableDSKhachHang.addColum("Thao tác", 0);
    }

    private void initPagi() {
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            loadDataTimKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadDataForm() {
        timKiem(1, SoHangCuaTrang);
    }

    private void loadItemTable(String maKH, String tenKH, boolean gioiTinh, KhachHang khachhang, String CMND, String diaChi, String ghiChu) {

        TableWebItem item = new TableWebItem();

        item.setId(maKH);

        // sub item component
        JLabel ten = new JLabel(tenKH);
        JLabel sex = new JLabel(xuLyHinhAnh.LayAnhTuSource(item, gioiTinh ? "/fpoly/icon/nam.png" : "/fpoly/icon/nu.png"));
        JLabel birthday = new JLabel(NgayThang.doiDinhDangNgay(khachhang.getNgaysinh(), "yyyy-MM-dd", "dd-MM-yyyy"));
        JLabel cmnd = new JLabel(CMND);
        JLabel address = new JLabel(diaChi);
//        JLabel note = new JLabel(ghiChu);
        JLabel edit = new JLabel();
        JLabel remove = new JLabel();

        // sub item style
        Font font = new Font("Tahoma", 1, 12);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        ten.setFont(font);
        ten.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
//        sex.setFont(font);
//        birthday.setFont(font);
//        cmnd.setFont(font);
//        address.setFont(font);
//        note.setFont(font);

        sex.setHorizontalAlignment(SwingConstants.CENTER);
        birthday.setHorizontalAlignment(SwingConstants.CENTER);
//        cmnd.setHorizontalAlignment(SwingConstants.CENTER);
//        note.setHorizontalAlignment(SwingConstants.CENTER);

        edit.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        remove.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        edit.setCursor(handCursor);
        remove.setCursor(handCursor);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        //compoents event
        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frm_ThemKhachHang frmT = new frm_ThemKhachHang(topFrame, maKH);
                frmT.setVisible(true);
                timKiem(1, SoHangCuaTrang);
            }
        });
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                boolean xacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn có muốn xóa khách hàng này", DialogThongBao.MessageIcon.QUESTION);
                if (xacNhan) {
                    if (DALKhachHang.xoaKhachHang(maKH)) {
                        ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
                    } else {
                        ThongBao.taoThongBao(null, "Có lỗi khi xóa khách hàng", DialogThongBao.MessageIcon.WARNING);
                    }
                    timKiem(1, SoHangCuaTrang);
                }
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        //sub thao tao
        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setOpaque(false);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSKhachHang.getItemHeight() - 20) / 2);
        fLayout.setHgap(5);

        panelThaoTac.setLayout(fLayout);

        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(ten);

        item.add(sex);
        item.add(birthday);
        item.add(cmnd);
        item.add(address);
//        item.add(note);
        item.add(panelThaoTac);

        tableDSKhachHang.addItem(item);

    }

    private void laySoTrangTimKiem(int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        int soTrang = DALKhachHang.laySoTrangTimKiem(search, SoHangCuaTrang);

        chiaTrang.setPageNumber(soTrang);

    }

    private void loadDataTimKiem(int SoTrang, int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        tableDSKhachHang.removeAllItem();
        ResultSet rs = DALKhachHang.timKiemKhachHang_chiaTrang(search, SoTrang, SoHangCuaTrang);
        try {
            while (rs.next()) {
                String gioiTinh;
                String maKH = rs.getString("ma");
                String tenKH = rs.getString("ten");
                boolean sex = rs.getBoolean("gioitinh");
                if (sex) {
                    gioiTinh = "Nam";
                } else {
                    gioiTinh = "Nữ";
                }
                String ngaySinh = rs.getString("ngaysinh");
                String cmnd = rs.getString("cmnd");
                String diaChi = rs.getString("diachi");
                String ghiChu = rs.getString("ghichu");
                KhachHang khachhang = new KhachHang(maKH, tenKH, sex, ngaySinh, cmnd, diaChi, ghiChu);
                loadItemTable(maKH, tenKH, sex, khachhang, cmnd, diaChi, ghiChu);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            SqlHelper.closeConnection();
        }
    }

    private void timKiem(int SoTrang, int SoHangCuaTrang) {
        laySoTrangTimKiem(SoHangCuaTrang);
        loadDataTimKiem(SoTrang, SoHangCuaTrang);
    }

    private void clickThemMoi() {
        moFormKhachHang("");
    }

    private void moFormKhachHang(String maKH) {
        frm_ThemKhachHang sp = new frm_ThemKhachHang((JFrame) SwingUtilities.getWindowAncestor(this), maKH);
        sp.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        sPanel1 = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSKhachHang = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm Khách hàng");
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
        chiaTrang.setPageNumber(15);
        chiaTrang.setPageSize(5);
        chiaTrang.setPreferredSize(new java.awt.Dimension(350, 30));
        sPanel1.add(chiaTrang);

        add(sPanel1);

        tableDSKhachHang.setBackground(new java.awt.Color(249, 248, 248));
        tableDSKhachHang.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSKhachHang.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSKhachHang.setHeaderHeight(40);
        tableDSKhachHang.setHeaderRound(15);
        tableDSKhachHang.setItemHeight(45);
        tableDSKhachHang.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSKhachHang.setItemRound(2);
        tableDSKhachHang.setItemSpace(2);
        tableDSKhachHang.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSKhachHang.setPreferredSize(new java.awt.Dimension(1000, 700));
        add(tableDSKhachHang);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        clickThemMoi();
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if(txtTimKiem.getTextValue().trim().length() == 0){
            timKiem(1,SoHangCuaTrang);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SPagination chiaTrang;
    private javax.swing.JPanel jPanel1;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SPanel sPanel1;
    private com.shady.scontrols.STableWeb tableDSKhachHang;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
