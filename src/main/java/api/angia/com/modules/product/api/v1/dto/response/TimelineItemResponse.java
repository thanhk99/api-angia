package api.angia.com.modules.product.api.v1.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Một cột mốc trong hành trình sản xuất sản phẩm.
 */
@Data
@Builder
public class TimelineItemResponse {

    /** Ngày xảy ra sự kiện (định dạng YYYY-MM-DD) */
    private String date;

    /**
     * Mô tả sự kiện đa ngôn ngữ.
     * Ví dụ: { "vi": "Thu hoạch tại Quảng Nam", "en": "Harvested in Quang Nam" }
     */
    private Map<String, String> event;
}
