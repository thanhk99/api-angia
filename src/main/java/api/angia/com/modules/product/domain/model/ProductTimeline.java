package api.angia.com.modules.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Value Object — hành trình sản xuất tại một cột mốc thời gian.
 * date: Ngày xảy ra sự kiện (YYYY-MM-DD)
 * event: Mô tả sự kiện đa ngôn ngữ (vi, en)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTimeline {

    /** Ngày xảy ra sự kiện, định dạng YYYY-MM-DD */
    private String date;

    /**
     * Mô tả sự kiện đa ngôn ngữ.
     * Key: mã ngôn ngữ (ví dụ: "vi", "en")
     * Value: nội dung mô tả
     * Ví dụ: { "vi": "Thu hoạch tại Quảng Nam", "en": "Harvested in Quang Nam" }
     */
    private Map<String, String> event;
}
