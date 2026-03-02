package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long>, CustomDeleteQueryRepository<User, Long> {

    @Query(value = "from User u where u.id in :ids")
    List<User> getUserById(List<Long> ids);

    @Query(value = "from User")
    List<User> getUsers();
}
