package dev.boog.moneyloverdatamanager.repositories;

import dev.boog.moneyloverdatamanager.entities.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Number> extends CrudRepository<E, ID> {

    <S extends E> S save(S entity);

    Optional<E> findByIdAndUserId(Long id, Long userId);
}
