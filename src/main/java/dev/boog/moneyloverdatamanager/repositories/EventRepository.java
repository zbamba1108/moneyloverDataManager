package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventRepository extends BaseRepository<Event, Long>,
                                         CustomSearchQueryRepository<Event> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Event WHERE id=:id AND user_id=:userId", nativeQuery = true)
    void deleteByIdAndUserId(Long id, Long userId);
}
