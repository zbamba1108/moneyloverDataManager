package dev.boog.moneyloverdatamanager.repositories;


import java.util.HashMap;
import java.util.List;

public interface CustomSearchQueryRepository<E, ID> {


    List<E> searchWithMultipleOptionalParams(HashMap<String, ID> params, Class<E> clazz);
}
