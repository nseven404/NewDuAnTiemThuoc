package gui.form.chonAnh;


import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.xuLyHinhAnh;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class frm_ChonAnh extends javax.swing.JDialog {

    //Khai báo biến
    Point mousePosition;
    // path file
    private static String pathAnhCu;

    // path file
    private static String pathAnhMoi;

    // trạng thái load ảnh
    private static boolean anhHopLe = true;

    public frm_ChonAnh(java.awt.Frame parent, boolean modal, String path) {
        super(parent, modal);

        initComponents();
        
        setFormUI();
        setMouseEffect();

        // tạo event cho textSearch
        setTextLinkEvent();

        pathAnhCu = path;

        // load ảnh từ path
        if (path != null) {
            if (path.length() > 1) {
                loadImage(path);
            }
        }
        
        
    }
    
    private void setFormUI(){
        // transparent form
        setBackground(new Color(0, 0, 0, 0));
       
        setMouseEffect();
        
    }
    
    private void setMouseEffect(){
        MouseEffect.MouseMovingForm(panelTuaDe, this);
        
        MouseEffect.addMouseEffect(btnThoat, Color.red, new Color(102,102,102), Color.white, Color.white);
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel2 = new com.shady.scontrols.SPanel();
        panelTuaDe = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnThoat = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        imageView = new javax.swing.JLabel();
        txtUrl = new com.shady.scontrols.STextField();
        bntXacNhan = new com.shady.scontrols.SButton();
        btnThoat2 = new com.shady.scontrols.SButton();

        setBackground(new java.awt.Color(243, 243, 243));
        setUndecorated(true);

        sPanel2.setBackground(new java.awt.Color(255, 255, 255));
        sPanel2.sets_dropShadow(true);
        sPanel2.sets_shadowOpacity(0.3F);
        sPanel2.sets_shadowSize(10);

        panelTuaDe.setBackground(new java.awt.Color(102, 102, 102));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconEditImage.png"))); // NOI18N
        jLabel7.setText("Thêm ảnh mới");
        jLabel7.setIconTextGap(10);

        btnThoat.setBackground(new java.awt.Color(102, 102, 102));
        btnThoat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThoat.setText("x");
        btnThoat.setToolTipText("Thoát");
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.setName(""); // NOI18N
        btnThoat.setOpaque(true);
        btnThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuaDeLayout = new javax.swing.GroupLayout(panelTuaDe);
        panelTuaDe.setLayout(panelTuaDeLayout);
        panelTuaDeLayout.setHorizontalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTuaDeLayout.setVerticalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(243, 243, 243));
        jPanel1.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Link ảnh internet");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("HOẶC");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconFolder.png"))); // NOI18N
        jLabel1.setText("Thư mục máy tính");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        imageView.setBackground(new java.awt.Color(255, 255, 255));
        imageView.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageView.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        imageView.setOpaque(true);

        txtUrl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUrl.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLink.png"))); // NOI18N
        txtUrl.sets_iconLeftPadding(10);
        txtUrl.sets_placeholder("Link ảnh ...");
        txtUrl.sets_radius(5);

        bntXacNhan.setBackground(new java.awt.Color(255, 255, 255));
        bntXacNhan.setForeground(new java.awt.Color(102, 102, 102));
        bntXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_save.png"))); // NOI18N
        bntXacNhan.setText("Lưu");
        bntXacNhan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntXacNhan.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntXacNhan.sets_Radius(10);
        bntXacNhan.sets_hoverBackground(new java.awt.Color(244, 244, 244));
        bntXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntXacNhanActionPerformed(evt);
            }
        });

        btnThoat2.setBackground(new java.awt.Color(255, 255, 255));
        btnThoat2.setForeground(new java.awt.Color(102, 102, 102));
        btnThoat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        btnThoat2.setText("Thoát");
        btnThoat2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThoat2.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat2.sets_Radius(10);
        btnThoat2.sets_hoverBackground(new java.awt.Color(244, 244, 244));
        btnThoat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoat2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(bntXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addComponent(imageView, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imageView, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sPanel2Layout = new javax.swing.GroupLayout(sPanel2);
        sPanel2.setLayout(sPanel2Layout);
        sPanel2Layout.setHorizontalGroup(
            sPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sPanel2Layout.setVerticalGroup(
            sPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel2Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatMouseClicked
        xacNhanThoat();
    }//GEN-LAST:event_btnThoatMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        moFileChosse();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void bntXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntXacNhanActionPerformed
        xacNhanThoat();
    }//GEN-LAST:event_bntXacNhanActionPerformed

    private void btnThoat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoat2ActionPerformed
        xacNhanThoat();
    }//GEN-LAST:event_btnThoat2ActionPerformed

    // HÀM XỬ LÝ
    
    // lấy data từ form khác
    public static String getResultValue() {
        return anhHopLe ? pathAnhMoi : pathAnhCu;
    }

    private void xacNhanThoat() {
        this.dispose();
    }

    private void loadImage(String path) {
        try {
            loadImageFromLink(path);
        } catch (IOException ex) {
            try {
                File sourceimage = new File(path);
                Image image = ImageIO.read(sourceimage);
                setImageIcon(image);
            } catch (IOException ex2) {
                setImageError();
            }
        }
    }

    private void moFileChosse() {

        FileDialog fd = new FileDialog(this, "Chọn file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.jpg;*.png;*.gif");
        fd.setVisible(true);
        String filename = fd.getDirectory();

        if (filename != null) {
            pathAnhMoi = fd.getDirectory() + fd.getFile();
            try {
                File sourceimage = new File(pathAnhMoi);
                Image image = ImageIO.read(sourceimage);
                setImageIcon(image);
            } catch (IOException ex) {
                setImageError();
                anhHopLe = false;
            }
        }
    }

    // load textSreach event
    private void setTextLinkEvent() {
        txtUrl.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    loadImageFromLink(txtUrl.getTextValue());
                } catch (IOException ex) {
                    anhHopLe = false;
                    setImageError();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    loadImageFromLink(txtUrl.getTextValue());
                } catch (IOException ex) {
                    anhHopLe = false;
                    setImageError();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    loadImageFromLink(txtUrl.getTextValue());
                } catch (IOException ex) {
                    anhHopLe = false;
                    setImageError();
                }
            }
        });
    }

    private void loadImageFromLink(String link) throws IOException {

        Image image;
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        conn.connect();
        InputStream urlStream = conn.getInputStream();
        image = ImageIO.read(urlStream);
        setImageIcon(image);
    }

    private void setImageIcon(Image image) {

        // kiểm tra độ lớn ảnh
        BufferedImage buffImage = xuLyHinhAnh.imageToBufferedImage(image);
        buffImage = xuLyHinhAnh.resizeImage(buffImage, imageView.getWidth(), true);

        imageView.setIcon(new ImageIcon(buffImage));
    }

    private void setImageError() {
        imageView.setIcon(new ImageIcon(getClass().getResource("/fpoly/tn/icon/iconError.png")));
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
            java.util.logging.Logger.getLogger(frm_ChonAnh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            frm_ChonAnh dialog = new frm_ChonAnh(new javax.swing.JFrame(), true, null);
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
    public static com.shady.scontrols.SButton bntXacNhan;
    public static javax.swing.JLabel btnThoat;
    public static com.shady.scontrols.SButton btnThoat2;
    public static javax.swing.JLabel imageView;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JSeparator jSeparator2;
    public static javax.swing.JSeparator jSeparator3;
    public static javax.swing.JPanel panelTuaDe;
    public static com.shady.scontrols.SPanel sPanel2;
    public static com.shady.scontrols.STextField txtUrl;
    // End of variables declaration//GEN-END:variables
}
