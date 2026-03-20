package dev.boog.moneyloverdatamanager;

import dev.boog.moneyloverdatamanager.dtos.request.RequestCategoryDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.repositories.utils.models.QueryRequest;
import dev.boog.moneyloverdatamanager.services.utils.CategoryQueryHelper;
import dev.boog.moneyloverdatamanager.services.utils.QueryRequestBuilder;
import dev.boog.moneyloverdatamanager.services.utils.WalletQueryHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class MoneyLoverDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLoverDataManagerApplication.class, args);
    }

    @Autowired
    private DBPopulator dbPopulator;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ThreadPoolExecutor  executorService = new ThreadPoolExecutor(
                    10,
                    10,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(200),
                    new ThreadPoolExecutor.CallerRunsPolicy());

            ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor();

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            monitor.scheduleAtFixedRate(() -> System.out.println(
                            "Active: " + executorService.getActiveCount() +
                            " | Pool: " + executorService.getPoolSize() +
                            " | Queue: " + executorService.getQueue().size() +
                            " | Completed: " + executorService.getCompletedTaskCount() +
                            " | time: " + stopWatch.getTime(TimeUnit.MILLISECONDS)
            ), 0, 10, TimeUnit.MILLISECONDS);

            // async
            try {
                executorService.execute(() -> {
                    try {
                        dbPopulator.populateDB(executorService, countDownLatch);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.await(1, TimeUnit.HOURS);
                executorService.shutdown();
                if (executorService.awaitTermination(1, TimeUnit.MINUTES) && executorService.isTerminated()) {
                    stopWatch.stop();
                    monitor.shutdown();
                }
            }
            System.out.println("stopWatchTime: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
            System.out.println("Ready!!!");
        };
    }


}

@Component
class DBPopulator {

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private WalletPopulator walletPopulator;

    @Autowired
    private CategoryPopulator categoryPopulator;

    @Autowired
    private TransactionPopulator transactionPopulator;

    public void populateDB(ExecutorService executorService, CountDownLatch countDownLatch) throws InterruptedException {

        Iterable<User> users = userPopulator.process();
        CountDownLatch userCountdown = new CountDownLatch(10);

        for (User user : users) {
            executorService.execute(() -> {
                try {
                    Future<List<Long>> futureWalletsId = executorService.submit(() -> walletPopulator.process(user.getId()));

                    Future<List<Long>> futureCategoriesId = executorService.submit(() -> categoryPopulator.process(user.getId()));

                    for (int i = 0; i < 10; i++) {
                        executorService.execute(() -> {
                            try {
                                transactionPopulator.process(user.getId(), futureWalletsId.get(), futureCategoriesId.get());
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
class UserPopulator {

    @Autowired
    private UserRepository userRepository;

    int userNumber = 10;

    AtomicInteger count = new AtomicInteger(1);

    @Transactional
    public Iterable<User> process() {
        List<User> users = new ArrayList<>();

        for(int i = 0; i < userNumber; i++) {
            users.add(new User("User" + count.getAndIncrement() + "@mail.com", "password"));
        }

        return userRepository.saveAll(users);
    }
}

@Component
class CategoryPopulator {

    @PersistenceContext
    private EntityManager entityManager;

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
                    .user(entityManager.getReference(User.class, userId))
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

    @PersistenceContext
    private EntityManager entityManager;

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
                    .user(entityManager.getReference(User.class, userId))
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
                    .user(entityManager.getReference(User.class, userId))
                    .build();

            transactions.add(transaction);
        }

        transactionRepository.saveAll(transactions);
    }


}

