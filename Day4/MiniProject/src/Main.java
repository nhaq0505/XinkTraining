
import engine.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        AnalyticsEngine engine = new AnalyticsEngine();

        // Sửa lại phương thức start() trong AnalyticsEngine trước khi chạy
        engine.start();

        Random random = new Random();
        String[] eventTypes = {"click", "view", "purchase", "login", "logout"};

        // Tạo và gửi 10 sự kiện mẫu
        System.out.println("Đang gửi các sự kiện mẫu...");
        for (int i = 0; i < 10; i++) {
            String eventType = eventTypes[random.nextInt(eventTypes.length)];
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", "user_" + random.nextInt(100));
            payload.put("value", random.nextInt(1000));

            Event event = new Event(eventType, System.currentTimeMillis(), payload);
            engine.ingest(event);
            System.out.println("Đã gửi sự kiện: " + event);

            // Tạm dừng một chút giữa các sự kiện
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Chờ để cho scheduler có thời gian xử lý
        try {
            System.out.println("Đang chờ xử lý sự kiện...");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Kết thúc chương trình một cách sạch sẽ
        System.out.println("Hoàn tất, đang thoát...");
        System.exit(0);
    }
}