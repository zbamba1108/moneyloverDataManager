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
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MoneyLoverDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLoverDataManagerApplication.class, args);
    }

    @Autowired
    private DBPopulator dbPopulator;

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            dbPopulator.populateDBAsync();

            System.out.println("stopWatchTime: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
            System.out.println("Ready!!!");
        };
    }


}

@Component
class DBPopulator {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WalletRepository walletRepository;

    int userNumber = 10;

    int walletNumber = 50;

    int transactionNumber = 1000;

    int categoryNumber = 100;

    AtomicInteger count = new AtomicInteger(0);

    @Transactional
    public void populateDBAsync() throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(
                cores,
                cores,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(20),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            for (int i = 0; i < userNumber; i++) {
                executorService.execute(this::process);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            int counter = 0;
            while(!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                counter++;
                System.out.println("working.. " + counter + " S");
            }
        }
    }

    private void process() {
        User user = userRepository.save(new User("User" + (count.getAndIncrement() + 1) + "@mail.com","password"));

        for (int w = 0; w < walletNumber; w++) {
            Wallet wallet = Wallet.builder()
                    .name("Wallet #" + (w + 1))
                    .user(user)
                    .build();
            walletRepository.save(wallet);
        }

        for (int c = 0; c < categoryNumber; c++) {
            Category category = Category.builder()
                    .name("Category #" + (c + 1))
                    .type(ThreadLocalRandom.current().nextInt(2))
                    .user(user)
                    .build();

            categoryRepository.save(category);
        }
        QueryRequest<Wallet> walletQueryRequest = QueryRequestBuilder.build(new WalletQueryHelper(), RequestWalletDto.builder().build(), user.getId());
        List<Long> walletIds = walletRepository
                .findAll(walletQueryRequest)
                .results()
                .stream()
                .map(Wallet::getId)
                .toList();

        QueryRequest<Category> categoryQueryRequest = QueryRequestBuilder.build(new CategoryQueryHelper(), RequestCategoryDto.builder().build(), user.getId());
        List<Long> categoryids = categoryRepository
                .findAll(categoryQueryRequest)
                .results()
                .stream()
                .map(Category::getId)
                .toList();

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
                    .user(user)
                    .build();

            transactionRepository.save(transaction);
        }

    }

}

