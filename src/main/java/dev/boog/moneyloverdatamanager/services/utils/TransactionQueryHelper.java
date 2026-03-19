package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionQueryHelper extends QueryHelper<Transaction, RequestTransactionDto> {

    public TransactionQueryHelper() {
        super(Transaction.class);
    }

    @Override
    public Map<String, Object> mapOptionalParams(RequestTransactionDto req) {
        Map<String, Object> params = new HashMap<>();

        if (req != null) {
            if (req.getWalletId() != null) {
                params.put(Constants.Fields.WALLET_ID, req.getWalletId());
            }
            if (req.getCategoryId() != null) {
                params.put(Constants.Fields.CATEGORY_ID, req.getCategoryId());
            }
        }

        return params;
    }
}
