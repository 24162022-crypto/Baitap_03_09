# JPA Web Project (Jakarta EE 10)

Dự án bài tập lập trình web sử dụng Jakarta EE 10 (Servlet 6.0, JSP/JSTL 3.0,
JPA 3.0 + Hibernate 6.4), MySQL, SiteMesh 3, kiến trúc MVC.

## 1. Yêu cầu môi trường

- JDK 22
- Apache Maven
- MySQL Server (đã cài & đang chạy)
- Apache Tomcat 10.x
- Spring Tool Suite (STS) / Eclipse có hỗ trợ Maven + Tomcat 10

## 2. Cài đặt Cơ sở dữ liệu

1. Mở MySQL Workbench / mysql CLI.
2. Chạy toàn bộ nội dung file `sql/jpa_web_db.sql` (script này sẽ tự tạo
   database `jpa_web_db`, bảng `users`, `categories`, và dữ liệu mẫu).
3. Tài khoản admin mẫu: **username: `admin` / password: `admin123`** (role_id = 1).

## 3. Cấu hình kết nối DB

Mở file:

```
src/main/resources/META-INF/persistence.xml
```

Sửa lại 2 dòng sau cho khớp với MySQL trên máy bạn:

```xml
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="root"/>
```

(Mặc định cấu hình đang trỏ tới `jdbc:mysql://localhost:3306/jpa_web_db`).

## 4. Import project vào Spring Tool Suite (STS)

1. Mở STS → `File` → `Import` → `Maven` → `Existing Maven Projects`.
2. Chọn thư mục gốc `jpa-web-project` (chứa `pom.xml`) → `Finish`.
3. Chờ Maven tải dependencies (cần kết nối mạng lần đầu).
4. Chuột phải vào project → `Properties` → `Project Facets`: đảm bảo
   **Dynamic Web Module = 6.0**, **Java = 22**.
5. Chuột phải vào project → `Properties` → `Targeted Runtimes`: chọn
   **Apache Tomcat v10.1**.

## 5. Chạy ứng dụng

1. Chuột phải vào project → `Run As` → `Run on Server` → chọn Tomcat 10.
2. Truy cập: `http://localhost:8080/jpa-web-project/login`

## 6. Cấu trúc chức năng đã hoàn thành

| Chức năng | URL | Ghi chú |
|---|---|---|
| Đăng ký | `GET/POST /register` | Kiểm tra trùng username bằng JPA |
| Đăng nhập | `GET/POST /login` | Lưu User vào HttpSession |
| Đăng xuất | `GET /logout` | Hủy session |
| Danh sách danh mục | `GET /admin/categories` | Yêu cầu đăng nhập role admin |
| Thêm danh mục | `GET /admin/category/add`, `POST /admin/category/insert` | Hỗ trợ URL ảnh hoặc Upload File |
| Xóa danh mục | `GET /admin/category/delete?id=` | Xóa bằng JPA |
| Trang cá nhân | `GET/POST /user/profile` | Cập nhật fullName, phone, avatar (Multipart Upload); tự refresh session |

## 7. Cấu trúc package

```
vn.iotstar.config      -> JpaConfig, SiteMeshFilter
vn.iotstar.entity      -> User, Category
vn.iotstar.dao         -> IUserDao, ICategoryDao (interface)
vn.iotstar.dao.impl    -> UserDaoImpl, CategoryDaoImpl (dùng EntityManager)
vn.iotstar.service     -> IUserService, ICategoryService (interface)
vn.iotstar.service.impl-> UserServiceImpl, CategoryServiceImpl
vn.iotstar.controller  -> LoginController, RegisterController, LogoutController,
                           CategoryController, ProfileController (Servlet)
vn.iotstar.utils       -> Constant
```

## 8. SiteMesh - Layout chung

- File layout: `src/main/webapp/views/layouts/main.jsp` (Header, Menu, Body, Footer).
- Cấu hình decorator & loại trừ trang login/register: `vn.iotstar.config.SiteMeshFilter`
  (đăng ký bằng `@WebFilter`, không cần khai báo tay trong `web.xml`).
- `WEB-INF/decorators.xml` giữ lại mang tính tài liệu tham khảo cấu hình khai báo.

## 9. Thư mục Upload

Ảnh upload (category, avatar) được lưu vào thư mục:
```
<Tomcat webapps>/jpa-web-project/uploads/
```
và đường dẫn tương đối (`/uploads/xxx.jpg`) được lưu vào cột `images` trong CSDL.

## 10. Đẩy code lên GitHub (Public repo)

```bash
git init
git add .
git commit -m "Initial commit - JPA Web Project"
git branch -M main
git remote add origin https://github.com/<your-username>/<repo-name>.git
git push -u origin main
```
