package dev.boog.moneyloverdatamanager.utils;

import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.enums.StringBuilderType;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.Iterator;

public final class QueryHelper {

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
        } else if (classSimpleName.equals(Wallet.class.getSimpleName())) {
            return em.getEntityGraph(Constants.EntityGraph.WALLET_TRANSACTION);
        }

        return null;
    }

    public static void buildQueryAndCreateQueryParam(StringBuilder sb, HashMap<String, String> inputMap, HashMap<String, String> outputMap) {

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
    }

    public static java.lang.StringBuilder getStringBuilder(StringBuilderType type, String classSimpleName) {

        if (type.equals(StringBuilderType.COUNT)) {
            return new StringBuilder()
                    .append("SELECT COUNT(e) FROM ")
                    .append(classSimpleName)
                    .append(" e WHERE e.user.id = :userId");
        } else if (type.equals(StringBuilderType.RETRIEVE_IDS)) {
            return new StringBuilder()
                    .append("SELECT e.id FROM ")
                    .append(classSimpleName)
                    .append(" e WHERE e.user.id = :userId");
        } else if (type.equals(StringBuilderType.RETRIEVE_ENTITY_LIST)) {
            return new StringBuilder()
                    .append("SELECT e FROM ")
                    .append(classSimpleName)
                    .append(" e WHERE e.user.id = :userId");
        }

        return null;
    }
}
