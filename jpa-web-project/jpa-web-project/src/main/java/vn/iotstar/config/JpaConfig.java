package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.iotstar.utils.Constant;

/**
 * Lớp cấu hình JPA - khởi tạo và quản lý EntityManagerFactory dùng chung
 * (singleton) cho toàn bộ ứng dụng.
 */
public class JpaConfig {

    private static volatile EntityManagerFactory entityManagerFactory;

    private JpaConfig() {
    }

    /**
     * Lấy EntityManagerFactory (khởi tạo 1 lần duy nhất - thread-safe).
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            synchronized (JpaConfig.class) {
                if (entityManagerFactory == null) {
                    entityManagerFactory = Persistence
                            .createEntityManagerFactory(Constant.PERSISTENCE_UNIT_NAME);
                }
            }
        }
        return entityManagerFactory;
    }

    /**
     * Tạo mới một EntityManager cho mỗi lượt thao tác DAO.
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Đóng EntityManagerFactory khi ứng dụng shutdown (gọi từ một
     * ServletContextListener nếu cần).
     */
    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
