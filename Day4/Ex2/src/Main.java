
import model.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DEMO MẠNG XÃ HỘI SỬ DỤNG BUILDER PATTERN ===");

        // Khởi tạo mạng xã hội
        SocialNetwork network = new SocialNetwork();

        // 1. Tạo và thêm người dùng
        System.out.println("\n1. TẠO VÀ THÊM NGƯỜI DÙNG");

        User user1 = new Builder()
                .setId("1")
                .setName("Nguyễn Văn A")
                .addProfileInfo("location", "Hà Nội")
                .addProfileInfo("age", "25")
                .build();

        User user2 = new Builder()
                .setId("2")
                .setName("Trần Thị B")
                .addProfileInfo("location", "TP.HCM")
                .build();

        User user3 = new Builder()
                .setId("3")
                .setName("Lê Văn C")
                .addProfileInfo("job", "Developer")
                .build();

        User user4 = new Builder()
                .setId("4")
                .setName("Phạm Thị D")
                .build();

        User user5 = new Builder()
                .setId("5")
                .setName("Hoàng Văn E")
                .build();

        // Thêm người dùng vào mạng
        network.addUser(user1);
        network.addUser(user2);
        network.addUser(user3);
        network.addUser(user4);
        network.addUser(user5);

        System.out.println("Đã thêm 5 người dùng vào mạng xã hội");

        // Hiển thị danh sách người dùng
        System.out.println("\nDanh sách người dùng:");
        List<User> allUsers = network.getAllUser();
        for (User user : allUsers) {
            System.out.println("- " + user.getName() + " (ID: " + user.getId() + ")");
            Map<String, String> profileInfo = user.getProfileInfo();
            if (!profileInfo.isEmpty()) {
                System.out.println("  Thông tin cá nhân:");
                for (Map.Entry<String, String> entry : profileInfo.entrySet()) {
                    System.out.println("  + " + entry.getKey() + ": " + entry.getValue());
                }
            }
        }

        // 2. Tạo kết nối giữa người dùng
        System.out.println("\n2. TẠO KẾT NỐI GIỮA NGƯỜI DÙNG");

        network.connect("1", "2");
        network.connect("1", "3");
        network.connect("2", "3");
        network.connect("2", "4");
        network.connect("3", "4");
        network.connect("4", "5");

        System.out.println("Đã tạo các kết nối giữa người dùng");

        // Hiển thị kết nối của mỗi người dùng
        System.out.println("\nKết nối của các người dùng:");
        for (User user : allUsers) {
            Set<String> userConnections = network.getConnections(user.getId());
            System.out.print(user.getName() + " kết nối với: ");

            if (userConnections != null && !userConnections.isEmpty()) {
                List<String> connectionNames = new ArrayList<>();
                for (String connectionId : userConnections) {
                    User connectionUser = network.getUser(connectionId);
                    if (connectionUser != null) {
                        connectionNames.add(connectionUser.getName());
                    }
                }
                System.out.println(String.join(", ", connectionNames));
            } else {
                System.out.println("Không có kết nối");
            }
        }

        // 3. Tạo và thêm bài đăng
        System.out.println("\n3. TẠO VÀ THÊM BÀI ĐĂNG");

        Post post1 = new Post.PostBuilder()
                .setId("p1")
                .setUserId("1")
                .setContent("Xin chào mọi người! Đây là bài đăng đầu tiên của tôi.")
                .build();

        Post post2 = new Post.PostBuilder()
                .setId("p2")
                .setUserId("2")
                .setContent("Hôm nay trời đẹp quá!")
                .build();

        Post post3 = new Post.PostBuilder()
                .setId("p3")
                .setUserId("1")
                .setContent("Tôi đang học Java Builder Pattern.")
                .build();

        Post post4 = new Post.PostBuilder()
                .setId("p4")
                .setUserId("3")
                .setContent("Lập trình thật thú vị!")
                .build();

        // Thêm bài đăng vào mạng
        network.addPost("1", post1);
        network.addPost("2", post2);
        network.addPost("1", post3);
        network.addPost("3", post4);

        System.out.println("Đã thêm 4 bài đăng vào mạng xã hội");

        // Hiển thị bài đăng của từng người dùng
        System.out.println("\nBài đăng của các người dùng:");
        for (User user : allUsers) {
            List<Post> userPosts = network.getUserPosts(user.getId());
            System.out.println(user.getName() + " có " +
                    (userPosts != null ? userPosts.size() : 0) + " bài đăng");
        }

        // 4. Demo xóa người dùng
        System.out.println("\n4. XÓA NGƯỜI DÙNG");

        User userToRemove = network.getUser("5");
        if (userToRemove != null) {
            System.out.println("Xóa người dùng: " + userToRemove.getName());
            boolean removed = network.removeUser("5");
            System.out.println("Kết quả xóa: " + (removed ? "Thành công" : "Thất bại"));

            // Kiểm tra lại danh sách người dùng
            System.out.println("\nDanh sách người dùng sau khi xóa:");
            allUsers = network.getAllUser();
            for (User user : allUsers) {
                System.out.println("- " + user.getName() + " (ID: " + user.getId() + ")");
            }
        }

        // 5. Demo ngắt kết nối
        System.out.println("\n5. NGẮT KẾT NỐI");

        System.out.println("Ngắt kết nối giữa Nguyễn Văn A và Lê Văn C");
        boolean disconnected = network.disconnect("1", "3");
        System.out.println("Kết quả ngắt kết nối: " + (disconnected ? "Thành công" : "Thất bại"));

        // Hiển thị lại kết nối của người dùng
        System.out.println("\nKết nối của Nguyễn Văn A sau khi ngắt:");
        Set<String> connections1 = network.getConnections("1");
        if (connections1 != null && !connections1.isEmpty()) {
            for (String connectionId : connections1) {
                User connectionUser = network.getUser(connectionId);
                if (connectionUser != null) {
                    System.out.println("- " + connectionUser.getName());
                }
            }
        } else {
            System.out.println("Không có kết nối");
        }

        System.out.println("\n=== KẾT THÚC DEMO ===");
    }
}