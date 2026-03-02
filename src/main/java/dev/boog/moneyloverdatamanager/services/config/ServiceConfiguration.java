package dev.boog.moneyloverdatamanager.services.config;

import dev.boog.moneyloverdatamanager.repositories.*;
import dev.boog.moneyloverdatamanager.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean("transactionService")
    public TransactionServiceImpl transactionService(TransactionRepository transactionRepository) {
        return new TransactionServiceImpl(transactionRepository);
    }

    @Bean("userService")
    public UserServiceImpl userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean("budgetService")
    public BudgetServiceImpl budgetService(BudgetRepository budgetRepository) {
        return new BudgetServiceImpl(budgetRepository);
    }

    @Bean("categoryService")
    public CategoryServiceImpl categoryService(CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean("eventService")
    public EventServiceImpl eventService(EventRepository eventRepository) {
        return new EventServiceImpl(eventRepository);
    }

    @Bean("walletService")
    public WalletServiceImpl walletService(WalletRepository walletRepository) {
        return new WalletServiceImpl(walletRepository);
    }

}
