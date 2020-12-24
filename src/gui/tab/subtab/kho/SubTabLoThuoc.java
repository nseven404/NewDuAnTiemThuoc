package gui.tab.subtab.kho;

import com.shady.event.PagiEvent;
import com.shady.scontrols.SPanel;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALLoThuoc;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.LoThuoc;
import fpoly.tn.dto.MyComboBox;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyComboBox;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.message.DialogThongBao;
import gui.form.lothuoc.Frm_LoThuoc;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SubTabLoThuoc extends javax.swing.JPanel {

    final int SoHangCuaTrang = 9;

    public SubTabLoThuoc() {
        initComponents();

        loadDanhSachKho();
        initForm();

        timKiem();
    }

    private void initForm() {
        initTableDSLo();
        initPagi();

        comboKho.addActionListener((ActionEvent e) -> {
            timKiem();
        });

        comboThoiGian.addActionListener((ActionEvent e) -> {
            timKiem();
        });
        txtTimKiem.addActionListener((ActionEvent e) -> {
            timKiem();
        });
    }

    private void initTableDSLo() {

        tableDSLoThuoc.addColum("Tên Thuốc", 200);
        tableDSLoThuoc.addColum("Nhà cung cấp", 200);
        tableDSLoThuoc.addColum("Ngày SX", 100);
        tableDSLoThuoc.addColum("Ngày Hết Hạn", 100);
        tableDSLoThuoc.addColum("Số lượng", 100);
        tableDSLoThuoc.addColum("Hạn Sử Dụng", 150);
        tableDSLoThuoc.addColum("Thao tác", 100);
    }

    private void initPagi() {
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            loadDataTimKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadDanhSachKho() {

        DefaultComboBoxModel model = xuLyComboBox.layModelCombo(comboKho);

        MyComboBox data0 = new MyComboBox("", "Tất cả Kho");
        model.addElement(data0);

        ResultSet rs = DALKho.loadDanhSachKho();
        try {
            if (rs.next()) {

                MyComboBox data = new MyComboBox(rs.getString("ma"), rs.getString("ten"));
                model.addElement(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

    }

    private void timKiem() {
        laySoTrangTimKiem(SoHangCuaTrang);
        loadDataTimKiem(1, SoHangCuaTrang);
    }

    private void laySoTrangTimKiem(int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        int trangThai = comboThoiGian.getSelectedIndex();
        String maKho = xuLyComboBox.getSelectedValue(comboKho);
        int soTrang = DALLoThuoc.laySoTrangTimKiemLoThuoc(search, maKho, trangThai, SoHangCuaTrang);

        chiaTrang.setPageNumber(soTrang);

    }

    private void loadItemTableLoThuoc(String tenThuoc, String tenNCC, LoThuoc loThuoc, int soNgayConLai) {

        TableWebItem item = new TableWebItem();
        item.setId(loThuoc.getMaLo());

        JPanel panelThaoTac = new JPanel();
        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSLoThuoc.getItemHeight() - 20) / 2);
        fLayout.setHgap(5);
        panelThaoTac.setLayout(fLayout);
        panelThaoTac.setOpaque(false);

        // sub item style
        Font font = new Font("Tahoma", 1, 12);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        JLabel lbtenThuoc = new JLabel(tenThuoc);
        JLabel lbtenNCC = new JLabel(tenNCC);
        JLabel lbNgaySX = new JLabel(NgayThang.doiDinhDangNgay(loThuoc.getNgaySanXuat(), "yyyy-MM-dd", "dd-MM-yyyy"));
        JLabel lbNgayHetHan = new JLabel(NgayThang.doiDinhDangNgay(loThuoc.getHanSuDung(), "yyyy-MM-dd", "dd-MM-yyyy"));
        JLabel lbSoLuong = new JLabel(String.valueOf(loThuoc.getSoLuong()));
        JLabel lbNgayConLai = new JLabel();

        lbtenThuoc.setFont(font);
        lbtenThuoc.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        lbNgaySX.setFont(font);
        lbNgayHetHan.setFont(font);
        lbNgayConLai.setFont(font);
        lbSoLuong.setFont(font);

        SPanel khungNgayHetHan = new SPanel();
        khungNgayHetHan.sets_cornerRadius(25);
        khungNgayHetHan.setPreferredSize(new Dimension(100, 25));
        khungNgayHetHan.setLayout(new GridBagLayout());

        if (soNgayConLai > 30) {
            lbNgayConLai.setText(soNgayConLai / 30 + " Tháng");
            khungNgayHetHan.setBackground(new Color(0, 0, 0, 0));
        } else if (soNgayConLai <= 0) {
            lbNgayConLai.setForeground(Color.white);
            lbNgayConLai.setText("Hết Hạn");
            khungNgayHetHan.setBackground(new Color(204, 0, 0));
        } else {
            lbNgayConLai.setForeground(Color.white);
            khungNgayHetHan.setBackground(new Color(204, 204, 0));
            lbNgayConLai.setText(soNgayConLai + " Ngày");
        }
        khungNgayHetHan.add(lbNgayConLai);

        JPanel panelNgayHetHan = new JPanel();
        panelNgayHetHan.setLayout(new GridBagLayout());
        panelNgayHetHan.setOpaque(false);
        panelNgayHetHan.add(khungNgayHetHan);

        JLabel edit = new JLabel();
        JLabel remove = new JLabel();
        edit.setCursor(handCursor);
        remove.setCursor(handCursor);
        edit.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        remove.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        edit.setBorder(new EmptyBorder(0, 0, 0, 15));

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frm_LoThuoc fm = new Frm_LoThuoc(null, loThuoc.getMaLo(), loThuoc.getMaThuoc());
                fm.setVisible(true);
                timKiem();
            }
        });
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean XacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa lô thuốc", loThuoc.getMaLo(), DialogThongBao.MessageIcon.QUESTION);
                if (XacNhan) {
                    if (DALLoThuoc.xoaLoThuoc(loThuoc.getMaLo())) {
                        ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
                    } else {
                        ThongBao.taoThongBao(null, "Có lỗi khi xóa lô thuốc", DialogThongBao.MessageIcon.WARNING);
                    }
                    timKiem();
                }
            }
        });

        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(lbtenThuoc);
        item.add(lbtenNCC);
        item.add(lbNgaySX);
        item.add(lbNgayHetHan);
        item.add(lbSoLuong);
        item.add(panelNgayHetHan);
        item.add(panelThaoTac);

        tableDSLoThuoc.addItem(item);

    }

    private void loadDataTimKiem(int soTrang, int SoHangCuaTrang) {

        tableDSLoThuoc.removeAllItem();

        String search = txtTimKiem.getTextValue();
        int trangThai = comboThoiGian.getSelectedIndex();
        String maKho = xuLyComboBox.getSelectedValue(comboKho);
        ResultSet rs = DALLoThuoc.timKiemLoThuocPhanTrang(search, maKho, trangThai, soTrang, SoHangCuaTrang);

        try {
            while (rs.next()) {

                String MaLo = rs.getString("MaLo");
                String TenThuoc = rs.getString("TenSP");
                String MaThuoc = rs.getString("MaSP");
                String TenNCC = rs.getString("TenNCC");
                String ngaySanXuat = rs.getString("NgaySX");
                String hanSuDung = rs.getString("hanSuDung");
                int SoLuong = rs.getInt("SoLuong");
                String ghiChu = rs.getString("GhiChu");
                int soNgayConLai = rs.getInt("hethan");

                LoThuoc loThuoc = new LoThuoc(MaLo, MaThuoc, "", ngaySanXuat, hanSuDung, SoLuong, ghiChu);

                loadItemTableLoThuoc(TenThuoc, TenNCC, loThuoc, soNgayConLai);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubTabLoThuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
    }

    private void clickThemMoi() {
        Frm_LoThuoc fm = new Frm_LoThuoc(null, "", "");
        fm.setVisible(true);
        timKiem();
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
        comboThoiGian = new javax.swing.JComboBox<>();
        comboKho = new javax.swing.JComboBox<>();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSLoThuoc = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm Lô");
        sButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton1.setPreferredSize(new java.awt.Dimension(115, 30));
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

        comboThoiGian.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboThoiGian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất Cả HSD", "HSD xa nhất", "HSD gần nhất", "Hết Hạn" }));
        comboThoiGian.setPreferredSize(new java.awt.Dimension(150, 30));
        sPanel1.add(comboThoiGian);

        comboKho.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboKho.setPreferredSize(new java.awt.Dimension(150, 30));
        sPanel1.add(comboKho);

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
        sPanel1.add(chiaTrang);

        add(sPanel1);

        tableDSLoThuoc.setBackground(new java.awt.Color(249, 248, 248));
        tableDSLoThuoc.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSLoThuoc.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSLoThuoc.setHeaderHeight(40);
        tableDSLoThuoc.setHeaderRound(15);
        tableDSLoThuoc.setItemHeight(45);
        tableDSLoThuoc.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSLoThuoc.setItemRound(2);
        tableDSLoThuoc.setItemSpace(2);
        tableDSLoThuoc.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSLoThuoc.setPreferredSize(new java.awt.Dimension(1000, 700));
        add(tableDSLoThuoc);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        clickThemMoi();
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if("".equals(txtTimKiem.getTextValue())){
            timKiem();
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SPagination chiaTrang;
    private javax.swing.JComboBox<String> comboKho;
    private javax.swing.JComboBox<String> comboThoiGian;
    private javax.swing.JPanel jPanel1;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SPanel sPanel1;
    private com.shady.scontrols.STableWeb tableDSLoThuoc;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
