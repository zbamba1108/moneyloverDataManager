package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends BaseRepository<Event, Long>,
                                         CustomSearchQueryRepository<Event>,
                                         CustomDeleteQueryRepository<Event, Long> {
}
