package dev.boog.moneyloverdatamanager.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserRelatedEntitiesRepository<E, ID> extends BaseRepository<E, ID>{

    E getByUserIdAndId(final ID userId, final ID id);

    List<E> getAllByUserId(final ID userId);
}
