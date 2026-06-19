package api.angia.com.shared.monitor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Order(1)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final List<String> SKIP_PATHS = Arrays.asList(
            "/actuator", "/v3/api-docs", "/swagger-ui", "/swagger-resources"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - start;

            String method = requestWrapper.getMethod();
            String uri = requestWrapper.getRequestURI();
            String query = requestWrapper.getQueryString();

            log.info("--> {} {}{}", method, uri, query != null ? "?" + query : "");

            if (log.isDebugEnabled()) {
                String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (!requestBody.isBlank()) {
                    log.debug("--> Request body: {}", requestBody);
                }
            }

            int status = responseWrapper.getStatus();
            log.info("<-- {} {} ({}ms)", method, status, duration);

            if (log.isDebugEnabled()) {
                String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (!responseBody.isBlank()) {
                    log.debug("<-- Response body: {}", responseBody);
                }
            }

            responseWrapper.copyBodyToResponse();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (!contextPath.isEmpty() && path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        return SKIP_PATHS.stream().anyMatch(path::startsWith);
    }
}
