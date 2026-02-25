package dev.boog.moneyloverdatamanager.repositories;


import java.util.HashMap;
import java.util.List;

public interface CustomSearchQueryRepository<E, ID> {


    List<E> searchWithMultipleOPtionalParams(ID userId, HashMap<String, ID> params);
}
