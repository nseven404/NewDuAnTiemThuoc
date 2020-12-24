package gui.form.nhanvien;

import com.datepick.DatePick;
import fpoly.tn.dal.DALChucVu;
import fpoly.tn.dal.DALNhanVien;
import fpoly.tn.dto.NhanVien;
import gui.form.message.DialogThongBao.MessageIcon;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyComboBox;
import gui.form.message.DialogThongBao;
import java.awt.Color;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class frm_thongtinnhanvien extends javax.swing.JDialog {

    // khai bao bien
    NhanVien nhanVien;
    DatePick datePickNgaySinh = new DatePick();

    boolean themMoi = false;
    boolean adminEdit = false;
    boolean lockNhanVien = false;

    public frm_thongtinnhanvien(JFrame frame,String manv, boolean themMoi, boolean adminEdit) {
        super(frame,true);
        initComponents();

        setFormUI();
        nhanVien = new NhanVien();
        // lấy thông tin ban đầu
        nhanVien.setMa(manv);

        this.themMoi = themMoi;
        this.adminEdit = adminEdit;
        loadFormData();

        //Lấy thông tin nhân viên
        loadThongTinNhanVien(manv);
        
        //trạng thái form
        datTrangThaiControlForm();
    }

    private void loadFormData() {
        // set datepick
        datePickNgaySinh.setButton(btnNgaySinh);

        // đặt icon frame
        ImageIcon img = new ImageIcon("./iconForm_addNV.png");
        setIconImage(img.getImage());

        // load combo chức vụ
        loadComboChucVu();
    }

    private void loadComboChucVu() {
        ResultSet rs = DALChucVu.layDanhSachChucVu();
        xuLyComboBox.addData(comboChucVu, rs, "ma", "ten");
    }

    private void loadThongTinNhanVien(String manv) {

        nhanVien.setLock(false);

        NhanVien nv = DALNhanVien.layThongTinNhanVien(manv);
        txtMaNhanVien.setTextValue(nv.getMa());
        txtTen.setTextValue(nv.getTen());
        radioNam.setSelected(nv.getGioitinh() > 0);
        radioNu.setSelected(nv.getGioitinh() == 0);
        datePickNgaySinh.setSelectDate(NgayThang.doiDinhDangNgay(nv.getNgaysinh(), "yyyy-MM-dd", "dd-MM-yyyy"));
        txtCmnd.setTextValue(nv.getCmnd());
        txtDiaChi.setTextValue(nv.getDiachi());
        txtSdt.setTextValue(nv.getSodienthoai());
        txttaikhoan.setTextValue(nv.getTaikhoan());
        txtMatKhau.setTextValue(nv.getMatkhau());
        txtGhiChu.setTextValue(nv.getGhichu());
        xuLyComboBox.setSelectValue(comboChucVu, nv.getMacv());

        lockNhanVien = nhanVien.isLock();
        setTrangThaiNutKhoa(lockNhanVien);
    }

    private void luuThongTinNhanVien() {

        boolean luuThanhCong;
        
        if(!checkValidation(themMoi))
            return;

        if (themMoi) {

            //lay thong tin
            String ma = txtMaNhanVien.getTextValue();
            String ten = txtTen.getTextValue();
            int gioitinh = radioNam.isSelected() ? 1 : 0;
            String ngaysinh = datePickNgaySinh.getSelectedDate();
            String cmnd = txtCmnd.getTextValue();
            String diachi = txtDiaChi.getTextValue();
            String sodienthoai = txtSdt.getTextValue();
            String taikhoan = txttaikhoan.getTextValue();
            String matkhau = txtMatKhau.getTextValue();
            String ghichu = txtGhiChu.getTextValue();
            String macv = xuLyComboBox.getSelectedValue(comboChucVu);
            boolean lock = lockNhanVien;

            nhanVien = new NhanVien(ma, ten, gioitinh, ngaysinh, cmnd, diachi, sodienthoai, taikhoan, matkhau, ghichu, macv, lock);

            luuThanhCong = DALNhanVien.themMoiNhanVien(nhanVien);
            themMoi = false;

        } else {

            String ten = txtTen.getTextValue();
            int gioitinh = radioNam.isSelected() ? 1 : 0;
            String ngaysinh = datePickNgaySinh.getSelectedDate();
            String cmnd = txtCmnd.getTextValue();
            String diachi = txtDiaChi.getTextValue();
            String sodienthoai = txtSdt.getTextValue();
            String ghichu = txtGhiChu.getTextValue();
            String macv = xuLyComboBox.getSelectedValue(comboChucVu);
            boolean lock = lockNhanVien;

            nhanVien.setTen(ten);
            nhanVien.setDiachi(diachi);
            nhanVien.setSodienthoai(sodienthoai);
            nhanVien.setGhichu(ghichu);
            nhanVien.setMacv(macv);
            nhanVien.setLock(lock);
            nhanVien.setGioitinh(gioitinh);
            nhanVien.setNgaysinh(ngaysinh);
            nhanVien.setCmnd(cmnd);

            luuThanhCong = DALNhanVien.suaThongTinNhanVien(nhanVien);
        }

        if (luuThanhCong) {
            ThongBao.taoThongBao(null, "Lưu thông tin thành công", DialogThongBao.MessageIcon.CHECK);
        } else {
            ThongBao.taoThongBao(null, "Lỗi lưu thông tin", DialogThongBao.MessageIcon.WARNING);
        }

        datTrangThaiControlForm();
    }

    private void datTrangThaiControlForm() {
        
        if(themMoi){
            txtMaNhanVien.setEnabled(true);
            txttaikhoan.setEnabled(true);
            txtMatKhau.setEnabled(true);
            
        }else{
            txtMaNhanVien.setEnabled(false);
            txttaikhoan.setEnabled(false);
            txtMatKhau.setEnabled(false);
        }
        
        if(!adminEdit){
            comboChucVu.setEnabled(false);
        }
    }

    private void moFormDoiMatKhau() {
        
        if (adminEdit) {
            ThongBao.taoThongBao(null, "Bạn không thể sửa mật khẩu của nhân viên", MessageIcon.WARNING);
            return;
        }
        if(themMoi)
            return;
        
        frm_doimatkhau frm = new frm_doimatkhau(null, nhanVien.getMa());
        frm.setVisible(true);
    }

    private void clickKhoa() {
        lockNhanVien = !lockNhanVien;
        setTrangThaiNutKhoa(lockNhanVien);
    }

    private void setTrangThaiNutKhoa(boolean lock) {

        String path = lock ? "/fpoly/icon/lockuser.png" : "/fpoly/icon/unlockcuser.png";
        ImageIcon Icon = new javax.swing.ImageIcon(getClass().getResource(path));
        btnKhoa.setIcon(Icon);

    }

    private void setFormUI() {
        setBackground(new Color(0, 0, 0, 0));
        setMouseEffect();
    }

    private void setMouseEffect() {

        MouseEffect.addMouseEffect(bntThoat, Color.red, new Color(102, 102, 102, 0), Color.white, Color.white);
        // set form moving panel
        MouseEffect.MouseMovingForm(panelTuade, this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupGioiTinh = new javax.swing.ButtonGroup();
        sPanel1 = new com.shady.scontrols.SPanel();
        panelTuade = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        bntThoat = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtMaNhanVien = new com.shady.scontrols.STextField();
        txttaikhoan = new com.shady.scontrols.STextField();
        txtTen = new com.shady.scontrols.STextField();
        txtMatKhau = new com.shady.scontrols.STextField();
        txtSdt = new com.shady.scontrols.STextField();
        btnNgaySinh = new com.shady.scontrols.SButton();
        txtDiaChi = new com.shady.scontrols.STextField();
        txtGhiChu = new com.shady.scontrols.STextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCmnd = new com.shady.scontrols.STextField();
        jLabel8 = new javax.swing.JLabel();
        radioNam = new javax.swing.JRadioButton();
        radioNu = new javax.swing.JRadioButton();
        btnDoiMatKhau = new com.shady.scontrols.SButton();
        jLabel13 = new javax.swing.JLabel();
        comboChucVu = new javax.swing.JComboBox<>();
        btnKhoa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnLuu = new com.shady.scontrols.SButton();
        sButton3 = new com.shady.scontrols.SButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_drawborder(true);
        sPanel1.sets_dropShadow(true);
        sPanel1.sets_shadowOpacity(0.3F);
        sPanel1.sets_shadowSize(10);

        panelTuade.setBackground(new java.awt.Color(0, 153, 255));
        panelTuade.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Thông tin nhân viên");

        bntThoat.setBackground(new java.awt.Color(0, 153, 255));
        bntThoat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bntThoat.setForeground(new java.awt.Color(255, 255, 255));
        bntThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bntThoat.setText("x");
        bntThoat.setToolTipText("Thoát");
        bntThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThoat.setName(""); // NOI18N
        bntThoat.setOpaque(true);
        bntThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntThoatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuadeLayout = new javax.swing.GroupLayout(panelTuade);
        panelTuade.setLayout(panelTuadeLayout);
        panelTuadeLayout.setHorizontalGroup(
            panelTuadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuadeLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)
                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelTuadeLayout.setVerticalGroup(
            panelTuadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtMaNhanVien.setEditable(false);
        txtMaNhanVien.setForeground(new java.awt.Color(102, 102, 102));
        txtMaNhanVien.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtMaNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaNhanVien.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtMaNhanVien.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtMaNhanVien.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtMaNhanVien.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconKey.png"))); // NOI18N
        txtMaNhanVien.sets_iconLeftPadding(8);
        txtMaNhanVien.sets_placeholder("Mã . . .");
        txtMaNhanVien.sets_radius(5);
        txtMaNhanVien.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        txttaikhoan.setEditable(false);
        txttaikhoan.setForeground(new java.awt.Color(102, 102, 102));
        txttaikhoan.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txttaikhoan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txttaikhoan.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txttaikhoan.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txttaikhoan.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txttaikhoan.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_user.png"))); // NOI18N
        txttaikhoan.sets_iconLeftPadding(8);
        txttaikhoan.sets_placeholder("Tài khoản ...");
        txttaikhoan.sets_radius(5);
        txttaikhoan.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        txtTen.setForeground(new java.awt.Color(51, 102, 255));
        txtTen.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtTen.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtTen.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtTen.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtTen.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconText.png"))); // NOI18N
        txtTen.sets_iconLeftPadding(8);
        txtTen.sets_placeholder("Tên. . .");
        txtTen.sets_radius(5);

        txtMatKhau.setEditable(false);
        txtMatKhau.setForeground(new java.awt.Color(51, 102, 255));
        txtMatKhau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMatKhau.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtMatKhau.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtMatKhau.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtMatKhau.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_password.png"))); // NOI18N
        txtMatKhau.sets_iconLeftPadding(8);
        txtMatKhau.sets_placeholder("Mật khẩu ...");
        txtMatKhau.sets_radius(5);
        txtMatKhau.sets_textType(com.shady.scontrols.STextField.textTypeEnum.PASSWORD);
        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });

        txtSdt.setForeground(new java.awt.Color(51, 102, 255));
        txtSdt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSdt.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtSdt.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtSdt.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtSdt.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconPhoneUser.png"))); // NOI18N
        txtSdt.sets_iconLeftPadding(8);
        txtSdt.sets_maxTextLength(10);
        txtSdt.sets_placeholder("SĐT. . .");
        txtSdt.sets_radius(5);
        txtSdt.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER);

        btnNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        btnNgaySinh.setForeground(new java.awt.Color(102, 102, 102));
        btnNgaySinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLich.png"))); // NOI18N
        btnNgaySinh.setText("00-00-0000");
        btnNgaySinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNgaySinh.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgaySinh.sets_Radius(4);
        btnNgaySinh.setSelected(true);

        txtDiaChi.setForeground(new java.awt.Color(51, 102, 255));
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiaChi.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtDiaChi.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtDiaChi.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtDiaChi.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconMap.png"))); // NOI18N
        txtDiaChi.sets_iconLeftPadding(8);
        txtDiaChi.sets_placeholder("Địa chỉ...");
        txtDiaChi.sets_radius(5);

        txtGhiChu.setForeground(new java.awt.Color(51, 102, 255));
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGhiChu.sets_errorBackground(new java.awt.Color(255, 255, 255));
        txtGhiChu.sets_errorBorder(new java.awt.Color(255, 51, 51));
        txtGhiChu.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtGhiChu.sets_placeholder("Ghi chú ...");
        txtGhiChu.sets_radius(5);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Mã nhân viên");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Tài khoản");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mật khẩu");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Số điện thoại");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Ngày sinh");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Địa chỉ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Giới tính:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Ghi chú");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Chứng minh nhân dân");

        txtCmnd.setForeground(new java.awt.Color(51, 102, 255));
        txtCmnd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCmnd.sets_errorBackground(new java.awt.Color(255, 204, 204));
        txtCmnd.sets_errorBorder(new java.awt.Color(255, 204, 204));
        txtCmnd.sets_errorIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png"))); // NOI18N
        txtCmnd.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconPhoneUser.png"))); // NOI18N
        txtCmnd.sets_iconLeftPadding(8);
        txtCmnd.sets_maxTextLength(9);
        txtCmnd.sets_placeholder("Số CMND ... ");
        txtCmnd.sets_radius(5);
        txtCmnd.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Chức vụ");

        groupGioiTinh.add(radioNam);
        radioNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioNam.setSelected(true);
        radioNam.setText("Nam");
        radioNam.setOpaque(false);
        radioNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNamActionPerformed(evt);
            }
        });

        groupGioiTinh.add(radioNu);
        radioNu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioNu.setText("Nữ");
        radioNu.setOpaque(false);
        radioNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNuActionPerformed(evt);
            }
        });

        btnDoiMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setBorder(null);
        btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/edit.png"))); // NOI18N
        btnDoiMatKhau.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoiMatKhau.sets_drawBorder(false);
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Khóa");

        btnKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/unlockcuser.png"))); // NOI18N
        btnKhoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKhoaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtGhiChu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCmnd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txttaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(comboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(radioNam)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(radioNu)))))
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKhoa)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKhoa)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCmnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(comboChucVu))
                            .addComponent(radioNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radioNu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        btnLuu.setBackground(new java.awt.Color(247, 246, 246));
        btnLuu.setText("Lưu");
        btnLuu.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLuu.sets_Radius(10);
        btnLuu.sets_borderColor(new java.awt.Color(153, 153, 153));
        btnLuu.sets_hoverBackground(new java.awt.Color(238, 238, 238));
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });
        jPanel2.add(btnLuu);

        sButton3.setBackground(new java.awt.Color(247, 246, 246));
        sButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        sButton3.setText("Thoát");
        sButton3.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sButton3.sets_Radius(10);
        sButton3.sets_borderColor(new java.awt.Color(153, 153, 153));
        sButton3.sets_hoverBackground(new java.awt.Color(238, 238, 238));
        sButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(sButton3);

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(276, 276, 276))
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTuade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // tabDanhSachNhanVien.loadSQL();
    }//GEN-LAST:event_formWindowClosing

    private void khungFormMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khungFormMouseDragged

    }//GEN-LAST:event_khungFormMouseDragged

    private void bntThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntThoatMouseClicked

        this.dispose();
    }//GEN-LAST:event_bntThoatMouseClicked

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        luuThongTinNhanVien();

    }//GEN-LAST:event_btnLuuActionPerformed

    private void sButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_sButton3ActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        moFormDoiMatKhau();
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void radioNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNamActionPerformed
        if (radioNam.isSelected() == true) {
            radioNu.setSelected(false);
        } else {
            radioNu.setSelected(true);
        }
    }//GEN-LAST:event_radioNamActionPerformed

    private void radioNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNuActionPerformed
        if (radioNu.isSelected() == true) {
            radioNam.setSelected(false);
        } else {
            radioNam.setSelected(true);
        }
    }//GEN-LAST:event_radioNuActionPerformed

    private void btnKhoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhoaMouseClicked
        clickKhoa();
    }//GEN-LAST:event_btnKhoaMouseClicked

    private boolean checkValidation(boolean themMoi) {
        boolean hopLe = false;
        String thongBao1 = "";
        String thongBao2 = "";
        String thongBao3 = "";

        // ngay thang
        String ngaysinh = datePickNgaySinh.getSelectedDate();
        String ngayHienTai = NgayThang.layNgayThangNamFormat("dd-MM-yyyy");
        long khoangCachNgay = NgayThang.layKhoangCachNgay(ngaysinh, ngayHienTai, "dd-MM-yyyy");

        if (themMoi && CheckValidation.checkStringTrong(txtMaNhanVien.getTextValue())) {
            thongBao2 = "Mã Nhân Viên";
            thongBao3 = " không được để trống";
            txtMaNhanVien.sets_error(!hopLe);
        }

        if (CheckValidation.checkStringTrong(txtTen.getTextValue())) {
            thongBao2 = "Họ và tên nhân viên";
            thongBao3 = " không được để trống";
            txtTen.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(txtTen.getTextValue())) {
            thongBao2 = "Tên nhân viên";
            thongBao3 = " không được để trống";
            txtTen.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(txtSdt.getTextValue())) {
            thongBao2 = "Số điện thoại";
            thongBao3 = " không được để trống";
            txtSdt.sets_error(!hopLe);
        } else if (txtSdt.getTextValue().length() < 10) {
            thongBao2 = "Số điện thoại";
            thongBao3 = " phải đủ 10 số";
            txtSdt.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(txtDiaChi.getTextValue())) {
            thongBao2 = "Địa chỉ";
            thongBao3 = " không được để trống";
            txtDiaChi.sets_error(!hopLe);
        } else if (khoangCachNgay / 365 < 15) {
            thongBao1 = "Tuổi nhân viên phải trên";
            thongBao2 = " 15 tuổi";
        } else if (CheckValidation.checkStringTrong(txtCmnd.getTextValue())) {
            thongBao2 = "Số Chứng minh nhân dân";
            thongBao3 = " không được để trống";
            txtCmnd.sets_error(!hopLe);
        } else if (txtCmnd.getTextValue().length() < 9) {
            thongBao1 = "Số Chứng minh nhân dân tối thiểu ";
            thongBao2 = "9";
            thongBao3 = " số";
            txtCmnd.sets_error(!hopLe);
        } else if (txtCmnd.getTextValue().length() > 12) {
            thongBao1 = "Số Chứng minh nhân dân tối đa ";
            thongBao2 = "12";
            thongBao3 = " số";
            txtCmnd.sets_error(!hopLe);
        } else if (CheckValidation.checkStringTrong(txtDiaChi.getTextValue())) {
            thongBao2 = "Địa chỉ";
            thongBao3 = " không được để trống";
            txtDiaChi.sets_error(!hopLe);
        } else {
            hopLe = true;
        }
        
        if(!themMoi && DALNhanVien.checkSoDienThoaiSuaTrung(nhanVien.getMa(), txtSdt.getTextValue())){
            thongBao1 = "SĐT";
            thongBao2 = txtSdt.getTextValue();
            thongBao3 = " đã tồn tại";
            txtSdt.sets_error(!hopLe);
            hopLe = false;
        }
        
        if(themMoi && DALNhanVien.checkSoDienThoaiThemMoiTrung(txtSdt.getTextValue())){
            thongBao1 = "SĐT";
            thongBao2 = txtSdt.getTextValue();
            thongBao3 = " đã tồn tại";
            txtSdt.sets_error(!hopLe);
            hopLe = false;
        }
        
        //tennv, gioitinh, ngaysinh, cmnd, diachi, sdt, ghichu, manv
        // hiện thông báo
        if (!hopLe) {
            ThongBao.taoThongBao(null, thongBao1, thongBao2, thongBao3, MessageIcon.WARNING);
        }
        return hopLe;
    }

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
            java.util.logging.Logger.getLogger(frm_thongtinnhanvien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            
            frm_thongtinnhanvien dialog = new frm_thongtinnhanvien(null,"", false,false);
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
    public static javax.swing.JLabel bntThoat;
    public static com.shady.scontrols.SButton btnDoiMatKhau;
    public static javax.swing.JLabel btnKhoa;
    public static com.shady.scontrols.SButton btnLuu;
    public static com.shady.scontrols.SButton btnNgaySinh;
    public static javax.swing.JComboBox<String> comboChucVu;
    public static javax.swing.ButtonGroup groupGioiTinh;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    public static javax.swing.JPanel panelTuade;
    public static javax.swing.JRadioButton radioNam;
    public static javax.swing.JRadioButton radioNu;
    public static com.shady.scontrols.SButton sButton3;
    public static com.shady.scontrols.SPanel sPanel1;
    public static com.shady.scontrols.STextField txtCmnd;
    public static com.shady.scontrols.STextField txtDiaChi;
    public static com.shady.scontrols.STextField txtGhiChu;
    public static com.shady.scontrols.STextField txtMaNhanVien;
    public static com.shady.scontrols.STextField txtMatKhau;
    public static com.shady.scontrols.STextField txtSdt;
    public static com.shady.scontrols.STextField txtTen;
    public static com.shady.scontrols.STextField txttaikhoan;
    // End of variables declaration//GEN-END:variables
}
