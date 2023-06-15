package com.mindlease.fa.mapstruct.mapper;

import org.mapstruct.Mapper;

import com.mindlease.fa.dto.OrderDetailsDto;
import com.mindlease.fa.model.OrderDetails;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {
	OrderDetailsDto convertOrderDetailsToOrderDetailsDto(OrderDetails orderDetails);
}
