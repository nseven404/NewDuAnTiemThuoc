package gui.form.main;

import com.alee.laf.menu.PopupMenuWay;
import com.alee.laf.menu.WebPopupMenu;
import com.shady.event.ItemEvent;
import fpoly.tn.dal.DALNhanVien;
import gui.form.message.DialogThongBao;
import gui.tab.TabCaiDat;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.util.Calendar;
import javax.swing.Timer;
import gui.tab.TabHome;
import gui.tab.TabQuanLyKhachHang;
import gui.tab.TabQuanLyHoaDon;
import gui.tab.TabQuanLyKhoHang;
import gui.tab.TabQuanLyNhanVien;
import gui.tab.TabQuanLyThongKe;
import fpoly.tn.helper.PhanQuyen;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.xuLyHinhAnh;
import gui.form.login.frm_DangNhap;
import gui.form.nhanvien.frm_thongtinnhanvien;
import gui.tab.TabQuanLyNhaCungCap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class frm_Main extends javax.swing.JFrame {

    // mouse position
    private Point mousePosition = null;

    // slider
    boolean openSliderStatus = false;

    // tabLayout
    CardLayout tabMainLayout;

    // tab name
    private final String tabHomeName = "home";
    private final String tabNhanVienName = "tabNhanVien";
    private final String tabKhoHangName = "tabKhoHang";
    private final String tabHoaDonName = "tabHoaDon";
    private final String tabKhachHangName = "tabKhachHang";
    private final String tabNhaCCName = "tabNhaCungCap";
    private final String tabThongKeName = "tabThongKe";
    private final String tabCaiDatName = "tabCaiDat";

    //tab
    TabHome home;

    // nhân viên
    String maNv;
    WebPopupMenu popupSetting;

    public frm_Main(String maNv) {

        initComponents();

        this.maNv = maNv;

        //ten nhan vien
       
        String[] ten = DALNhanVien.layTenNhanVien(maNv).split(" ");
        btnSettingNV.setText(ten[ten.length - 1]);

        initForm();
    }

    private void initForm() {

        // ngay thang
        showDateTime();
        // tab
        initTab();
        // slider effect
        initSlider();
        // header button
        initHeaderButton();

        // setting nhân viên
        initButtonUser();
    }

    private void initButtonUser() {

        initPopupSettingUser();

        btnSettingNV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupSetting.showBelowEnd(btnSettingNV);
            }

        });
    }

    private void initPopupSettingUser() {
        
        popupSetting = new WebPopupMenu();
        popupSetting.setPopupMenuWay(PopupMenuWay.belowEnd);

        JMenuItem itemThongTin = new JMenuItem();
        JMenuItem itemCaiDat = new JMenuItem();
        JMenuItem itemDangXuat = new JMenuItem();
        itemThongTin.setText("Thông tin tài khoản");
        itemCaiDat.setText("Cài đặt");
        
        itemDangXuat.setText("Đăng xuất");

        itemThongTin.addActionListener((ActionEvent arg0) -> {
            frm_thongtinnhanvien frm = new frm_thongtinnhanvien(this,maNv, false, false);
            frm.setVisible(true);
        });
        
        itemDangXuat.addActionListener((ActionEvent arg0) -> {
            frm_DangNhap frm = new frm_DangNhap(openSliderStatus);
            frm.setVisible(true);
            this.dispose();  
        });
        
        itemCaiDat.addActionListener((ActionEvent arg0) -> {
        
           
        });
        
        popupSetting.add(itemThongTin);
        popupSetting.add(itemCaiDat);
        popupSetting.addSeparator();
        popupSetting.add(itemDangXuat);
        
    }

    private void initHeaderButton() {
        btnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thuNhoForm();
            }

        });
        btnFormSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

        });
    }

    private void initTab() {
        tabMainLayout = (CardLayout) tabMain.getLayout();
        home = new TabHome();
        loadTabHome(); // load tab home đầu tiên
    }

    private void initSlider() {
        
        boolean admin  = DALNhanVien.nhanVienLaAdmin(DALNhanVien.LayChucVuNV(maNv));
        
        mainSlider.addButtonSlider(btnSlider);
        
        mainSlider.addItem(tabHomeName, "Home", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabHome.png"));
        mainSlider.addItem(tabKhoHangName, "KHO HÀNG", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabKhoHang.png"));
        mainSlider.addItem(tabHoaDonName, "HÓA ĐƠN", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabDonHang.png"));
        
        if(admin){
            mainSlider.addItem(tabNhanVienName, "NHÂN VIÊN", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabUser.png")); 
        }
        
        mainSlider.addItem(tabKhachHangName, "KHÁCH HÀNG", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabKhachHang.png"));
        mainSlider.addItem(tabNhaCCName, "NHÀ CUNG CẤP",xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabNhaCungCap.png"));
        mainSlider.addItem(tabThongKeName, "THỐNG KÊ", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconTabThongKe.png"));
        mainSlider.addItem(tabCaiDatName, "CÀI ĐẶT", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconSetting.png"));
        mainSlider.addItem("dangxuat", "ĐĂNG XUẤT", xuLyHinhAnh.LayAnhTuSource(mainSlider, "/fpoly/tn/icon/iconLogout.png"));
        
        mainSlider.setSelectedItem(tabHomeName);

        mainSlider.addItemEventListener((ItemEvent ie) -> {

            switch (ie.getEventType()) {
                case 0: // exit
                    
                    break;
                case 1: // hover
                    break;
                case 2: // click
                    if (PhanQuyen.nhanVienCoTheMoTab(ie.getSelectName())) {
                        moTab(ie.getSelectName());
                    } else {
                        ThongBao.taoThongBao(this, "Chức năng của Quản Lý", DialogThongBao.MessageIcon.WARNING);
                    }
                    break;

            }
        });
       

    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainSlider = new com.shady.scontrols.SSlider();
        mainHeader = new javax.swing.JPanel();
        khungHeaderLeft = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSlider = new javax.swing.JLabel();
        khungHeaderMid = new javax.swing.JPanel();
        textNgayThang = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtGio = new javax.swing.JLabel();
        khungHeaderRight = new javax.swing.JPanel();
        btnSettingNV = new javax.swing.JLabel();
        btnFormSize = new javax.swing.JLabel();
        btnThongBao = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JLabel();
        tabMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý nhân viên");
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        mainSlider.setBackground(new java.awt.Color(25, 38, 61));
        mainSlider.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0, 0));
        mainSlider.setItemBackground(new java.awt.Color(25, 38, 61));
        mainSlider.setItemForeGround(new java.awt.Color(255, 255, 255));
        mainSlider.setItemHoverBackground(new java.awt.Color(62, 74, 95));
        mainSlider.setItemSelectBackground(new java.awt.Color(62, 74, 95));
        mainSlider.setItemTextIconSpace(30);
        mainSlider.setItemsMarginLeft(20);
        getContentPane().add(mainSlider, java.awt.BorderLayout.WEST);

        mainHeader.setBackground(new java.awt.Color(255, 255, 255));
        mainHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(243, 243, 243)));
        mainHeader.setPreferredSize(new java.awt.Dimension(990, 50));
        mainHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mainHeaderMouseDragged(evt);
            }
        });
        mainHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mainHeaderMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mainHeaderMouseReleased(evt);
            }
        });
        mainHeader.setLayout(new java.awt.GridBagLayout());

        khungHeaderLeft.setMinimumSize(new java.awt.Dimension(235, 10));
        khungHeaderLeft.setOpaque(false);
        khungHeaderLeft.setPreferredSize(new java.awt.Dimension(100, 20));
        khungHeaderLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 12));
        khungHeaderLeft.add(jLabel1);

        btnSlider.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSlider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/menu.png"))); // NOI18N
        btnSlider.setPreferredSize(new java.awt.Dimension(34, 25));
        khungHeaderLeft.add(btnSlider);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        mainHeader.add(khungHeaderLeft, gridBagConstraints);

        khungHeaderMid.setMinimumSize(new java.awt.Dimension(119, 50));
        khungHeaderMid.setOpaque(false);
        khungHeaderMid.setPreferredSize(new java.awt.Dimension(786, 50));
        khungHeaderMid.setLayout(new java.awt.BorderLayout());

        textNgayThang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textNgayThang.setForeground(new java.awt.Color(102, 102, 102));
        textNgayThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textNgayThang.setText("Thứ Hai, 23 Tháng 10");
        textNgayThang.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        textNgayThang.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        khungHeaderMid.add(textNgayThang, java.awt.BorderLayout.CENTER);

        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(588, 25));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 4));

        txtGio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtGio.setForeground(new java.awt.Color(102, 102, 102));
        txtGio.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        txtGio.setText("00:00");
        txtGio.setToolTipText("");
        txtGio.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(txtGio);

        khungHeaderMid.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainHeader.add(khungHeaderMid, gridBagConstraints);

        khungHeaderRight.setMinimumSize(new java.awt.Dimension(235, 0));
        khungHeaderRight.setOpaque(false);
        khungHeaderRight.setPreferredSize(new java.awt.Dimension(235, 54));

        btnSettingNV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSettingNV.setForeground(new java.awt.Color(102, 102, 102));
        btnSettingNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSettingNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/user.png"))); // NOI18N
        btnSettingNV.setText("Tên NV");
        btnSettingNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnFormSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnFormSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/restore_down.png"))); // NOI18N
        btnFormSize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnThongBao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThongBao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconBell.png"))); // NOI18N
        btnThongBao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel5.setBackground(new java.awt.Color(153, 153, 153));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("|");

        btnMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconMinimize.png"))); // NOI18N
        btnMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout khungHeaderRightLayout = new javax.swing.GroupLayout(khungHeaderRight);
        khungHeaderRight.setLayout(khungHeaderRightLayout);
        khungHeaderRightLayout.setHorizontalGroup(
            khungHeaderRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khungHeaderRightLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFormSize, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongBao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnSettingNV, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        khungHeaderRightLayout.setVerticalGroup(
            khungHeaderRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khungHeaderRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khungHeaderRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSettingNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFormSize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThongBao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMinimize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        mainHeader.add(khungHeaderRight, gridBagConstraints);

        getContentPane().add(mainHeader, java.awt.BorderLayout.PAGE_START);

        tabMain.setBackground(new java.awt.Color(255, 255, 255));
        tabMain.setLayout(new java.awt.CardLayout());
        getContentPane().add(tabMain, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mainHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainHeaderMouseDragged
        Point currCoords = evt.getLocationOnScreen();
        this.setLocation(currCoords.x - mousePosition.x, currCoords.y - mousePosition.y);
    }//GEN-LAST:event_mainHeaderMouseDragged

    private void mainHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainHeaderMousePressed
        mousePosition = evt.getPoint();
    }//GEN-LAST:event_mainHeaderMousePressed

    private void mainHeaderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainHeaderMouseReleased
        mousePosition = null;
    }//GEN-LAST:event_mainHeaderMouseReleased

    /**
     * ****** HÀM XỬ LÝ ******
     */
    private void dangXuat() {
        frm_DangNhap frmDN = new frm_DangNhap(true);
        frmDN.setVisible(true);
        this.dispose();
        
    }

    private void thuNhoForm() {
        this.setState(Frame.ICONIFIED);
    }

    // Thêm tab
    private void moTab(String tabName) {

        if (tabName.equals("dangxuat")) {
            dangXuat();
        }

        if (!daCoTab(tabName)) {

            JPanel tab = new JPanel();

            switch (tabName) {
                case tabNhanVienName: {
                    tab = new TabQuanLyNhanVien(maNv);
                    break;
                }
                case tabHoaDonName: {
                    tab = new TabQuanLyHoaDon(maNv);
                    break;
                }
                case tabKhoHangName: {
                    tab = new TabQuanLyKhoHang();
                    break;
                }
                case tabThongKeName: {
                    tab = new TabQuanLyThongKe();
                    break;
                }
                case tabKhachHangName: {
                    tab = new TabQuanLyKhachHang();
                    break;
                }
                case tabNhaCCName: {
                    tab = new TabQuanLyNhaCungCap();
                    break;
                }
                case tabCaiDatName: {
                    tab = new TabCaiDat();
                    break;
                }
                case tabHomeName: {
                    tab = new TabCaiDat();
                    break;
                }
            }

            tab.setName(tabName);
            tabMain.add(tab, tabName);

        }

        showTab(tabName);
    }

    private void loadTabHome() {
        tabMain.add(home, tabHomeName);
    }

    private void showTab(String tabName) {
        tabMainLayout.show(tabMain, tabName);
    }

    // kiểm tra tab tồn tại
    private boolean daCoTab(String tabName) {
        boolean daCoTab = false;
        int tabCount = tabMain.getComponentCount();

        for (int i = 0; i < tabCount; i++) {
            Component tabItem = tabMain.getComponent(i);
            if (tabItem.getName().equals(tabName)) {
                daCoTab = true;
                break;
            }
        }
        return daCoTab;
    }

    // show date time
    private void showDateTime() {
        setNgayThang();
        chayGio();
    }

    private void setNgayThang() {
        Calendar calendar = Calendar.getInstance();
        //    int nam = calendar.get(Calendar.YEAR);
        int thang = calendar.get(Calendar.MONTH);
        int ngay = calendar.get(Calendar.DATE);

        String thu;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                thu = "Thứ Hai";
                break;
            case Calendar.TUESDAY:
                thu = "Thứ Ba";
                break;
            case Calendar.WEDNESDAY:
                thu = "Thứ Tư";
                break;
            case Calendar.THURSDAY:
                thu = "Thứ Năm";
                break;
            case Calendar.FRIDAY:
                thu = "Thứ Sáu";
                break;
            case Calendar.SATURDAY:
                thu = "Thứ Bảy";
                break;
            case Calendar.SUNDAY:
                thu = "Chủ Nhật";
                break;
            default:
                thu = "Thứ Hai";
        }
        String ngaythang = String.format("%s, %d Tháng %s", thu, ngay, thang);
        textNgayThang.setText(ngaythang);
    }

    private void chayGio() {
        // hiển thị lần đầu
        int gio = LocalTime.now().getHour();
        int phut = LocalTime.now().getMinute();
        txtGio.setText(String.format("%02d:%02d", gio,phut));

        new Timer(500, (ActionEvent e) -> {
            
            int gio1 = LocalTime.now().getHour();
            int phut1 = LocalTime.now().getMinute();
            
            txtGio.setText(String.format("%02d:%02d", gio1,phut1));
        }).start();

    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new frm_Main("NV001").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnFormSize;
    private javax.swing.JLabel btnMinimize;
    private javax.swing.JLabel btnSettingNV;
    private javax.swing.JLabel btnSlider;
    private javax.swing.JLabel btnThongBao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel khungHeaderLeft;
    private javax.swing.JPanel khungHeaderMid;
    private javax.swing.JPanel khungHeaderRight;
    private javax.swing.JPanel mainHeader;
    private com.shady.scontrols.SSlider mainSlider;
    private javax.swing.JPanel tabMain;
    private javax.swing.JLabel textNgayThang;
    private javax.swing.JLabel txtGio;
    // End of variables declaration//GEN-END:variables
}
