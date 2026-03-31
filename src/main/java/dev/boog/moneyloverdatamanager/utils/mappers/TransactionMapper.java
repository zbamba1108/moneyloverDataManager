package dev.boog.moneyloverdatamanager.utils.mappers;

import dev.boog.moneyloverdatamanager.dtos.request.RequestTransactionDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseTransactionDto;
import dev.boog.moneyloverdatamanager.entities.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper extends BaseEntityMapper<Transaction, RequestTransactionDto, ResponseTransactionDto> {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Override
    @Mappings({
            @Mapping(target="category.id", source = "dto.categoryId"),
            @Mapping(target="wallet.id", source = "dto.walletId"),
            @Mapping(target="event.id", source = "dto.eventId")
    })
    Transaction toEntity(RequestTransactionDto dto, Long userId);

    @Mappings({
            @Mapping(target="category.id", source = "dto.categoryId"),
            @Mapping(target="wallet.id", source = "dto.walletId"),
            @Mapping(target="event.id", source = "dto.eventId")
    })
    Transaction updateEntity(RequestTransactionDto dto, @MappingTarget Transaction transaction);

    @Override
    @Mappings({
            @Mapping(target="category", ignore = true),
            @Mapping(target="wallet", ignore = true),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseTransactionDto toResponseDto(Transaction transaction);

    @Override
    @Mappings({
            @Mapping(target="category", source = "category"),
            @Mapping(target="category.createdAt", ignore = true),
            @Mapping(target="wallet", source = "wallet"),
            @Mapping(target="wallet.createdAt", ignore = true),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "timestampToLong")
    })
    ResponseTransactionDto toResponseDtoDetails(Transaction transaction);

    @AfterMapping
    default Transaction postProcessing(@MappingTarget Transaction transaction) {
        if (transaction.getEvent() != null && transaction.getEvent().getId() == null) {
            transaction.setEvent(null);
        }

        return transaction;
    }

}
