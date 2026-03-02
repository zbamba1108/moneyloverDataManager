package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper extends BaseMapper<Wallet, RequestWalletDto, ResponseWalletDto> {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "transactionList", ignore = true),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseWalletDto toResponseDto(Wallet wallet);
}
