package dev.boog.moneyloverdatamanager.utils;

public class Constants {

    public static class Headers {

        public static final String USER_ID = "User-ID";
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
    }
}
