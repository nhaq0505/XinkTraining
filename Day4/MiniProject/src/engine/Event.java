package engine;

import java.util.Map;

public class Event {
    private final String type;
    private final long timestamp;
    private final Map<String,Object> payload;

    public Event(String type, long timestamp, Map<String,Object> payload) {
        this.type = type;
        this.timestamp = timestamp;
        this.payload = payload;
    }
    public String getType() {
        return type;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public Map<String, Object> getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Event{" + "type=" + type + ", timestamp=" + timestamp + ", payload=" + payload + '}';
    }
}
