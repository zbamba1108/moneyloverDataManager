package dev.boog.moneyloverdatamanager.repositories;


import dev.boog.moneyloverdatamanager.utils.*;
import java.util.*;

public interface CustomSearchQueryRepository<E> {

    List<E> searchByUserId(Class<E> clazz, String userId, ResultFilters resultFilters, boolean mapDetails);

    List<E> searchByUserIdAndIds(Class<E> clazz, String userId, List<String> ids, ResultFilters resultFilters, boolean mapDetails);

    List<E> searchByUserIdAndOptionalParams(Class<E> clazz, String userId, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

    List<E> searchByUserIdAndIdsAndOptionalParams(Class<E> clazz, String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

    List<E> search(Class<E> clazz , String userId, List<String> ids, HashMap<String, String> optionalParams, ResultFilters resultFilters, boolean mapDetails);

}
