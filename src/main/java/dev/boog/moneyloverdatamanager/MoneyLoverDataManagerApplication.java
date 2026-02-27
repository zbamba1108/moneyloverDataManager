package dev.boog.moneyloverdatamanager;

import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.repositories.CategoryRepository;
import dev.boog.moneyloverdatamanager.repositories.TransactionRepository;
import dev.boog.moneyloverdatamanager.repositories.UserRepository;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class MoneyLoverDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLoverDataManagerApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    public  CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            int userNumber = 10;

            int walletNumber = 500;

            int transactionNumber = 10000;

            int categoryNumber = 1000;

            for (int i = 0; i < userNumber; i++) {
                userRepository.save(new User(null,"User#" + (i + 1), "Doe"));
            }

            for (int i = 0; i < walletNumber; i++) {
                int randomUserId = ThreadLocalRandom.current().nextInt(1, userNumber);
                walletRepository.save(new Wallet(null, "Wallet #" + (i + 1), new User((long) randomUserId, null, null)));
            }

            for (int i = 0; i < categoryNumber; i++) {
                int randomUserId = ThreadLocalRandom.current().nextInt(1, userNumber);
                categoryRepository.save(new Category(null, "Category #" + (i + 1), new User((long) randomUserId, null, null), 1, null));
            }

            for (int i = 0; i < transactionNumber; i++) {
                int randomUserId = ThreadLocalRandom.current().nextInt(1, userNumber);
                int randomWalletId = ThreadLocalRandom.current().nextInt(1, walletNumber);
                int randomCategoryId = ThreadLocalRandom.current().nextInt(1, categoryNumber);
                int randomAmount = ThreadLocalRandom.current().nextInt(1, 5000);
                transactionRepository.save(new Transaction(
                        null,
                        new Wallet((long) randomWalletId, null, null),
                        new User((long) randomUserId, null, null),
                        null,
                        new Category((long) randomCategoryId, null, null, null, null),
                        new BigDecimal(randomAmount),
                        null));
            }

            System.out.println("Ready!!!");
        };
    }
}
