package multithreading;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private  final int CARS_COUNT = 4;
    private Semaphore tunnelLimit = new Semaphore(CARS_COUNT / 2);
    private CyclicBarrier cyclicBarrier;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {

            try {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    tunnelLimit.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " + description);
                    tunnelLimit.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
