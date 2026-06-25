package api.angia.com.shared.interceptor;

import api.angia.com.shared.annotation.RateLimit;
import api.angia.com.shared.enums.RateLimitLevel;
import api.angia.com.shared.service.RateLimitingService;
import api.angia.com.shared.exception.RateLimitExceededException;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitingService rateLimitingService;

    public RateLimitInterceptor(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);

        // Nếu endpoint không có annotation RateLimit, có thể áp dụng giới hạn mặc định hoặc bỏ qua.
        // Ở đây chúng ta tạm thời bỏ qua nếu không đánh dấu.
        if (rateLimit == null) {
            return true;
        }

        String key = generateKey(request, rateLimit.level(), handlerMethod);
        
        Bucket bucket = rateLimitingService.resolveBucket(
                key, 
                rateLimit.capacity(), 
                rateLimit.refillTokens(), 
                rateLimit.refillDurationSeconds()
        );

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        
        if (probe.isConsumed()) {
            // Cho phép request và thêm header thông tin số token còn lại
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            // Từ chối request vì vượt quá giới hạn
            long waitForRefill = probe.getNanosToWaitForRefill() / TimeUnit.SECONDS.toNanos(1);
            throw new RateLimitExceededException(waitForRefill);
        }
    }

    private String generateKey(HttpServletRequest request, RateLimitLevel level, HandlerMethod handlerMethod) {
        return switch (level) {
            case IP -> "rate_limit:ip:" + getClientIP(request);
            // Cần lấy user_id từ request (ví dụ qua jwt token, filter), tạm mock
            case USER -> "rate_limit:user:" + (request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : getClientIP(request));
            case ENDPOINT -> "rate_limit:endpoint:" + handlerMethod.getMethod().getName() + ":" + getClientIP(request);
        };
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
