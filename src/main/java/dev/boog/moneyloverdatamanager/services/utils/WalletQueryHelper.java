package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WalletQueryHelper extends QueryHelper<Wallet, RequestWalletDto> {

    public WalletQueryHelper() {
        super(Wallet.class);
    }

    @Override
    public Map<String, Object> mapOptionalParams(RequestWalletDto req) {
        Map<String, Object> params = new HashMap<>();

        if (req == null) {
            return params;
        }

        if (req.getName() != null) {
            params.put(Constants.Fields.NAME, req.getName());
        }

        return params;
    }
}
