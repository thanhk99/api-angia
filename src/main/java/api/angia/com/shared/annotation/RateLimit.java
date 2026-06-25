package api.angia.com.shared.annotation;

import api.angia.com.shared.enums.RateLimitLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * Mức độ bảo vệ áp dụng.
     */
    RateLimitLevel level() default RateLimitLevel.IP;

    /**
     * Số lượng request tối đa (capacity).
     */
    int capacity() default 100;

    /**
     * Số lượng token được thêm vào mỗi khoảng thời gian.
     */
    int refillTokens() default 100;

    /**
     * Khoảng thời gian để refill token (tính bằng giây).
     */
    int refillDurationSeconds() default 60;
}
