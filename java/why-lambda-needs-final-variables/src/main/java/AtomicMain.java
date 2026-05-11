import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicMain {
    public static void main(String[] args) {
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            items.add(i);
        }

        AtomicInteger result = new AtomicInteger(0);
        items.forEach(i -> {
            result.incrementAndGet();
        });
        System.out.println(result);
    }
}
