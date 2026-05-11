import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            items.add(i);
        }

        measure(() -> {
            int result = 0;
            for (int i = 0; i < items.size(); i++) {
                result++;
            }
            System.out.println(result);
        });

        measure(() -> {
            AtomicInteger result = new AtomicInteger(0);
            items.forEach(i -> {
                result.incrementAndGet();
            });
            System.out.println(result);
        });
    }


    public static void measure(Runnable method) {
        long start = System.nanoTime();
        method.run();
        long elapsed = System.nanoTime() - start;
        System.out.println(String.format("%.3fs", nanosToSeconds(elapsed)));
    }

    public static double nanosToSeconds(long nanos) {
        return nanos / 1_000_000_000.0;
    }
}