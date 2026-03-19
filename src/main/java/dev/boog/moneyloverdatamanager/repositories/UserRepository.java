package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long>,
                                        CustomSearchQueryRepository<User>,
                                        CustomDeleteQueryRepository<User, Long> {
}
