package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Category;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class CustomDeleteQueryRepositoryImpl<E, ID extends Number> implements CustomDeleteQueryRepository<E, ID> {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void deleteByIds(Class<E> entityClass, ID userId, List<ID> ids) {
        String className = entityClass.getSimpleName();

        if (className.equals(Wallet.class.getSimpleName())) {
            deleteWallet(userId, ids);
        } else if (className.equals(User.class.getSimpleName())) {
            deleteUser(ids);
        } else if (className.equals(Category.class.getSimpleName())) {
            deleteCategory(userId, ids);
        }
    }

    private void deleteUser(List<ID> ids) {
        String transactionSql = "DELETE FROM Transaction t WHERE t.user.id IN :ids";
        String categorySql = "DELETE FROM Category c WHERE c.user.id IN :ids";
        String walletSql = "DELETE FROM Wallet w WHERE w.user.id IN :ids";
        // TODO implement other entities

        String userSql = "DELETE FROM User u WHERE u.id IN :ids";

        executeSqlWithIds(transactionSql, ids);
        executeSqlWithIds(categorySql, ids);
        executeSqlWithIds(walletSql, ids);
        executeSqlWithIds(userSql, ids);
    }

    private void deleteWallet(ID userID, List<ID> ids) {
        String transactionSql = "DELETE FROM Transaction t WHERE t.wallet.id IN :ids AND t.user.id = :userID";
        String walletSql = "DELETE FROM Wallet w WHERE w.id IN :ids AND w.user.id = :userID";

        executeSqlWithIdsAndUserId(transactionSql, userID, ids);
        executeSqlWithIdsAndUserId(walletSql, userID, ids);
    }

    private void deleteCategory(ID userID, List<ID> ids) {
        String transactionSql = "DELETE FROM Transaction t WHERE t.category.id IN :ids AND t.user.id = :userID";
        String categorySql = "DELETE FROM Category c WHERE c.id IN :ids AND c.user.id = :userID";

        executeSqlWithIdsAndUserId(transactionSql, userID, ids);
        executeSqlWithIdsAndUserId(categorySql, userID, ids);
    }

    private void executeSqlWithIds(String sql, List<ID> ids) {
        int deleted = em.createQuery(sql)
                .setParameter("ids", ids)
                .executeUpdate();

        System.out.println(sql + "\n" + "deleted: " + deleted);
    }

    private void executeSqlWithIdsAndUserId(String sql, ID userId, List<ID> ids) {
        em.createQuery(sql)
                .setParameter("ids", ids)
                .setParameter("userID", userId)
                .executeUpdate();
    }

}
