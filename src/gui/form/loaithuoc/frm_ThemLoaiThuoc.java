package gui.form.loaithuoc;

import gui.form.thuoc.*;
import gui.form.lothuoc.Frm_LoThuoc;
import com.shady.tableweb.TableWebItem;
import com.shady.tableweb.TableWebSubItem;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALLoaiThuoc;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.LoaiThuoc;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.dto.MyComboBox;
import fpoly.tn.dto.Thuoc;
import gui.form.message.DialogThongBao;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.XuLyInput;
import fpoly.tn.helper.xuLyComboBox;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.lothuoc.Frm_LoThuoc;
import gui.tab.subtab.kho.SubTabNhomThuoc;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_ThemLoaiThuoc extends javax.swing.JDialog {

    LoaiThuoc loaiThuoc;

    public frm_ThemLoaiThuoc(java.awt.Frame parent, String maLoai) {
        super(parent, true);

        initComponents();

        setFormUI();

        loadDataControls();

        loadDataForm(maLoai);
    }

    private void loadDataControls() {
//        loadDataComboDVT();
//        loadDataComboKho();
//        loadDataLoaiThuoc();
    }

    private void loadDataForm(String maLoai) {

        loaiThuoc = new LoaiThuoc();
        loaiThuoc.setMa(maLoai);

        boolean themMoi = CheckValidation.checkStringTrong(maLoai);

        if (themMoi) {
            lbTuaDe.setText("THÊM LOẠI THUỐC");
            cleanControls();

        } else {

            loaiThuoc = DALLoaiThuoc.layThongTinLoaiThuoc(maLoai);
            
            txtMa.setTextValue(loaiThuoc.getMa().trim());
            txtTen.setTextValue(loaiThuoc.getTen());
            txtGhiChu.setText(loaiThuoc.getGhiChu());
            txtMaNhom.setText(loaiThuoc.getMaNhom());
            lbTuaDe.setText(loaiThuoc.getTen());
        }
        txtMa.setEditable(themMoi);

        bntXoa.setEnabled(!themMoi);

    }

    private boolean checkValidate(boolean themMoi) {
        boolean hopLe = true;

        String message = "", message2 = "", message3;

        message3 = "không được để trống";

        String maLoai = txtMa.getTextValue();
        String ten = txtTen.getTextValue();

        if (CheckValidation.checkStringTrong(maLoai)) {
            message2 = "Mã loại thuốc";
            hopLe = false;
        } else if (CheckValidation.checkStringTrong(ten)) {
            message2 = "Tên";
            hopLe = false;
        } else if (themMoi && DALLoaiThuoc.kiemTraMaLoaiThuocTonTai(maLoai)) {
            message = "Mã";
            message2 = maLoai;
            message3 = "đã tồn tại! Điền mã mới";
            hopLe = false;
        }

        if (!hopLe) {
            ThongBao.taoThongBao(null, message, message2, message3, DialogThongBao.MessageIcon.WARNING);
        }

        return hopLe;
    }

    private void setFormUI() {
        setBackground(new Color(0, 0, 0, 0));
        MouseEffect.MouseMovingForm(panelTuaDe, this);

        // scroll
        txtGhiChu.putClientProperty("styleId", "hovering");

    }

    private void clickLuu() {

        boolean themMoi = CheckValidation.checkStringTrong(loaiThuoc.getMa());

        if (!checkValidate(themMoi)) {
            return;
        }

        luuThongTinThuoc(themMoi);

    }

    private void luuThongTinThuoc(boolean themMoi) {

        layThongTinControls(themMoi);

        if (themMoi) {

            if (DALLoaiThuoc.themLoaiThuocMoi(loaiThuoc)) {
                ThongBao.taoThongBao(null, "Thêm thành công", DialogThongBao.MessageIcon.CHECK);
                lbTuaDe.setText(loaiThuoc.getTen());
            } else {
                ThongBao.taoThongBao(null, "Lỗi khi thêm loại thuốc", DialogThongBao.MessageIcon.WARNING);
                loaiThuoc.setMa("");
            }

        } else {
            DALLoaiThuoc.suaLoaiThuoc(loaiThuoc);
            ThongBao.taoThongBao(null, "Lưu thông tin thành công", DialogThongBao.MessageIcon.CHECK);
        }
    }

    private void cleanControls() {

        loaiThuoc = new LoaiThuoc();

        txtMa.setTextValue("");
        txtTen.setTextValue("");
        txtMaNhom.setTextValue("");
        txtGhiChu.setText("");
    }

    private void clickThoat() {
        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn thoát", DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }
        this.dispose();
    }

    private void layThongTinControls(boolean ThemMoi) {

        if (ThemMoi) {
            loaiThuoc.setMa(txtMa.getTextValue());
        }

        loaiThuoc.setTen(txtTen.getTextValue());
        loaiThuoc.setMaNhom(txtMaNhom.getTextValue());
        loaiThuoc.setGhiChu(txtGhiChu.getText());

    }

    private void clickXoa() {

        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa", loaiThuoc.getTen(), DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }

        if (DALThuoc.kiemTraSanPhamCoTrongHoaDon(loaiThuoc.getMa())) {
            ThongBao.taoThongBao(null, "Không thể xóa", loaiThuoc.getTen() + ".", "Sản phẩm này có trong hóa đơn", DialogThongBao.MessageIcon.WARNING);
            return;
        }

//        if (DALThuoc.xoaSanPham(thuoc.getMa())) {
//            ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
//            dispose();
//        } else {
//            ThongBao.taoThongBao(null, "Lỗi khi xóa sản phẩm", DialogThongBao.MessageIcon.WARNING);
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel1 = new com.shady.scontrols.SPanel();
        panelTuaDe = new javax.swing.JPanel();
        lbTuaDe = new javax.swing.JLabel();
        btThoat2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMa = new com.shady.scontrols.STextField();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new com.shady.scontrols.STextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scrollGhiChu = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        bntLuu = new com.shady.scontrols.SButton();
        bntMoi = new com.shady.scontrols.SButton();
        bntXoa = new com.shady.scontrols.SButton();
        bntThoat = new com.shady.scontrols.SButton();
        txtMaNhom = new com.shady.scontrols.STextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_cornerRadius(0);
        sPanel1.sets_dropShadow(true);
        sPanel1.sets_shadowOpacity(0.2F);
        sPanel1.sets_shadowSize(10);

        panelTuaDe.setBackground(new java.awt.Color(82, 130, 216));

        lbTuaDe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTuaDe.setForeground(new java.awt.Color(255, 255, 255));
        lbTuaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/loaithuoc.png"))); // NOI18N
        lbTuaDe.setText("THÊM LOẠI THUỐC");

        btThoat2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btThoat2.setForeground(new java.awt.Color(255, 255, 255));
        btThoat2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btThoat2.setText("X");
        btThoat2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btThoat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btThoat2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuaDeLayout = new javax.swing.GroupLayout(panelTuaDe);
        panelTuaDe.setLayout(panelTuaDeLayout);
        panelTuaDeLayout.setHorizontalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbTuaDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTuaDeLayout.setVerticalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addComponent(lbTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btThoat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Mã loại thuốc");

        txtMa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMa.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconKey.png"))); // NOI18N
        txtMa.sets_maxTextLength(6);
        txtMa.sets_placeholder("Mã");
        txtMa.sets_radius(10);
        txtMa.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tên loại");

        txtTen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTen.sets_placeholder("Tên Thuốc...");
        txtTen.sets_radius(10);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("THÔNG TIN CƠ BẢN");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        scrollGhiChu.setViewportView(txtGhiChu);

        bntLuu.setBackground(new java.awt.Color(248, 246, 246));
        bntLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_save.png"))); // NOI18N
        bntLuu.setText("Lưu");
        bntLuu.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntLuu.sets_Radius(10);
        bntLuu.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLuuActionPerformed(evt);
            }
        });

        bntMoi.setBackground(new java.awt.Color(248, 246, 246));
        bntMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_new_file_20.png"))); // NOI18N
        bntMoi.setText("Làm mới");
        bntMoi.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntMoi.sets_Radius(10);
        bntMoi.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMoiActionPerformed(evt);
            }
        });

        bntXoa.setBackground(new java.awt.Color(248, 246, 246));
        bntXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        bntXoa.setText("Xóa");
        bntXoa.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntXoa.sets_Radius(10);
        bntXoa.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntXoaActionPerformed(evt);
            }
        });

        bntThoat.setBackground(new java.awt.Color(255, 204, 204));
        bntThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_exit_20.png"))); // NOI18N
        bntThoat.setText("Thoát");
        bntThoat.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThoat.sets_Radius(10);
        bntThoat.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntThoat.sets_hoverBackground(new java.awt.Color(240, 240, 240));
        bntThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntThoatActionPerformed(evt);
            }
        });

        txtMaNhom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNhom.sets_placeholder("Mã nhóm...");
        txtMaNhom.sets_radius(10);
        txtMaNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhomActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Mã nhóm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollGhiChu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNhom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addGap(22, 22, 22))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNhom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btThoat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btThoat2MouseClicked
        clickThoat();
    }//GEN-LAST:event_btThoat2MouseClicked

    private void bntLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLuuActionPerformed
        clickLuu();
    }//GEN-LAST:event_bntLuuActionPerformed

    private void bntMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMoiActionPerformed
        loadDataForm("");
    }//GEN-LAST:event_bntMoiActionPerformed

    private void bntThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntThoatActionPerformed
        clickThoat();
    }//GEN-LAST:event_bntThoatActionPerformed

    private void bntXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntXoaActionPerformed
        clickXoa();
    }//GEN-LAST:event_bntXoaActionPerformed

    private void txtMaNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhomActionPerformed

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
            java.util.logging.Logger.getLogger(frm_ThemLoaiThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            frm_ThemLoaiThuoc dialog = new frm_ThemLoaiThuoc(new javax.swing.JFrame(), "");
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SButton bntLuu;
    private com.shady.scontrols.SButton bntMoi;
    private com.shady.scontrols.SButton bntThoat;
    private com.shady.scontrols.SButton bntXoa;
    private javax.swing.JLabel btThoat2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbTuaDe;
    private javax.swing.JPanel panelTuaDe;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JScrollPane scrollGhiChu;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtMa;
    private com.shady.scontrols.STextField txtMaNhom;
    private com.shady.scontrols.STextField txtTen;
    // End of variables declaration//GEN-END:variables
}
