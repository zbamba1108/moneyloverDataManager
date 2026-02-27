package dev.boog.moneyloverdatamanager.repositories;


import java.util.HashMap;
import java.util.List;

public interface CustomSearchQueryRepository<E> {


    List<E> searchWithMultipleOptionalParams(HashMap<String, String> params, Class<E> clazz);
}
