package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>,
                                        CustomSearchQueryRepository<User> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteById(Long id);

}
