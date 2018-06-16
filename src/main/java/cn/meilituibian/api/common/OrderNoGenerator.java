package cn.meilituibian.api.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderNoGenerator {
    public static String generateOrderNo(Long userId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNo = localDateTime.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        return orderNo + userId;
    }
}
