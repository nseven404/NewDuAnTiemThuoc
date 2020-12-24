/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tab.subtab.kho;

import com.shady.event.PagiEvent;
import com.shady.event.PaginationEvent;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALLoThuoc;
import fpoly.tn.dal.DALLoaiThuoc;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.MyComboBox;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyComboBox;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.loaithuoc.frm_ThemLoaiThuoc;
import gui.form.message.DialogThongBao;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
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
import org.w3c.dom.events.MouseEvent;

/**
 *
 * @author DELL
 */
public class SubTabNhomThuoc extends javax.swing.JPanel {

    /**
     * Creates new form SubTabNhomThuoc
     */
    final int SoHangCuaTrang = 9;

    public SubTabNhomThuoc() {
        initComponents();
        initEventComponent();
        // table
        initTable();

        loadDataForm();
        initPagi();
    }

    private void initEventComponent() {

        txtTimKiem.addActionListener((ActionEvent e) -> {
            timKiem(1, SoHangCuaTrang);
        });
    }

    private void initTable() {

        tableDSLoaiThuoc.addColum("Tên", 300);
        tableDSLoaiThuoc.addColum("Ghi chú", 300);
        tableDSLoaiThuoc.addColum("Thao tác", 100);
        //    tableDSThuoc.setColumnAlignment(0, SwingConstants.LEFT);
    }

    private void initPagi() {
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            loadDataTimKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadItemTable(String maLoai, String tenLoai, String ghiChu) {
        TableWebItem item = new TableWebItem();
        item.setId(maLoai);
        // sub item component
        JLabel lblTen = new JLabel(tenLoai);
        JLabel lblNote = new JLabel(ghiChu);
        JLabel lblEdit = new JLabel();
        JLabel lblRemove = new JLabel();

        // sub item style
        Font font = new Font("Tahoma", 1, 12);
        lblTen.setFont(font);
        lblTen.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        lblTen.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        lblNote.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));

        lblTen.setHorizontalAlignment(SwingConstants.CENTER);
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        lblEdit.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        lblRemove.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        lblEdit.setCursor(handCursor);
        lblRemove.setCursor(handCursor);
        //component event
        lblEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                moFormEdit(maLoai);
            }
            
        });
                
        lblRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                XoaLoaiThuoc(maLoai);
            }
            
        });
        

        //sub thao tao
        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setOpaque(false);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSLoaiThuoc.getItemHeight() - 20) / 2);
        fLayout.setHgap(10);

        panelThaoTac.setLayout(fLayout);

        panelThaoTac.add(lblEdit);
        panelThaoTac.add(lblRemove);

        item.add(lblTen);
        item.add(lblNote);
        item.add(panelThaoTac);

        tableDSLoaiThuoc.addItem(item);
    }

    private void XoaLoaiThuoc(String maLoai) {
        boolean XacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa loại thuốc", DialogThongBao.MessageIcon.QUESTION);
        if (XacNhan) {
            if (DALLoaiThuoc.xoaLoaiThuoc(maLoai)) {
                ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
            } else {
                ThongBao.taoThongBao(null, "Có lỗi khi xóa loại thuốc", DialogThongBao.MessageIcon.WARNING);
            }
            timKiem(1, SoHangCuaTrang);
        }
    }

    private void moFormEdit(String maloai) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frm_ThemLoaiThuoc frmT = new frm_ThemLoaiThuoc(topFrame, maloai);
        frmT.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }

    private void loadDataForm() {

        loadDataLoaiThuoc();

        // Hien thi Danh Sach San Pham khi moi form
        timKiem(1, SoHangCuaTrang);
    }

    private void laySoTrangTimKiem(int SoHangCuaTrang) {
        String search = txtTimKiem.getTextValue();
        int soTrang = DALLoaiThuoc.laySoTrangTimKiem(search, SoHangCuaTrang);
        chiaTrang.setPageNumber(soTrang);
    }

    private void loadDataTimKiem(int SoTrang, int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        tableDSLoaiThuoc.removeAllItem();

        ResultSet rs = DALLoaiThuoc.timKiemLoaiThuoc_chiaTrang(search, SoTrang, SoHangCuaTrang);
        try {
            while (rs.next()) {
                loadItemTable(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("ghichu")
                );
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

    public void loadDataLoaiThuoc() {
        tableDSLoaiThuoc.removeAllItem();
        ResultSet rs = DALKho.loadDanhSachLoai();
        try {
            while (rs.next()) {
                loadItemTable(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("ghichu")
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            SqlHelper.closeConnection();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        sPanel1 = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSLoaiThuoc = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));
        setPreferredSize(new java.awt.Dimension(2850, 720));

        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm Loại Thuốc");
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
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 845, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel2);

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
        sPanel1.add(chiaTrang);

        add(sPanel1);

        tableDSLoaiThuoc.setBackground(new java.awt.Color(249, 248, 248));
        tableDSLoaiThuoc.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSLoaiThuoc.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSLoaiThuoc.setHeaderHeight(40);
        tableDSLoaiThuoc.setHeaderRound(15);
        tableDSLoaiThuoc.setItemHeight(45);
        tableDSLoaiThuoc.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSLoaiThuoc.setItemRound(2);
        tableDSLoaiThuoc.setItemSpace(2);
        tableDSLoaiThuoc.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSLoaiThuoc.setPreferredSize(new java.awt.Dimension(1000, 700));
        add(tableDSLoaiThuoc);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        moFormLoaiThuoc("");
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
       timKiem(1,SoHangCuaTrang);
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void moFormLoaiThuoc(String maLoai) {
        frm_ThemLoaiThuoc loai = new frm_ThemLoaiThuoc((JFrame) SwingUtilities.getWindowAncestor(this), maLoai);
        loai.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SPagination chiaTrang;
    private javax.swing.JPanel jPanel2;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SPanel sPanel1;
    private com.shady.scontrols.STableWeb tableDSLoaiThuoc;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
