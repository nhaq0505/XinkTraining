package engine;

public class AnomalyDetector {
    private final CircularBuffer<DataPoint> recentPoints;
    private final double zScoreThreshold;

    public AnomalyDetector(int windowSize, double zScoreThreshold) {
        this.recentPoints = new CircularBuffer<>(windowSize);
        this.zScoreThreshold = zScoreThreshold;
    }

    public boolean detectAnomaly(DataPoint newPoint) {
        // Thêm điểm mới vào buffer
        try {
            recentPoints.add(newPoint);
        } catch (IllegalStateException e) {
            // Buffer đầy, loại bỏ điểm cũ nhất
            recentPoints.poll();
            recentPoints.add(newPoint);
        }

        // Nếu chưa đủ điểm để phát hiện bất thường
        if (recentPoints.size() < 5) {
            return false;
        }

        // Tính trung bình và độ lệch chuẩn của các giá trị gần đây
        double sum = 0;
        double count = 0;

        for (int i = 0; i < recentPoints.size(); i++) {
            DataPoint point = recentPoints.get(i);
            sum += point.getValue();
            count++;
        }

        double mean = sum / count;

        // Tính độ lệch chuẩn
        double sumSquaredDiff = 0;
        for (int i = 0; i < recentPoints.size(); i++) {
            DataPoint point = recentPoints.get(i);
            double diff = point.getValue() - mean;
            sumSquaredDiff += diff * diff;
        }

        double stdDev = Math.sqrt(sumSquaredDiff / count);

        // Tránh chia cho 0
        if (stdDev < 0.0001) {
            stdDev = 0.0001;
        }

        // Tính Z-score và kiểm tra bất thường
        double zScore = Math.abs(newPoint.getValue() - mean) / stdDev;
        return zScore > zScoreThreshold;
    }
}