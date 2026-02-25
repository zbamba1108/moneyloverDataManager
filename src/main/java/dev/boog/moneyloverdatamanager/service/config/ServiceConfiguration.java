package dev.boog.moneyloverdatamanager.service.config;

import dev.boog.moneyloverdatamanager.repositories.*;
import dev.boog.moneyloverdatamanager.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean("transactionService")
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        TransactionService transactionService = new TransactionService();
        transactionService.setRepository(transactionRepository);
        return transactionService;
    }

    @Bean("userService")
    public UserService userService(UserRepository userRepository) {
        UserService userService = new UserService();
        userService.setRepository(userRepository);
        return userService;
    }

    @Bean("budgetService")
    public BudgetService budgetService(BudgetRepository budgetRepository) {
        BudgetService budgetService = new BudgetService();
        budgetService.setRepository(budgetRepository);
        return budgetService;
    }

    @Bean("categoryService")
    public CategoryService categoryService(CategoryRepository categoryRepository) {
        CategoryService categoryService = new CategoryService();
        categoryService.setRepository(categoryRepository);
        return categoryService;
    }

    @Bean("eventService")
    public EventService eventService(EventRepository eventRepository) {
        EventService eventService = new EventService();
        eventService.setRepository(eventRepository);
        return eventService;
    }

    @Bean("walletService")
    public WalletService walletService(WalletRepository walletRepository) {
        WalletService walletService = new WalletService();
        walletService.setRepository(walletRepository);
        return walletService;
    }

}
