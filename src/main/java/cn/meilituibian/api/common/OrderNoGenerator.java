package cn.meilituibian.api.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderNoGenerator {
    public static String getOrderNo() {
        Random rand = new Random();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time) + rand.nextInt(100);
        return localTime;
    }

}
