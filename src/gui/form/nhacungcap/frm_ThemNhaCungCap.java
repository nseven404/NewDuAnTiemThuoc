package gui.form.nhacungcap;

import fpoly.tn.dal.DALNhaCungCap;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.NhaCungCap;
import fpoly.tn.helper.CheckValidation;
import gui.form.message.DialogThongBao;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.ThongBao;
import java.awt.Color;

public class frm_ThemNhaCungCap extends javax.swing.JDialog {

    NhaCungCap nhacc;

    public frm_ThemNhaCungCap(java.awt.Frame parent, String maLoai) {
        super(parent, true);

        initComponents();

        setFormUI();

        loadDataForm(maLoai);
    }

    private void loadDataForm(String maNCC) {

        nhacc = new NhaCungCap();
        nhacc.setMa(maNCC);

        boolean themMoi = CheckValidation.checkStringTrong(maNCC);

        if (themMoi) {
            lbTuaDe.setText("THÊM NHÀ CUNG CẤP");
            cleanControls();

        } else {

            nhacc = DALNhaCungCap.layThongTinNhaCungCap(maNCC);

            txtMaNCC.setTextValue(nhacc.getMa().trim());
            txtTenNCC.setText(nhacc.getTen().trim());
            txtDiaChi.setText(nhacc.getDiachi().trim());
            txtSDT.setTextValue(nhacc.getSdt().trim());
            
            lbTuaDe.setText(nhacc.getTen());
        }
        txtMaNCC.setEditable(themMoi);

    }

    private boolean checkValidate(boolean themMoi) {
        boolean hopLe = false;

        String message = "", message2 = "", message3 = "";

        String maNCC = txtMaNCC.getTextValue();
        String tenNCC = txtTenNCC.getTextValue();
        String diaChi = txtDiaChi.getTextValue();
        String sdt = txtSDT.getTextValue();

        if (CheckValidation.checkStringTrong(maNCC) && CheckValidation.checkStringTrong(tenNCC) 
                && CheckValidation.checkStringTrong(diaChi) && CheckValidation.checkStringTrong(sdt)) {
            message2 = "Thông tin ";
            message3 = "không được để trống";
            txtMaNCC.sets_error(!hopLe);
            txtDiaChi.sets_error(!hopLe);
            txtSDT.sets_error(!hopLe);
            txtTenNCC.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(maNCC)) {
            message2 = "Mã Nhà cung cấp ";
            message3 = "không được để trống";
            txtMaNCC.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(tenNCC)) {
            message2 = "Tên Nhà cung cấp ";
            message3 = "không được để trống";
            txtTenNCC.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(diaChi)) {
            message2 = "Địa chỉ Nhà cung cấp ";
            message3 = "không được để trống";
            txtDiaChi.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(sdt)) {
            message2 = "Số điện thoại của Nhà cung cấp ";
            message3 = "không được để trống";
            txtSDT.sets_error(!hopLe);
        } else if (maNCC.length() > 6){
            message = "Mã Nhà cung cấp tối đa có ";
            message2 = "6";
            message3 = "ký tự.";
            txtMaNCC.sets_error(!hopLe);
        } else if (sdt.length() != 10) {
            message = "Số điện thoại Nhà cung cấp tối thiểu có ";
            message2 = "10";
            message3 = "ký tự số.";
            txtSDT.sets_error(!hopLe);
        } else if (themMoi && DALNhaCungCap.kiemTraMaNhaCungCapTonTai(maNCC)) {
            message = "Mã ";
            message2 = maNCC;
            message3 = " đã tồn tại! Vui lòng điền mã mới";
            txtMaNCC.sets_error(!hopLe);
        } else {
            hopLe = true;
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

        boolean themMoi = CheckValidation.checkStringTrong(nhacc.getMa());

        if (!checkValidate(themMoi)) {
            return;
        }

        luuThongTinNhaCungCap(themMoi);

    }

    private void luuThongTinNhaCungCap(boolean themMoi) {

        layThongTinControls(themMoi);

        if (themMoi) {
            if(checkValidate(themMoi)){
                //ma, ten, diachi, sdt, ghichu
                String ma = txtMaNCC.getTextValue();
                String ten = txtTenNCC.getTextValue();
                String diachi = txtDiaChi.getTextValue();
                String sdt = txtSDT.getTextValue();
                String ghichu = txtGhiChu.getText();
                
                if (DALNhaCungCap.themNhaCungCapMoi(nhacc)) {
                    ThongBao.taoThongBao(null, "Thêm thành công", DialogThongBao.MessageIcon.CHECK);
                    lbTuaDe.setText(nhacc.getTen());
                    this.dispose();
                } else {
                    ThongBao.taoThongBao(null, "Lỗi khi thêm Nhà cung cấp", DialogThongBao.MessageIcon.WARNING);
                }
            }
        } else {
            txtMaNCC.setEnabled(false);
            //ma, ten, diachi, sdt, ghichu
            String ma = txtMaNCC.getTextValue();
            String ten = txtTenNCC.getTextValue();
            String diachi = txtDiaChi.getTextValue();
            String sdt = txtSDT.getTextValue();
            String ghichu = txtGhiChu.getText();
            if (DALNhaCungCap.suaNhaCungCap(nhacc)) {
                ThongBao.taoThongBao(null, "Sửa thông tin thành công", DialogThongBao.MessageIcon.CHECK);
                lbTuaDe.setText(nhacc.getTen());
                this.dispose();
            } else {
                ThongBao.taoThongBao(null, "Lỗi khi sửa thông tin Nhà cung cấp", DialogThongBao.MessageIcon.WARNING);
            }
        }
    }

    private void cleanControls() {

        nhacc = new NhaCungCap();

        txtMaNCC.setTextValue("");
        txtTenNCC.setTextValue("");
        txtDiaChi.setTextValue("");
        txtSDT.setTextValue("");
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
            nhacc.setMa(txtMaNCC.getTextValue());
        }

        nhacc.setTen(txtTenNCC.getTextValue());
        nhacc.setDiachi(txtDiaChi.getTextValue());
        nhacc.setSdt(txtSDT.getTextValue());

    }

