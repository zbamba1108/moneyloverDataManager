package dev.boog.moneyloverdatamanager.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper extends BaseMapper<Transaction, RequestTransactionDto, ResponseTransactionDto> {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mappings({
            @Mapping(target="user.id", source = "userId"),
            @Mapping(target="category.id", source = "categoryId"),
            @Mapping(target="wallet.id", source = "walletId"),
            @Mapping(target="event.id", source = "eventId")
    })
    Transaction toEntity(RequestTransactionDto dto);

    @Mapping(target="userId", source = "user.id")
    ResponseTransactionDto toResponseDto(Transaction transaction);

    @AfterMapping
    default Transaction postProcessing(@MappingTarget Transaction transaction) {
        if (transaction.getEvent() != null && transaction.getEvent().getId() == null) {
            transaction.setEvent(null);
        }

        return transaction;
    }

}
