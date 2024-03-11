package com.free.swd_392.mapper.app;

import com.free.swd_392.core.mapper.DtoMapper;
import com.free.swd_392.dto.order.OrderDetails;
import com.free.swd_392.dto.order.OrderInfo;
import com.free.swd_392.entity.order.OrderEntity;
import com.free.swd_392.shared.projection.OrderInfoProjection;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class AppOrderMapper implements
        DtoMapper<OrderInfo, OrderDetails, OrderEntity> {

    @Named("convertToInfoProjectionMapper")
    @Mapping(target = "firstItem", source = "firstItem")
    public abstract OrderInfo convertToInfo(OrderInfoProjection projection);

    @IterableMapping(elementTargetType = OrderInfo.class, qualifiedByName = "convertToInfoProjectionMapper")
    public abstract List<OrderInfo> convertToInfoProjectionList(List<OrderInfoProjection> orders);
}
