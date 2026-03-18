package dev.boog.moneyloverdatamanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends CrudRepository<E, ID> {

    <S extends E> S save(S entity);
}
