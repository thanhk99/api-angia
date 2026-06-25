package api.angia.com.shared.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class RateLimitingService {

    private final ProxyManager<byte[]> proxyManager;

    public RateLimitingService(ProxyManager<byte[]> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket resolveBucket(String key, int capacity, int refillTokens, int refillDurationSeconds) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierFor(capacity, refillTokens, refillDurationSeconds);
        // ProxyManager.builder() được sử dụng cho việc lấy bucket từ Redis.
        return proxyManager.builder().build(key.getBytes(), configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierFor(int capacity, int refillTokens, int refillDurationSeconds) {
        return () -> {
            Refill refill = Refill.greedy(refillTokens, Duration.ofSeconds(refillDurationSeconds));
            Bandwidth limit = Bandwidth.classic(capacity, refill);
            return BucketConfiguration.builder()
                    .addLimit(limit)
                    .build();
        };
    }
}
