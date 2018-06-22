package chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author WallfacerRZD
 * @date 2018/6/22 17:26
 */
public class Snippet {
    private static void listing7_2() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        java.util.concurrent.Future<?> future = executor.schedule(() -> System.out.println("60 seconds later"),
                60, TimeUnit.SECONDS);
        // ...
        executor.shutdown();
    }

    private static void listing7_3() {
/*        Channel ch = ...;
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                () -> System.out.println("60 seconds later"), 60, TimeUnit.SECONDS
        );*/
    }

    private static void listing7_4() {
/*        Channel ch = ...;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                ()->System.out.println("60 seconds later"), 60, 60, TimeUnit.SECONDS
        );*/
    }

    private static void listing7_5() {
/*        Channel ch = ...;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                () -> System.out.println("60 seconds later"), 60, 60, TimeUnit.SECONDS
        );
        boolean mayInterruptIfRunning = false;
        future.cancel(mayInterruptIfRunning);*/

    }
}
