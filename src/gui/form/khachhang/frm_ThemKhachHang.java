package gui.form.khachhang;

import fpoly.tn.dal.DALKhachHang;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.KhachHang;
import fpoly.tn.helper.CheckValidation;
import gui.form.message.DialogThongBao;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.ThongBao;
import java.awt.Color;

public class frm_ThemKhachHang extends javax.swing.JDialog {

    KhachHang khachHang;

    public frm_ThemKhachHang(java.awt.Frame parent, String maKH) {
        super(parent, true);

        initComponents();
        // set datepick
        datePickKhachhang.setButton(btnNgaySinh);
        setFormUI();

        loadDataForm(maKH);
    }

    private void loadDataForm(String maKH) {

        khachHang = new KhachHang();
        khachHang.setMa(maKH);

        boolean themMoi = CheckValidation.checkStringTrong(maKH);

        if (themMoi) {
            lbTuaDe.setText("THÊM KHÁCH HÀNG");
            cleanControls();

        } else {
            khachHang = DALKhachHang.laythongtinKhachHang(maKH);
            txtMa.setTextValue(khachHang.getMa().trim());
            txtTen.setTextValue(khachHang.getTen());
            datePickKhachhang.setSelectDate(NgayThang.doiDinhDangNgay(khachHang.getNgaysinh(), "yyyy-MM-dd", "dd-MM-yyyy"));
            txtCMND.setTextValue(khachHang.getCmnd());
            if (khachHang.isGioitinh()) {
                rdbNam.setSelected(true);
            } else {
                rdbNu.setSelected(true);
            }
            txtDiaChi.setTextValue(khachHang.getDiachi());
            txtGhiChu.setText(khachHang.getGhichu());
            lbTuaDe.setText(khachHang.getTen());
        }
        txtMa.setEditable(themMoi);

        bntXoa.setEnabled(!themMoi);

    }

    private boolean checkValidate(boolean themMoi) {
        boolean hopLe = false;

        String message = "", message2 = "", message3;

        message3 = "không được để trống";

        String maKH = txtMa.getTextValue();
        String ten = txtTen.getTextValue();
        String cmnd = txtCMND.getTextValue();
        String diaChi = txtDiaChi.getTextValue();

        // ngay thang
        String ngaysinh = datePickKhachhang.getSelectedDate();
        String ngayHienTai = NgayThang.layNgayThangNamFormat("dd-MM-yyyy");
        long khoangCachNgay = NgayThang.layKhoangCachNgay(ngaysinh, ngayHienTai, "dd-MM-yyyy");

        if (CheckValidation.checkStringTrong(maKH)) {
            message2 = "Mã khách hàng";
            txtMa.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(ten)) {
            message2 = "Tên";
            txtTen.sets_error(!hopLe);
        } else if (khoangCachNgay / 365 < 15) {
            message2 = "Tuổi nhân viên phải trên";
            message3 = "15 tuổi";
        } else if (CheckValidation.checkStringTrong(cmnd)) {
            message2 = "CMND";
            txtCMND.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(diaChi)) {
            message2 = "Địa chỉ";
            txtDiaChi.sets_error(!hopLe);
        } else if (cmnd.length() > 12) {
            message2 = "CMND";
            message3 = "không được lớn hơn 10 số";
            txtCMND.sets_error(!hopLe);
        } else if (cmnd.length() < 9) {
            message2 = "CMND";
            message3 = "phải là 9 số";
            txtCMND.sets_error(!hopLe);
        } else if (themMoi && DALKhachHang.kiemTraMaLoaiKhachHangTonTai(maKH)) {
            message = "Mã";
            message2 = maKH;
            message3 = "đã tồn tại! Điền mã mới";
            txtMa.sets_error(!hopLe);
        } else if (themMoi && DALKhachHang.kiemTraCMNDTonTai(cmnd)) {
            message = "CMND";
            message2 = cmnd;
            message3 = "đã tồn tại! Điền số mới";
            txtCMND.sets_error(!hopLe);
        }else{
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

        boolean themMoi = CheckValidation.checkStringTrong(khachHang.getMa());

        if (!checkValidate(themMoi)) {
            return;
        }

        luuThongTinKhachHang(themMoi);

    }

    private void luuThongTinKhachHang(boolean themMoi) {

        layThongTinControls(themMoi);

        if (themMoi) {

            if (DALKhachHang.themKhachHang(khachHang)) {
                ThongBao.taoThongBao(null, "Thêm thành công", DialogThongBao.MessageIcon.CHECK);
                lbTuaDe.setText(khachHang.getTen());
            } else {
                ThongBao.taoThongBao(null, "Lỗi khi thêm khách hàng", DialogThongBao.MessageIcon.WARNING);
                khachHang.setMa("");
            }
        } else {
           if( DALKhachHang.suaKhachHang(khachHang))
            ThongBao.taoThongBao(null, "Lưu thông tin thành công", DialogThongBao.MessageIcon.CHECK);
           else
               ThongBao.taoThongBao(null, "Lỗi khi lưu thông tin khách hàng", DialogThongBao.MessageIcon.WARNING);
        }
    }

    private void cleanControls() {

        khachHang = new KhachHang();

        txtMa.setTextValue("");
        txtTen.setTextValue("");
        txtCMND.setTextValue("");
        txtDiaChi.setTextValue("");
        txtGhiChu.setText("");
        rdbNam.isSelected();
    }

    private void clickThoat() {
        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn thoát", DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }
        this.dispose();
    }

    private void layThongTinControls(boolean ThemMoi) {

        if (ThemMoi) {
            khachHang.setMa(txtMa.getTextValue());
        }

        khachHang.setTen(txtTen.getTextValue());
        khachHang.setNgaysinh(NgayThang.doiDinhDangNgay(datePickKhachhang.getSelectedDate(), "dd-MM-yyyy","yyyy-MM-dd"));
        khachHang.setGioitinh(rdbNam.isSelected());
        khachHang.setCmnd(txtCMND.getTextValue());
        khachHang.setDiachi(txtDiaChi.getTextValue());
        khachHang.setGhichu(txtGhiChu.getText());

    }

    private void clickXoa() {

        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa", khachHang.getTen(), DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }

        if (DALThuoc.kiemTraSanPhamCoTrongHoaDon(khachHang.getMa())) {
            ThongBao.taoThongBao(null, "Không thể xóa", khachHang.getTen() + ".", "Khách hàng này có trong hóa đơn", DialogThongBao.MessageIcon.WARNING);
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

        btnGroup = new javax.swing.ButtonGroup();
        datePickKhachhang = new com.datepick.DatePick();
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
        jLabel3 = new javax.swing.JLabel();
        btnNgaySinh = new com.shady.scontrols.SButton();
        txtCMND = new com.shady.scontrols.STextField();
        lblCMND = new javax.swing.JLabel();
        txtDiaChi = new com.shady.scontrols.STextField();
        lblCMND1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        bntLuu = new com.shady.scontrols.SButton();
        bntMoi = new com.shady.scontrols.SButton();
        bntXoa = new com.shady.scontrols.SButton();
        bntThoat = new com.shady.scontrols.SButton();

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
        lbTuaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/addKhachHang.png"))); // NOI18N
        lbTuaDe.setText("THÊM KHÁCH HÀNG");

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
        jLabel1.setText("Mã khách hàng");

        txtMa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMa.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtMa.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtMa.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtMa.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconKey.png"))); // NOI18N
        txtMa.sets_maxTextLength(6);
        txtMa.sets_placeholder("Mã");
        txtMa.sets_radius(10);
        txtMa.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tên khách hàng");

        txtTen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTen.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtTen.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtTen.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtTen.sets_placeholder("Tên khách hàng...");
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Ngày sinh");

        btnNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        btnNgaySinh.setForeground(new java.awt.Color(102, 102, 102));
        btnNgaySinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLich.png"))); // NOI18N
        btnNgaySinh.setText("00-00-0000");
        btnNgaySinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNgaySinh.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgaySinh.sets_Radius(4);
        btnNgaySinh.setSelected(true);
        btnNgaySinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnNgaySinhFocusGained(evt);
            }
        });

