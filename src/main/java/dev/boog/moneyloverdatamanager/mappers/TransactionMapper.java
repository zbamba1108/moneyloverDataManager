package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper extends BaseMapper<Transaction, RequestTransactionDto, ResponseTransactionDto> {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Override
    @Mappings({
            @Mapping(target="category.id", source = "categoryId"),
            @Mapping(target="wallet.id", source = "walletId"),
            @Mapping(target="event.id", source = "eventId")
    })
    Transaction toEntity(RequestTransactionDto dto);

    @Override
    @Mappings({
            @Mapping(target="category", ignore = true),
            @Mapping(target="wallet", ignore = true),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseTransactionDto toResponseDto(Transaction transaction);

    @AfterMapping
    default Transaction postProcessing(@MappingTarget Transaction transaction) {
        if (transaction.getEvent() != null && transaction.getEvent().getId() == null) {
            transaction.setEvent(null);
        }

        return transaction;
    }

}
