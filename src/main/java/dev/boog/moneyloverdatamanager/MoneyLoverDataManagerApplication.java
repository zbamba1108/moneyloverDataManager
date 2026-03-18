package dev.boog.moneyloverdatamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyLoverDataManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyLoverDataManagerApplication.class, args);
    }

    /*@Autowired
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

            int walletNumber = 50;

            int transactionNumber = 1000;

            int categoryNumber = 100;

            for (int i = 0; i < userNumber; i++) {
                User user = userRepository.save(User.builder()
                        .email("User#" + (i + 1))
                        .password("password")
                        .build());

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
                            .type(1)
                            .user(user)
                            .build();

                    categoryRepository.save(category);
                }
                List<Long> walletIds = walletRepository
                        .getAllByUserId(user.getId())
                        .stream()
                        .map(Wallet::getId)
                        .toList();

                List<Long> categoryids = categoryRepository
                        .getAllByUserId(user.getId())
                        .stream()
                        .map(Category::getId)
                        .toList();

                for (int t = 0; t < transactionNumber; t++) {
                    int randomWalletIndex = ThreadLocalRandom.current().nextInt(0, walletIds.size()-1);
                    int randomCategoryId = ThreadLocalRandom.current().nextInt(0, categoryids.size()-1);
                    int randomAmount = ThreadLocalRandom.current().nextInt(1, 5000);

                    Wallet wallet = Wallet.builder()
                            .id(walletIds.get(randomWalletIndex))
                            .build();

                    Category category = Category.builder()
                            .id(categoryids.get(randomCategoryId))
                            .build();

                    Transaction transaction = Transaction.builder()
                            .amount(new BigDecimal(randomAmount))
                            .category(category)
                            .wallet(wallet)
                            .user(user)
                            .build();

                    transactionRepository.save(transaction);
                }
            }

            System.out.println("Ready!!!");
        };
    }*/
}
