package gui.tab.subtab.kho;

import com.shady.event.PagiEvent;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.MyComboBox;
import gui.form.thuoc.frm_thuoc;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.XuLyInput;
import fpoly.tn.helper.xuLyHinhAnh;
import fpoly.tn.helper.xuLyComboBox;
import gui.form.message.DialogThongBao;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SubTabDanhSachThuoc extends javax.swing.JPanel {

    private final int SoHangCuaTrang = 10;

    public SubTabDanhSachThuoc() {
        initComponents();
        initEventComponent();

        // table
        initTable();

        // pagi
        initPagi();

        // event component
        loadDataForm();

    }

    private void initEventComponent() {

        txtTimKiem.addActionListener((ActionEvent e) -> {
            timKiem(1, SoHangCuaTrang);
        });
    }

    private void initTable() {

        tableDSThuoc.addColum("Tên", 300);
        tableDSThuoc.addColum("ĐVT", 100);
        tableDSThuoc.addColum("Số lượng", 100);
        tableDSThuoc.addColum("Giá nhập", 100);
        tableDSThuoc.addColum("Giá bán", 100);
        tableDSThuoc.addColum("Thao tác", 0);

        //    tableDSThuoc.setColumnAlignment(0, SwingConstants.LEFT);
    }

    private void initPagi() {
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            loadDataTimKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadItemTable(String maThuoc, String tenThuoc, String loaiThuoc, String dvtThuoc, int SoLuong, int GiaNhap, int GiaBan) {

        TableWebItem item = new TableWebItem();

        item.setId(maThuoc);

        // sub item component
        JLabel ten = new JLabel(tenThuoc);
        JLabel loai = new JLabel(loaiThuoc);
        JLabel dvt = new JLabel(dvtThuoc);
        JLabel soluong = new JLabel(String.valueOf(SoLuong));
        JLabel gianhap = new JLabel(XuLyInput.formatThemChamVaoSo(GiaNhap) + " VNĐ");
        JLabel giaban = new JLabel(XuLyInput.formatThemChamVaoSo(GiaBan) + " VNĐ");

        JLabel edit = new JLabel();
        JLabel remove = new JLabel();

        // sub item style
        Font font = new Font("Tahoma", 1, 12);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        ten.setFont(font);
        loai.setFont(new Font("Tahoma", 0, 11));
        loai.setForeground(Color.GRAY);
        ten.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        loai.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        dvt.setFont(font);
        soluong.setFont(font);
        gianhap.setFont(font);
        giaban.setFont(font);

        dvt.setHorizontalAlignment(SwingConstants.CENTER);
        soluong.setHorizontalAlignment(SwingConstants.CENTER);
        gianhap.setHorizontalAlignment(SwingConstants.CENTER);
        giaban.setHorizontalAlignment(SwingConstants.CENTER);

        edit.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        remove.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        edit.setCursor(handCursor);
        remove.setCursor(handCursor);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        //compoents event
        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frm_thuoc frmT = new frm_thuoc(topFrame, maThuoc);
                frmT.setVisible(true);
                timKiem(1, SoHangCuaTrang);
            }
        });
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               
                boolean xacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn có muốn xóa thuốc", DialogThongBao.MessageIcon.QUESTION);
                if (xacNhan) {
//                    if (DALThuoc.xoaThuoc(maThuoc)) {
//                        ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
//                    }else{
//                        ThongBao.taoThongBao(null, "Có lỗi khi xóa lô thuốc", DialogThongBao.MessageIcon.WARNING);
//                    }
//                   s
                }
            }
        });

        // sub ten
        JPanel panelTen = new JPanel();
        panelTen.setLayout(new GridBagLayout());
        panelTen.setBackground(new Color(0, 0, 0, 0));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        panelTen.add(ten, constraints);
        constraints.gridy = 1;
        panelTen.add(loai, constraints);

        //sub thao tao
        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setOpaque(false);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSThuoc.getItemHeight() - 20) / 2);
        fLayout.setHgap(5);

        panelThaoTac.setLayout(fLayout);

        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(panelTen);
        item.add(dvt);

        item.add(soluong);
        item.add(gianhap);
        item.add(giaban);

        item.add(panelThaoTac);

        tableDSThuoc.addItem(item);

    }
    

    private void loadDataForm() {

        loadDanhSachKho();
        loadDataLoaiThuoc();

        // Hien thi Danh Sach San Pham khi moi form
        timKiem(1, SoHangCuaTrang);
    }

    private void loadDataLoaiThuoc() {

        DefaultComboBoxModel model = (DefaultComboBoxModel) comboLoaiThuoc.getModel();
        MyComboBox data0 = new MyComboBox("", "Tất cả Loại");
        model.addElement(data0);

        ResultSet rs;
        rs = DALKho.loadDanhSachLoai();
        if (rs != null) {
            try {
                while (rs.next()) {
                    model.addElement(new MyComboBox(rs.getString("ma"), rs.getString("ten")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SubTabDanhSachThuoc.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SqlHelper.closeConnection();
            }
        }

        comboLoaiThuoc.addActionListener((ActionEvent e) -> {
            timKiem(1, SoHangCuaTrang);
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

        comboKho.addActionListener((ActionEvent e) -> {
            timKiem(1, SoHangCuaTrang);
        });

    }

    private void laySoTrangTimKiem(int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        String maLoai = xuLyComboBox.getSelectedValue(comboLoaiThuoc);
        String maKho = xuLyComboBox.getSelectedValue(comboKho);
        int soTrang = DALThuoc.laySoTrangTimKiem(search, maLoai, maKho, SoHangCuaTrang);

        chiaTrang.setPageNumber(soTrang);

    }

    private void loadDataTimKiem(int SoTrang, int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        String maLoai = xuLyComboBox.getSelectedValue(comboLoaiThuoc);
        String maKho = xuLyComboBox.getSelectedValue(comboKho);

        tableDSThuoc.removeAllItem();

        ResultSet rs = DALThuoc.timKiemSanPham_chiaTrang(search, maLoai, maKho, SoTrang, SoHangCuaTrang);
        try {
            while (rs.next()) {
                loadItemTable(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("TenLoai"),
                        rs.getString("TenDVT"),
                        rs.getInt("soluong"),
                        rs.getInt("GiaNhap"),
                        rs.getInt("GiaBan"));
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
        moFormThuoc("");
    }

    private void moFormThuoc(String maSp) {
        frm_thuoc sp = new frm_thuoc((JFrame) SwingUtilities.getWindowAncestor(this), maSp);
        sp.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        sPanel1 = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        comboLoaiThuoc = new javax.swing.JComboBox<>();
        comboKho = new javax.swing.JComboBox<>();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSThuoc = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));
        setName("tabKhoHang"); // NOI18N
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));

        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(950, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm Thuốc");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 835, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel2);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.setPreferredSize(new java.awt.Dimension(950, 40));
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

        comboLoaiThuoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboLoaiThuoc.setPreferredSize(new java.awt.Dimension(150, 30));
        sPanel1.add(comboLoaiThuoc);

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

        tableDSThuoc.setBackground(new java.awt.Color(249, 248, 248));
        tableDSThuoc.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSThuoc.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSThuoc.setHeaderHeight(40);
        tableDSThuoc.setHeaderRound(15);
        tableDSThuoc.setItemHeight(45);
        tableDSThuoc.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSThuoc.setItemRound(2);
        tableDSThuoc.setItemSpace(2);
        tableDSThuoc.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSThuoc.setPreferredSize(new java.awt.Dimension(950, 300));
        add(tableDSThuoc);
    }// </editor-fold>//GEN-END:initComponents

    private void comboDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDVTActionPerformed
        //  timKiem();
    }//GEN-LAST:event_comboDVTActionPerformed

    private void comboKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKhoActionPerformed
        //  timKiem();
    }//GEN-LAST:event_comboKhoActionPerformed

    private void comboSuDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSuDungActionPerformed
        //   timKiem();
    }//GEN-LAST:event_comboSuDungActionPerformed

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        clickThemMoi();
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        if ("".equals(txtTimKiem.getTextValue())) {
            loadDataForm();
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SPagination chiaTrang;
    private javax.swing.JComboBox<String> comboKho;
    private javax.swing.JComboBox<String> comboLoaiThuoc;
    private javax.swing.JPanel jPanel2;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SPanel sPanel1;
    private com.shady.scontrols.STableWeb tableDSThuoc;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
