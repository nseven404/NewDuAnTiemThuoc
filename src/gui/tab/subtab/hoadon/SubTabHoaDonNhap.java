package gui.tab.subtab.hoadon;

import com.datepick.DatePick;
import com.shady.tableweb.TableWebItem;
import com.shady.tableweb.TableWebSubItem;
import fpoly.tn.dal.DALDonHangXuat;
import gui.form.hoadon.frm_HoaDonXuat;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import java.beans.PropertyChangeEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class SubTabHoaDonNhap extends javax.swing.JPanel {

    private final String MaNV;
    
    DatePick ngayBatDau;
    DatePick ngayKetThuc;
    
    private final int SoHangMoiTrang = 10;
    
    
    public SubTabHoaDonNhap(String MaNv) {
        initComponents();
        this.MaNV = MaNv;
        
        initDatePick();
        
        initComboBox();
        
     //   timKiem();
        
        initTable();
        
        
    }
    
    /// HÀM XỬ LÝ
    
    private void initTable(){
        
        tableHoaDonNhap.addColum("Mã HD", 140);
        tableHoaDonNhap.addColum("Nhân Viên", 200);
        tableHoaDonNhap.addColum("Khách Hàng", 200);
        tableHoaDonNhap.addColum("Ngày Lập", 140);
        tableHoaDonNhap.addColum("Tổng Tiền", 140);
        tableHoaDonNhap.addColum("Trạng Thái", 140);
        tableHoaDonNhap.addColum("Ghi Chú", 0);
        tableHoaDonNhap.addColum("Thao Tác", 100);
        
    }
    
    private void initComboBox(){
        
        comboThoiGian.addActionListener((e) -> {
            chonThoiGian(comboThoiGian.getSelectedIndex());
        });
        comboTrangThai.addActionListener((e) -> {
            timKiem();
        });
        
    }
    
    private void initDatePick(){
        
        ngayBatDau = new DatePick();
        ngayKetThuc = new DatePick();
        
        ngayBatDau.setButton(bntNgayBatDau);
        ngayKetThuc.setButton(bntNgayKetThuc);
        
        chonThoiGian(comboThoiGian.getSelectedIndex());
        
        
        bntNgayBatDau.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if(evt.getPropertyName().equals("text")){
                timKiem();
            }
        });
        
        bntNgayKetThuc.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if(evt.getPropertyName().equals("text")){
                timKiem();
            }
        });
    }
    
    private void chonThoiGian(int selectIndex){
        
        switch(selectIndex){
            case 0:
                bntNgayKetThuc.setVisible(false);
                lbNgayKetThuc.setVisible(false);
                lbNgayBatDau.setText("Ngày tháng");
                break;
            case 1:
                bntNgayKetThuc.setVisible(true);
                lbNgayKetThuc.setVisible(true);
                lbNgayBatDau.setText("Từ ngày");
                break;
        }
        
        timKiem();
    }
    
    private void timKiem(){
        
        String s_ngayBatDau = NgayThang.doiDinhDangNgay(ngayBatDau.getSelectedDate(),"dd-MM-yyyy","yyyy-MM-dd");
        String s_ngayKetThuc = NgayThang.doiDinhDangNgay(ngayKetThuc.getSelectedDate(),"dd-MM-yyyy","yyyy-MM-dd");
        
        
        String search = "%" +txtTimKiem.getTextValue() + "%";
        
        if(comboThoiGian.getSelectedIndex() == 0){
            s_ngayKetThuc = s_ngayBatDau;
        }
        
        // thêm đuôi thời gian, có thể sau này thêm chọn giờ?
        s_ngayBatDau += " 00:00:00:000";
        s_ngayKetThuc += " 23:59:59:999";
        
        ResultSet rs = DALDonHangXuat.timKiemDanhSachDonHangBan(s_ngayBatDau, s_ngayKetThuc, search, comboTrangThai.getSelectedIndex()-1);
        loadDanhSachDonHang(rs);
    }
    
    private void loadItemTable(String ma, String tenkhachhang, String tennhanvien, String ngaythang, int tongtien, int trangthai, String ghichu){
        
        TableWebItem item = new TableWebItem();
        
        TableWebSubItem subMa = new TableWebSubItem();
        TableWebSubItem subTenKh = new TableWebSubItem();
        TableWebSubItem subTenNv = new TableWebSubItem();
        TableWebSubItem subNgayThang = new TableWebSubItem();
        TableWebSubItem subTongTien = new TableWebSubItem();
        TableWebSubItem subTrangThai = new TableWebSubItem();
        TableWebSubItem subGhiChu = new TableWebSubItem();
        TableWebSubItem subThaoTac = new TableWebSubItem();
        
    }
    
    private void loadDanhSachDonHang(ResultSet rs){
        
        tableHoaDonNhap.removeAllItem();
        try {
            while(rs.next()){
                
                String ma = rs.getString("ma");
                String tenkhachhang = rs.getString("tenkhachhang");
                String tennhanvien = rs.getString("tennhanvien");
                Date date = NgayThang.layNgayTuTimestamp(rs.getTimestamp("ngaythang"));
                String ngaythang = NgayThang.layNgayThangNamFormat(date, "dd-MM-yyyy HH:mm:ss");
                int tongtien = rs.getInt("tongtien");
                int trangthai = rs.getInt("trangthai");
                String ghichu = rs.getString("ghichu");
                
                loadItemTable(ma, tenkhachhang, tennhanvien, ngaythang, tongtien, trangthai, ghichu);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally{
            SqlHelper.closeConnection();
        }
    }
    
    private void moFormDonHangNhap(String maHD){
        frm_HoaDonXuat frmHD = new frm_HoaDonXuat((JFrame)SwingUtilities.getWindowAncestor(this),MaNV, maHD);
        frmHD.setVisible(true);
        timKiem();
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bntThemMoi = new com.shady.scontrols.SButton();
        tableHoaDonNhap = new com.shady.scontrols.STableWeb();
        sPanel1 = new com.shady.scontrols.SPanel();
        txtTimKiem = new com.shady.scontrols.STextField();
        jLabel1 = new javax.swing.JLabel();
        comboThoiGian = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lbNgayBatDau = new javax.swing.JLabel();
        bntNgayBatDau = new com.shady.scontrols.SButton();
        bntNgayKetThuc = new com.shady.scontrols.SButton();
        lbNgayKetThuc = new javax.swing.JLabel();
        comboTrangThai = new javax.swing.JComboBox<>();
        lbTrangThai = new javax.swing.JLabel();
        chiaTrang = new com.shady.scontrols.SPagination();

        setBackground(new java.awt.Color(247, 247, 247));
        setName("tabHoaDon"); // NOI18N

        bntThemMoi.setBackground(new java.awt.Color(6, 175, 62));
        bntThemMoi.setForeground(new java.awt.Color(255, 255, 255));
        bntThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        bntThemMoi.setText("THÊM HĐ");
        bntThemMoi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bntThemMoi.sets_ForeColor(new java.awt.Color(255, 255, 255));
        bntThemMoi.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        bntThemMoi.sets_Radius(10);
        bntThemMoi.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntThemMoi.sets_drawBorder(false);
        bntThemMoi.sets_hoverBackground(new java.awt.Color(0, 153, 51));
        bntThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntThemMoiActionPerformed(evt);
            }
        });

        tableHoaDonNhap.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableHoaDonNhap.setHeaderHeight(40);
        tableHoaDonNhap.setHeaderRound(10);
        tableHoaDonNhap.setItemHoverBackground(new java.awt.Color(238, 238, 238));
        tableHoaDonNhap.setListViewBackground(new java.awt.Color(247, 247, 247));

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_borderColor(new java.awt.Color(233, 230, 230));
        sPanel1.sets_drawborder(true);

        txtTimKiem.sets_iconRight(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconSearch.png"))); // NOI18N
        txtTimKiem.sets_placeholder("Tìm kiếm . . .");
        txtTimKiem.sets_radius(10);
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Thời gian");

        comboThoiGian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trong Ngày", "Trong Khoảng" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tìm kiếm");

        lbNgayBatDau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNgayBatDau.setText("Từ ngày");

        bntNgayBatDau.setBackground(new java.awt.Color(247, 247, 247));
        bntNgayBatDau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLich.png"))); // NOI18N
        bntNgayBatDau.setText("00-00-0000");
        bntNgayBatDau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntNgayBatDau.setIconTextGap(15);
        bntNgayBatDau.sets_Radius(5);
        bntNgayBatDau.sets_hoverBackground(new java.awt.Color(238, 238, 238));

        bntNgayKetThuc.setBackground(new java.awt.Color(247, 247, 247));
        bntNgayKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLich.png"))); // NOI18N
        bntNgayKetThuc.setText("00-00-0000");
        bntNgayKetThuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntNgayKetThuc.setIconTextGap(15);
        bntNgayKetThuc.sets_Radius(5);
        bntNgayKetThuc.sets_hoverBackground(new java.awt.Color(238, 238, 238));

        lbNgayKetThuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNgayKetThuc.setText("Đến ngày");

        comboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa Thanh Toán", "Còn thiếu", "Đã Thanh Toán" }));

        lbTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTrangThai.setText("Trạng thái");

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

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(comboThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNgayBatDau)
                    .addComponent(bntNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNgayKetThuc))
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTrangThai)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addComponent(comboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chiaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sPanel1Layout.createSequentialGroup()
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbNgayBatDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(sPanel1Layout.createSequentialGroup()
                                .addComponent(lbTrangThai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bntNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(sPanel1Layout.createSequentialGroup()
                                .addComponent(lbNgayKetThuc)
                                .addGap(36, 36, 36))
                            .addComponent(chiaTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableHoaDonNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 1242, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntThemMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(bntThemMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableHoaDonNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void bntThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntThemMoiActionPerformed
        moFormDonHangNhap("");
    }//GEN-LAST:event_bntThemMoiActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        timKiem();
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SButton bntNgayBatDau;
    private com.shady.scontrols.SButton bntNgayKetThuc;
    private com.shady.scontrols.SButton bntThemMoi;
    private com.shady.scontrols.SPagination chiaTrang;
    private javax.swing.JComboBox<String> comboThoiGian;
    private javax.swing.JComboBox<String> comboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbNgayBatDau;
    private javax.swing.JLabel lbNgayKetThuc;
    private javax.swing.JLabel lbTrangThai;
    private com.shady.scontrols.SPanel sPanel1;
    private com.shady.scontrols.STableWeb tableHoaDonNhap;
    private com.shady.scontrols.STextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
