package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TransactionQueryHelper extends QueryHelper<Transaction, RequestTransactionDto> {

    public TransactionQueryHelper() {
        super(Transaction.class);
    }

    @Override
    public HashMap<String, String> mapOptionalParams(RequestTransactionDto req) {
        HashMap<String, String> params = new HashMap<>();

        if (req == null) {
            return params;
        }

        if (req.getWalletId() != null) {
            params.put("wallet.id", String.valueOf(req.getWalletId()));
        }
        if (req.getCategoryId() != null) {
            params.put("category.id", String.valueOf(req.getCategoryId()));
        }

        return params;
    }
}