        txtCMND.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCMND.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtCMND.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtCMND.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtCMND.sets_placeholder("CMND...");
        txtCMND.sets_radius(10);
        txtCMND.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER);

        lblCMND.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblCMND.setForeground(new java.awt.Color(102, 102, 102));
        lblCMND.setText("CMND");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDiaChi.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtDiaChi.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtDiaChi.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtDiaChi.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconMap.png"))); // NOI18N
        txtDiaChi.sets_placeholder("Địa chỉ...");
        txtDiaChi.sets_radius(10);

        lblCMND1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblCMND1.setForeground(new java.awt.Color(102, 102, 102));
        lblCMND1.setText("Địa chỉ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Giới tính");

        btnGroup.add(rdbNam);
        rdbNam.setSelected(true);
        rdbNam.setText("Nam");
        rdbNam.setPreferredSize(new java.awt.Dimension(65, 30));

        btnGroup.add(rdbNu);
        rdbNu.setText("Nữ");
        rdbNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNuActionPerformed(evt);
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(313, 313, 313)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCMND)))
                                    .addComponent(jLabel3)
                                    .addComponent(btnNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbNu))
                            .addComponent(lblCMND1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scrollGhiChu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCMND)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(lblCMND1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

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

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
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

    private void btnNgaySinhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnNgaySinhFocusGained

    }//GEN-LAST:event_btnNgaySinhFocusGained

    private void rdbNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbNuActionPerformed

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
            java.util.logging.Logger.getLogger(frm_ThemKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            frm_ThemKhachHang dialog = new frm_ThemKhachHang(new javax.swing.JFrame(), "");
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
    private javax.swing.ButtonGroup btnGroup;
    private com.shady.scontrols.SButton btnNgaySinh;
    private com.datepick.DatePick datePickKhachhang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbTuaDe;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblCMND1;
    private javax.swing.JPanel panelTuaDe;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JScrollPane scrollGhiChu;
    private com.shady.scontrols.STextField txtCMND;
    private com.shady.scontrols.STextField txtDiaChi;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtMa;
    private com.shady.scontrols.STextField txtTen;
    // End of variables declaration//GEN-END:variables
}
