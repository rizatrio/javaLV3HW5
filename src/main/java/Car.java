import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    private static boolean raceWinner = false;
    private static Lock lock = new ReentrantLock();

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cyclicBarrier;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            cyclicBarrier.await();
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            lock.lock();

            if (!raceWinner) {
                raceWinner = true;
                System.out.println(this.name + " Победитель гонки!!!!!!!!!!!!!!!");
            }

            lock.unlock();
            Api.countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
