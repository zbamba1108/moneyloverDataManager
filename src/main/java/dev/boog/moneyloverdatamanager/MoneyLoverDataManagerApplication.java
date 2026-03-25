package dev.boog.moneyloverdatamanager;

import dev.boog.moneyloverdatamanager.configs.datasource.Monitor;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableScheduling
public class MoneyLoverDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLoverDataManagerApplication.class, args);
    }

    @Autowired
    private Monitor monitor;

    @Autowired
    private DBPopulator dbPopulator;

    @Autowired
    private ThreadPoolTaskExecutor dbTaskExecutor;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private final int schedulerFrequency = 10;

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            ThreadPoolExecutor dbExecutor = dbTaskExecutor.getThreadPoolExecutor();
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            scheduledExecutor.scheduleAtFixedRate(() -> {
                monitor.logHikariPoolStats(schedulerFrequency);
                monitor.logDbExecutorStats(stopWatch);
            }, 0, schedulerFrequency, TimeUnit.MILLISECONDS);

            // async
            try {
                dbExecutor.execute(() -> {
                    try {
                        dbPopulator.populateDB(dbExecutor, countDownLatch);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.await(1, TimeUnit.HOURS);
                dbExecutor.shutdown();
                if (dbExecutor.awaitTermination(1, TimeUnit.HOURS) && dbExecutor.isTerminated()) {
                    stopWatch.stop();
                    scheduledExecutor.shutdown();
                }
            }

            log(stopWatch);
        };
    }

    private void log(StopWatch stopWatch) {
        int timeSpentWithDbSaturated = monitor.getSaturatedDbCounter();
        double percentage = (double) timeSpentWithDbSaturated / stopWatch.getTime(TimeUnit.MILLISECONDS) * 100;
        System.out.println("stopWatchTime: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
        System.out.println("time spent with db connection saturated: " + timeSpentWithDbSaturated);
        System.out.println("time in % " + percentage);
        System.out.println("-------------------------------");
        System.out.println("WAITING THREAD MAP");
        System.out.println("Time spent with no threads waiting: " + monitor.getMaxThreadsWaiting().get(0) + ", percentage: " + ((double) monitor.getMaxThreadsWaiting().get(0) / stopWatch.getTime() * 100));

        int spentWithMoreThan5Threads = monitor.getMaxThreadsWaiting()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() > 5)
                .mapToInt(Map.Entry::getValue)
                .sum();
        System.out.println("Time spent with more than 5 threads waiting: " + spentWithMoreThan5Threads + ", percentage: " + ((double) spentWithMoreThan5Threads / stopWatch.getTime() * 100));
        System.out.println("Ready!!!");
    }


}

@Component
class DBPopulator {

    @Autowired
    private WalletPopulator walletPopulator;

    @Autowired
    private CategoryPopulator categoryPopulator;

    @Autowired
    private TransactionPopulator transactionPopulator;

    private final int userCounter = 10;

    public void populateDB(ExecutorService executorService, CountDownLatch countDownLatch) throws InterruptedException {

        List<Long> userIds = new ArrayList<>();

        for (int i = 0; i < userCounter; i++) {
            userIds.add(Long.valueOf(i));
        }
        CountDownLatch userCountdown = new CountDownLatch(userCounter);

        for (Long id : userIds) {
            executorService.execute(() -> {
                try {
                    Future<List<Long>> futureWalletsId = executorService.submit(() -> walletPopulator.process(id));

                    Future<List<Long>> futureCategoriesId = executorService.submit(() -> categoryPopulator.process(id));

                    for (int i = 0; i < 10; i++) {
                        executorService.execute(() -> {
                            try {
                                transactionPopulator.process(id, futureWalletsId.get(), futureCategoriesId.get());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } finally {
                    userCountdown.countDown();
                }
            });
        }
        userCountdown.await();
        countDownLatch.countDown();
    }

}

@Component
class CategoryPopulator {

    @Autowired
    private CategoryRepository categoryRepository;

    int categoryNumber = 100;

    AtomicInteger count = new AtomicInteger(1);

    @Transactional
    public List<Long> process(Long userId) {
        List<Category> categories = new ArrayList<>();
        for (int c = 0; c < categoryNumber; c++) {
            Category category = Category.builder()
                    .name("Category #" + (count.getAndIncrement()))
                    .type(ThreadLocalRandom.current().nextInt(2))
                    .userId(userId)
                    .build();
            categories.add(category);
        }
        final Iterable<Category> categoryIterable = categoryRepository.saveAll(categories);

        List<Long> categoryIds = new ArrayList<>();
        for (Category category : categoryIterable) {
            categoryIds.add(category.getId());
        }

        return categoryIds;
    }
}

@Component
class WalletPopulator {

    @Autowired
    private WalletRepository walletRepository;

    int walletNumber = 50;

    AtomicInteger count = new AtomicInteger(1);

    @Transactional
    public List<Long> process(Long userId) {
        List<Wallet> wallets = new ArrayList<>();

        for (int i = 0; i < walletNumber; i++) {
            Wallet wallet = Wallet.builder()
                    .name("Wallet #" + (count.getAndIncrement()))
                    .userId(userId)
                    .build();
            wallets.add(wallet);
        }

        final Iterable<Wallet> walletIterable = walletRepository.saveAll(wallets);

        List<Long> walletIds = new ArrayList<>();

        for (Wallet wallet : walletIterable) {
            walletIds.add(wallet.getId());
        }

        return walletIds;
    }
}

@Component
class TransactionPopulator {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    int transactionNumber = 100;

    @Transactional
    public void process(Long userId, List<Long> walletIds, List<Long> categoryids) {
        List<Transaction> transactions = new ArrayList<>();

        for (int j = 0; j < transactionNumber; j++) {
            int randomWalletId = ThreadLocalRandom.current().nextInt(0, walletIds.size());
            int randomCategoryId = ThreadLocalRandom.current().nextInt(0, categoryids.size());
            int randomAmount = ThreadLocalRandom.current().nextInt(1, 5000);

            Wallet wallet = entityManager.getReference(Wallet.class, walletIds.get(randomWalletId));

            Category category = entityManager.getReference(Category.class, categoryids.get(randomCategoryId));

            Transaction transaction = Transaction.builder()
                    .amount(new BigDecimal(randomAmount))
                    .category(category)
                    .wallet(wallet)
                    .userId(userId)
                    .build();

            transactions.add(transaction);
        }

        transactionRepository.saveAll(transactions);
    }


}

