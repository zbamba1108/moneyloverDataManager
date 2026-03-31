package dev.boog.moneyloverdatamanager.utils;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException(Constants.Messages.UTILITY_CLASS);
    }

    public static class Headers {

        public static final String USER_ID = "X-User-ID";
        public static final String API_KEY = "X-API-Key";
    }

    public static class Transaction {

        public static class ColumnsName  {

            public static final String ID = "id";

            public static final String WALLET = "wallet_id";

            public static final String USER = "user_id";

            public static final String EVENT = "event_id";

            public static final String CATEGORY = "category_id";
        }
    }

    public static class EntityGraph {

        public static final String TRANSACTION_WALLET_CATEGORY = "transaction.wallet_category";
        public static final String TRANSACTION_CATEGORY = "transaction.category";
        public static final String WALLET_TRANSACTION = "wallet.transaction";
    }

    public static class Messages {
        public static final String UTILITY_CLASS = "Utility class!";
        public static final String RESOURCE_NOT_FOUND = "Resource not found!";
    }

    public static class Fields {
        public static final String ID = "id";
        public static final String USER_ID = "userId";
        public static final String NAME = "name";
        public static final String WALLET_ID = "wallet.id";
        public static final String CATEGORY_ID = "category.id";
        public static final String EVENT_ID = "event.id";
        public static final String CREATED_AT = "createdAt";
        public static final String TYPE = "type";
        public static final String PARENT_ID = "parent.id";
    }

    public static class Secrets {
        public static final String INTERNAL = "api-gateway_secret";
    }
}
