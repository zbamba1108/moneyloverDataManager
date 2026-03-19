package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import dev.boog.moneyloverdatamanager.utils.Constants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserQueryHelper extends QueryHelper<User, RequestUserDto> {

    public UserQueryHelper() {
        super(User.class);
    }

    @Override
    public Map<String, Object> mapOptionalParams(RequestUserDto req) {
        Map<String, Object> params = new HashMap<>();

        if (req == null) {
            return params;
        }

        if (StringUtils.hasText(req.getEmail())) {
            params.put(Constants.Fields.EMAIL, req.getEmail());
        }

        return params;
    }
}
