package gui.tab.subtab.kho;

import com.shady.scontrols.SPanel;
import com.shady.tableweb.TableWebItem;
import com.shady.tableweb.TableWebSubItem;
import fpoly.tn.dal.DALDonViDongDoi;
import fpoly.tn.dal.DALDonViTinh;
import fpoly.tn.dal.DALKho;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.donvidonggoi.Frm_DonViDongGoi;
import gui.form.message.DialogThongBao;
import gui.form.donvitinh.Frm_DonViTinh;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SubTabDonViTinh extends javax.swing.JPanel {

    public SubTabDonViTinh() {
        initComponents();
        
        initTableDVT();
        initTableDongGoi();
        
        //load data
        loadDanhSachDonViTinh();
        loadDanhSachDongGoi();
    }
    
    private void initTableDVT(){
        tableDVT.addColum("Tên", 0);
        tableDVT.addColum("Thao tác", 150);
    }
    private void initTableDongGoi(){
        tableDongGoi.addColum("Mã", 100);
        tableDongGoi.addColum("Tên", 0);
        tableDongGoi.addColum("Thao Tác", 150);
    }
    
    private void loadItemDVT(String ma, String ten){
        
        TableWebItem item = new TableWebItem();
        
        JPanel panelThaoTac = new JPanel();
        item.setId(ma);
        
        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDVT.getItemHeight() - 20) / 2);
        fLayout.setHgap(10);
        panelThaoTac.setLayout(fLayout);
        panelThaoTac.setOpaque(false);
        
        JLabel lTen = new JLabel(ten);
        lTen.setFont(new Font("Tahoma",1,12));
        lTen.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lSua = new JLabel();
        lSua.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        lSua.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moFormDonViTinh(ma);
            }
            
        });
        
        JLabel lXoa = new JLabel();
        lXoa.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));
        lXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xoaDonViTinh(ma,ten);
            }
            
        });
        
        panelThaoTac.add(lSua);
        panelThaoTac.add(lXoa);
        
        item.add(lTen);
        item.add(panelThaoTac);
        
        tableDVT.addItem(item);
    }
    
    private void xoaDonViTinh(String ma, String Ten){
        
        if(!ThongBao.taoThongBaoXacNhan((JFrame) SwingUtilities.getWindowAncestor(this), "Xác nhận xóa", Ten, DialogThongBao.MessageIcon.QUESTION)){
            return;
        }
        
        if(DALDonViTinh.kiemTraDVTCoTheXoa(ma)){
            if(DALDonViTinh.xoaDonViTinh(ma)){
                ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
            }else{
                ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Lỗi khi xóa", DialogThongBao.MessageIcon.WARNING);
            }
        }else{
            ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Không thể xóa ĐVT",Ten, DialogThongBao.MessageIcon.WARNING);
        }
        loadDanhSachDonViTinh();
    }
    
    
    private void loadItemDongGoi(String ma, String tenDVT1, String tenDVT2){
        
        TableWebItem item = new TableWebItem();
        item.setId(ma);
        
        JPanel panelThaoTac = new JPanel();
        
        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDongGoi.getItemHeight() - 20) / 2);
        fLayout.setHgap(10);
        panelThaoTac.setLayout(fLayout);
        panelThaoTac.setOpaque(false);
        
        JLabel lbMa = new JLabel(ma);
        JLabel lTen = new JLabel(String.format("%s/%s", tenDVT1,tenDVT2));
        lbMa.setHorizontalAlignment(SwingConstants.CENTER);
        lTen.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lSua = new JLabel();
        lSua.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/edit.png"));
        lSua.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               moFormDVDG(ma);
            }
            
        });
        
        JLabel lXoa = new JLabel();
        lXoa.setIcon(xuLyHinhAnh.LayAnhTuSource(this, "/fpoly/icon/remove.png"));
        lXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xoaDongGoi(ma);
            }
            
        });
        
        lTen.setFont(new Font("Tahoma",1,12));
        lbMa.setFont(new Font("Tahoma",1,12));
        
        panelThaoTac.add(lSua);
        panelThaoTac.add(lXoa);
        
        
        
        item.add(lbMa);
        item.add(lTen);
        item.add(panelThaoTac);
        
        tableDongGoi.addItem(item);
        
    }
    
    private void xoaDongGoi(String ma){
        if(!ThongBao.taoThongBaoXacNhan((JFrame) SwingUtilities.getWindowAncestor(this), "Xác nhận xóa đóng gói", ma, DialogThongBao.MessageIcon.QUESTION)){
            return;
        }
        
        if(DALDonViDongDoi.kiemTraCoTheXoaDVDG(ma)){
            if(DALDonViDongDoi.xoaDVDG(ma)){
                ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
            }else{
                ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Lỗi khi xóa", DialogThongBao.MessageIcon.WARNING);
            }
        }else{
            ThongBao.taoThongBao((JFrame) SwingUtilities.getWindowAncestor(this), "Không thể xóa ĐVĐG",ma, DialogThongBao.MessageIcon.WARNING);
        }
        loadDanhSachDongGoi();
    }
    
    private void loadDanhSachDonViTinh(){
        ResultSet rs = DALKho.loadDanhSachDonViTinh();
        tableDVT.removeAllItem();
        try {
            while(rs.next()){
                loadItemDVT(rs.getString("ma"), rs.getString("ten")); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubTabDonViTinh.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
    }
    
    private void loadDanhSachDongGoi(){
        
         ResultSet rs = DALKho.loadDanhSachDongGoi();
        tableDongGoi.removeAllItem();
        try {
            while(rs.next()){
                
                 loadItemDongGoi(rs.getString("ma"), rs.getString("tendvt1"),rs.getString("tendvt2"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubTabDonViTinh.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
    }
    
    private void moFormDonViTinh(String maDVT){
        Frm_DonViTinh frm = new Frm_DonViTinh((JFrame) SwingUtilities.getWindowAncestor(this),maDVT);
        frm.setVisible(true);
        loadDanhSachDonViTinh();
    }
    
    private void moFormDVDG(String maDVDG){
        Frm_DonViDongGoi fm = new Frm_DonViDongGoi((JFrame) SwingUtilities.getWindowAncestor(this), maDVDG);
        fm.setVisible(true);
        loadDanhSachDongGoi();
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        sButton1 = new com.shady.scontrols.SButton();
        tableDVT = new com.shady.scontrols.STableWeb();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        sButton2 = new com.shady.scontrols.SButton();
        tableDongGoi = new com.shady.scontrols.STableWeb();

        setBackground(new java.awt.Color(247, 247, 247));
        setPreferredSize(new java.awt.Dimension(937, 780));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 780));

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 30));

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton1.setText("Thêm mới");
        sButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton1.setPreferredSize(new java.awt.Dimension(120, 30));
        sButton1.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sButton1.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_Radius(15);
        sButton1.sets_drawBorder(false);
        sButton1.sets_hoverBackground(new java.awt.Color(12, 181, 68));
        sButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 280, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel4);

        tableDVT.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDVT.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDVT.setHeaderHeight(35);
        tableDVT.setHeaderRound(10);
        tableDVT.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDVT.setItemSpace(2);
        tableDVT.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDVT.setPreferredSize(new java.awt.Dimension(400, 700));
        jPanel1.add(tableDVT);

        add(jPanel1);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 780));

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 30));

        sButton2.setBackground(new java.awt.Color(0, 153, 51));
        sButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add.png"))); // NOI18N
        sButton2.setText("Thêm mới");
        sButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton2.setPreferredSize(new java.awt.Dimension(120, 30));
        sButton2.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sButton2.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton2.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton2.sets_Radius(15);
        sButton2.sets_drawBorder(false);
        sButton2.sets_hoverBackground(new java.awt.Color(12, 181, 68));
        sButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(sButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 280, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(sButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5);

        tableDongGoi.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDongGoi.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDongGoi.setHeaderHeight(35);
        tableDongGoi.setHeaderRound(10);
        tableDongGoi.setItemHoverBackground(new java.awt.Color(204, 204, 204));
        tableDongGoi.setItemSpace(2);
        tableDongGoi.setListViewBackground(new java.awt.Color(247, 247, 247));
        tableDongGoi.setPreferredSize(new java.awt.Dimension(400, 700));
        jPanel3.add(tableDongGoi);

        add(jPanel3);
    }// </editor-fold>//GEN-END:initComponents

    private void sButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton1ActionPerformed
       moFormDonViTinh("");
    }//GEN-LAST:event_sButton1ActionPerformed

    private void sButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton2ActionPerformed
       moFormDVDG("");
    }//GEN-LAST:event_sButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SButton sButton2;
    private com.shady.scontrols.STableWeb tableDVT;
    private com.shady.scontrols.STableWeb tableDongGoi;
    // End of variables declaration//GEN-END:variables
}
