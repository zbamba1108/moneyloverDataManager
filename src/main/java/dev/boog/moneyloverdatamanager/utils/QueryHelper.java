package dev.boog.moneyloverdatamanager.utils;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.Iterator;

public class QueryHelper {

    public static String capitalizeProperty(String property) {
        while (property.contains(".")){
            int letterToCapitalizeIndex = property.indexOf(".") + 1;
            String capitalizedLetter = String.valueOf(property.charAt(letterToCapitalizeIndex)).toUpperCase();
            property = property.substring(0, letterToCapitalizeIndex-1)
                    + capitalizedLetter
                    + property.substring(letterToCapitalizeIndex+1);
        }

        return property;
    }

    public static EntityGraph<?> getEntityGraph(EntityManager em, String classSimpleName) {
        if (classSimpleName.equals(Transaction.class.getSimpleName())) {
            return em.getEntityGraph(Constants.EntityGraph.TRANSACTION_WALLET_CATEGORY);
        }

        return null;
    }

    public static String buildQueryAndCreateQueryParam(StringBuilder sb, HashMap<String, String> inputMap, HashMap<String, String> outputMap) {

        Iterator<String> iterator = inputMap.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String capitalizedProperty = QueryHelper.capitalizeProperty(key);
            outputMap.put(capitalizedProperty, inputMap.get(key));
            sb.append(" e.").append(key).append(" = :").append(capitalizedProperty);

            if (iterator.hasNext()) {
                sb.append(" AND");
            }
        }

        return sb.toString();
    }
}
