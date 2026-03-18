package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.BaseRequestDto;
import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class WalletQueryHelper extends QueryHelper<Wallet, RequestWalletDto> {

    public WalletQueryHelper() {
        super(Wallet.class);
    }

    @Override
    public HashMap<String, String> mapOptionalParams(RequestWalletDto req) {
        HashMap<String, String> params = new HashMap<>();

        if (req == null) {
            return params;
        }
        
        if (req.getName() != null) {
            params.put("name", req.getName());
        }

        return params;
    }
}
