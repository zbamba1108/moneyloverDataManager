package dev.boog.moneyloverdatamanager.services.utils;

import dev.boog.moneyloverdatamanager.dtos.request.RequestUserDto;
import dev.boog.moneyloverdatamanager.entities.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserQueryHelper extends QueryHelper<User, RequestUserDto> {

    public UserQueryHelper() {
        super(User.class);
    }

    @Override
    public Map<String, Object> mapOptionalParams(RequestUserDto req) {
        return Map.of();
    }
}
