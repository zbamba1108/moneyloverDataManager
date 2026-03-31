package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper extends BaseEntityMapper<Wallet, RequestWalletDto, ResponseWalletDto> {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    Wallet updateEntity(RequestWalletDto dto, @MappingTarget Wallet entity);

    @Override
    @Mappings({
            @Mapping(target = "transactions", ignore = true),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseWalletDto toResponseDto(Wallet wallet);

    @Override
    @Mappings({
            @Mapping(target = "transactions", source = "transactionList", qualifiedByName = "transactionToDto"),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseWalletDto toResponseDtoDetails(Wallet wallet);

    @Named("transactionToDto")
    default ResponseTransactionDto transactionToDto(Transaction transaction) {
        return TransactionMapper.INSTANCE.toResponseDtoDetails(transaction);
    }
}
