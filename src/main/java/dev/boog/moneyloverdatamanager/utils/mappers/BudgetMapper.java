package dev.boog.moneyloverdatamanager.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BudgetMapper /*extends BaseMapper<Budget, RequestBudgetDto, ResponseBudgetDto>*/ {

    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

}
