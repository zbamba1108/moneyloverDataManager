package dev.boog.moneyloverdatamanager.services.config;

import dev.boog.moneyloverdatamanager.repositories.*;
import dev.boog.moneyloverdatamanager.services.*;
import dev.boog.moneyloverdatamanager.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public BudgetService budgetService(BudgetRepository budgetRepository) {
        return new BudgetServiceImpl(budgetRepository);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public EventService eventService(EventRepository eventRepository) {
        return new EventServiceImpl(eventRepository);
    }

    @Bean("walletService")
    public WalletServiceImpl walletService(WalletRepository walletRepository) {
        return new WalletServiceImpl(walletRepository);
    }

}