    private void clickXoa() {

        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa", nhacc.getTen(), DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }

        if (DALThuoc.kiemTraSanPhamCoTrongHoaDon(nhacc.getMa())) {
            ThongBao.taoThongBao(null, "Không thể xóa", nhacc.getTen() + ".", "Nhà cung cấp này có trong hóa đơn", DialogThongBao.MessageIcon.WARNING);
            return;
        }
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
        txtMaNCC = new com.shady.scontrols.STextField();
        jLabel2 = new javax.swing.JLabel();
        txtSDT = new com.shady.scontrols.STextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scrollGhiChu = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        bntLuu = new com.shady.scontrols.SButton();
        bntMoi = new com.shady.scontrols.SButton();
        bntThoat = new com.shady.scontrols.SButton();
        txtTenNCC = new com.shady.scontrols.STextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiaChi = new com.shady.scontrols.STextField();
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
        lbTuaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/nhacungcap.png"))); // NOI18N
        lbTuaDe.setText("THÊM NHÀ CUNG CẤP");

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
        jLabel1.setText("Mã Nhà cung cấp");

        txtMaNCC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMaNCC.sets_errorBackground(new java.awt.Color(255, 255, 255));
        txtMaNCC.sets_errorBorder(new java.awt.Color(255, 0, 0));
        txtMaNCC.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconKey.png"))); // NOI18N
        txtMaNCC.sets_maxTextLength(6);
        txtMaNCC.sets_placeholder("Mã");
        txtMaNCC.sets_radius(10);
        txtMaNCC.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Số điện thoại liên hệ");

        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSDT.sets_errorBackground(new java.awt.Color(255, 255, 255));
        txtSDT.sets_errorBorder(new java.awt.Color(255, 0, 0));
        txtSDT.sets_maxTextLength(11);
        txtSDT.sets_placeholder("Số điện thoại ...");
        txtSDT.sets_radius(10);
        txtSDT.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER);

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

        txtTenNCC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTenNCC.sets_errorBackground(new java.awt.Color(255, 255, 255));
        txtTenNCC.sets_errorBorder(new java.awt.Color(255, 0, 0));
        txtTenNCC.sets_placeholder("Tên nhà cung cấp...");
        txtTenNCC.sets_radius(10);
        txtTenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNCCActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Tên Nhà cung cấp");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Địa chỉ");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.sets_errorBackground(new java.awt.Color(255, 255, 255));
        txtDiaChi.sets_errorBorder(new java.awt.Color(255, 0, 0));
        txtDiaChi.sets_placeholder("Địa chỉ...");
        txtDiaChi.sets_radius(10);
        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11))
                        .addContainerGap(67, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollGhiChu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenNCC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(txtMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
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
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtTenNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNCCActionPerformed

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

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
            java.util.logging.Logger.getLogger(frm_ThemNhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            frm_ThemNhaCungCap dialog = new frm_ThemNhaCungCap(new javax.swing.JFrame(), "");
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
    private javax.swing.JLabel btThoat2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbTuaDe;
    private javax.swing.JPanel panelTuaDe;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JScrollPane scrollGhiChu;
    private com.shady.scontrols.STextField txtDiaChi;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtMaNCC;
    private com.shady.scontrols.STextField txtSDT;
    private com.shady.scontrols.STextField txtTenNCC;
    // End of variables declaration//GEN-END:variables
}
