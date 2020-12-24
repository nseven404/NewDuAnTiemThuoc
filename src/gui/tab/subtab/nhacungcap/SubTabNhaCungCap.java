/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tab.subtab.nhacungcap;

import com.shady.event.PagiEvent;
import com.shady.tableweb.TableWebItem;
import fpoly.tn.dal.DALNhaCungCap;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.message.DialogThongBao;
import gui.form.nhacungcap.frm_ThemNhaCungCap;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DELL
 */
public class SubTabNhaCungCap extends javax.swing.JPanel {

    final int SoHangCuaTrang = 9;
    
    public SubTabNhaCungCap() {
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

        tableDSNhaCungCap.addColum("Tên", 300);
        tableDSNhaCungCap.addColum("Địa chỉ", 450);
        tableDSNhaCungCap.addColum("Số điện thoại", 150);
        tableDSNhaCungCap.addColum("Thao tác", 100);
    }

    private void initPagi() {
        chiaTrang.addButtonClickEventListener((PagiEvent pe) -> {
            loadDataTimKiem(pe.getPageNumber(), SoHangCuaTrang);
        });
    }

    private void loadDataForm() {

        loadDataNhaCungCap();

        // Hien thi Danh Sach NhaCungCap khi moi form
        timKiem(1, SoHangCuaTrang);
    }
    
     private void XoaNhaCungCap(String maNCC) {
        boolean XacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa Nhà cung cấp", DialogThongBao.MessageIcon.QUESTION);
        if (XacNhan) {
            if (DALNhaCungCap.xoaNhaCungCap(maNCC)) {
                ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
            } else {
                ThongBao.taoThongBao(null, "Có lỗi khi xóa Nhà cung cấp", DialogThongBao.MessageIcon.WARNING);
            }
            timKiem(1, SoHangCuaTrang);
        }
    }

    private void moFormEdit(String ma) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frm_ThemNhaCungCap frmT = new frm_ThemNhaCungCap(topFrame, ma);
        frmT.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }
    
    private void loadItemTable(String maNCC, String tenNCC, String diaChi, String sdt, String ghiChu) {
        TableWebItem item = new TableWebItem();
        item.setId(maNCC);
        // sub item component
        JLabel lblTen = new JLabel(tenNCC);
        JLabel lbldiaChi = new JLabel(diaChi);
        JLabel lblSDT = new JLabel(sdt);
        JLabel lblEdit = new JLabel();
        JLabel lblRemove = new JLabel();

        // sub item style
        Font font = new Font("Tahoma", 1, 12);
        lblTen.setFont(font);
        lblTen.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        lblTen.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        
        lbldiaChi.setFont(font);
        lbldiaChi.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        
        lblSDT.setFont(font);
        lblSDT.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
        
        lblSDT.setHorizontalAlignment(SwingConstants.CENTER);
        
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        lblEdit.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        lblRemove.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));

        lblEdit.setCursor(handCursor);
        lblRemove.setCursor(handCursor);
        //component event
        lblEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                moFormEdit(maNCC);
            }
            
        });
                
        lblRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                XoaNhaCungCap(maNCC);
            }
            
        });
        

        //sub thao tao
        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setOpaque(false);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDSNhaCungCap.getItemHeight() - 20) / 2);
        fLayout.setHgap(10);

        panelThaoTac.setLayout(fLayout);

        panelThaoTac.add(lblEdit);
        panelThaoTac.add(lblRemove);

        item.add(lblTen);
        item.add(lbldiaChi);
        item.add(lblSDT);
        item.add(panelThaoTac);

        tableDSNhaCungCap.addItem(item);
    }
    public void loadDataNhaCungCap(){
        tableDSNhaCungCap.removeAllItem();
        ResultSet rs = DALNhaCungCap.loadDanhSachNhaCungCap();
        try {
            while (rs.next()) {
                loadItemTable(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("diachi"),
                        rs.getString("sodienthoai"),
                        rs.getString("ghichu")
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            SqlHelper.closeConnection();
        }
        
    }
    //tim kiếm
    private void laySoTrangTimKiem(int SoHangCuaTrang) {
        String search = txtTimKiem.getTextValue();
        int soTrang = DALNhaCungCap.laySoTrangTimKiem(search, SoHangCuaTrang);
        chiaTrang.setPageNumber(soTrang);
    }

    private void loadDataTimKiem(int SoTrang, int SoHangCuaTrang) {

        String search = txtTimKiem.getTextValue();
        tableDSNhaCungCap.removeAllItem();

        ResultSet rs = DALNhaCungCap.timKiemNhaCungCap_chiaTrang(search, SoTrang, SoHangCuaTrang);
        try {
            while (rs.next()) {
                loadItemTable(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getString("diachi"),
                        rs.getString("sodienthoai"),
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlThaoTac = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        pnlBangChon = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        chiaTrang = new com.shady.scontrols.SPagination();
        tableDSNhaCungCap = new com.shady.scontrols.STableWeb();

        pnlThaoTac.setAlignmentX(0.0F);
        pnlThaoTac.setAlignmentY(0.0F);
        pnlThaoTac.setOpaque(false);
        pnlThaoTac.setPreferredSize(new java.awt.Dimension(1000, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setForeground(new java.awt.Color(255, 255, 255));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm mới");
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

        javax.swing.GroupLayout pnlThaoTacLayout = new javax.swing.GroupLayout(pnlThaoTac);
        pnlThaoTac.setLayout(pnlThaoTacLayout);
        pnlThaoTacLayout.setHorizontalGroup(
            pnlThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThaoTacLayout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 845, Short.MAX_VALUE))
        );
        pnlThaoTacLayout.setVerticalGroup(
            pnlThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThaoTacLayout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(pnlThaoTac);

        pnlBangChon.setBackground(new java.awt.Color(255, 255, 255));
        pnlBangChon.setPreferredSize(new java.awt.Dimension(1000, 40));
        pnlBangChon.sets_borderColor(new java.awt.Color(228, 228, 229));
        pnlBangChon.sets_cornerRadius(15);
        pnlBangChon.sets_drawborder(true);
        pnlBangChon.sets_dropShadow(false);
        pnlBangChon.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 5));

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
        pnlBangChon.add(txtTimKiem);

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
        pnlBangChon.add(chiaTrang);

        add(pnlBangChon);

        tableDSNhaCungCap.setBackground(new java.awt.Color(249, 248, 248));
        tableDSNhaCungCap.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDSNhaCungCap.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDSNhaCungCap.setHeaderHeight(40);
        tableDSNhaCungCap.setHeaderRound(15);
        tableDSNhaCungCap.setItemHeight(45);
        tableDSNhaCungCap.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDSNhaCungCap.setItemRound(2);
        tableDSNhaCungCap.setItemSpace(2);
        tableDSNhaCungCap.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDSNhaCungCap.setPreferredSize(new java.awt.Dimension(1000, 700));
        add(tableDSNhaCungCap);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
        moFormThemMoiNhaCungCap("");
    }//GEN-LAST:event_sButton1ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        timKiem(1,SoHangCuaTrang);
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void moFormThemMoiNhaCungCap(String maNCC) {
        frm_ThemNhaCungCap nhacc = new frm_ThemNhaCungCap((JFrame) SwingUtilities.getWindowAncestor(this), maNCC);
        nhacc.setVisible(true);
        timKiem(1, SoHangCuaTrang);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SPagination chiaTrang;
    private com.shady.scontrols.SPanel pnlBangChon;
    private javax.swing.JPanel pnlThaoTac;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.STableWeb tableDSNhaCungCap;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
