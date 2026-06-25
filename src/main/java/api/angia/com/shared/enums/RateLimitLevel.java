package api.angia.com.shared.enums;

public enum RateLimitLevel {
    IP,         // Giới hạn theo IP (chống DDoS, spam chung)
    USER,       // Giới hạn theo ID người dùng (chống lạm dụng API sau đăng nhập)
    ENDPOINT    // Giới hạn theo Endpoint cụ thể
}
