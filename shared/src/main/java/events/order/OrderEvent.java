package events.order;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record OrderEvent(
        Long orderId,
        OrderEventType type,
        String eventId,
        Instant eventTime,
        String source, // hangi servis üretti
        Map<String, Object>payload // opsiyonel, dinamik alanlar
) {

    // compact constructor runs *after* canonical constructor
    public OrderEvent {
        if (eventId == null || eventId.isBlank()) {
            eventId = UUID.randomUUID().toString();
        }
        if (eventTime == null) {
            eventTime = Instant.now();
        }
    }

    // convenience factory method (callers don’t pass nulls)
    public static OrderEvent of(Long orderId, OrderEventType type, String source, Map<String, Object> payload) {
        return new OrderEvent(orderId, type, null, null, source, payload);
    }
}
