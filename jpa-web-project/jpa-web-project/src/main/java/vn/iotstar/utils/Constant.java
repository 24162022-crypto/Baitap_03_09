package vn.iotstar.utils;

/**
 * Class chứa các hằng số dùng chung trong toàn bộ ứng dụng.
 */
public class Constant {

    // Tên đơn vị persistence khai báo trong persistence.xml
    public static final String PERSISTENCE_UNIT_NAME = "jpa_web_pu";

    // Key lưu đối tượng User đang đăng nhập trong HttpSession
    public static final String SESSION_USER = "SESSION_USER";

    // Thư mục vật lý (trên server) dùng để lưu file upload
    public static final String UPLOAD_DIR = "uploads";

    // Đường dẫn URL public trỏ tới thư mục upload (dùng trong JSP để hiển thị ảnh)
    public static final String UPLOAD_URL = "/uploads";

    // Role mặc định
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 2;

    // Trạng thái category
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    private Constant() {
        // Không cho phép khởi tạo
    }
}
