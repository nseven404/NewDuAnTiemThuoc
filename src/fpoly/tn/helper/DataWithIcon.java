package fpoly.tn.helper;

import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class DataWithIcon {

    protected ImageIcon icon;
    protected Object data;
    protected String path;

    public DataWithIcon(Object data, String path,int size) {
        this.data = data;
        this.path = path;
        try {
            this.icon = xuLyHinhAnh.taoAnhVienTron(path, size);
        } catch (IOException ex) {
            System.out.println("lỗi datawithicon " +ex.getMessage());
        }
    }

    public Icon getIcon() {
        return icon;
    }

    public Object getData() {
        return data;
    }

    public String toString() {
        return data.toString();
    }
    
    public String getPath(){
        return path;
    }

}

