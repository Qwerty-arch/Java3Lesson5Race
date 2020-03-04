package multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static int CARS_COUNT;
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;

    static {
        CARS_COUNT = 0;
    }
    private static volatile boolean whoIsWinner;

    private Race race;
    private int speed;
    private String name;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier,CountDownLatch countDownLatch) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        whoIsWinner = true;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
         for (int i = 0; i < race.getStages().size(); i++) {
             race.getStages().get(i).go(this);
         }
        if (whoIsWinner) {
            whoIsWinner = false;
            System.out.println(this.name + " WINNER!!!");
        }
         countDownLatch.countDown();
    }
}