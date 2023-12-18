package client.particles;

public class MathUtils {
    public static float calculateDistance(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float)Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    public static float remapTo10(float value, float min, float max) {
        return 1 - (value - min) / (max - min);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
    public static float lerp(float start, float end, float t) {
        return start + t * (end - start);
    }
}
