package com.testingthings.orderservice.service;

import com.testingthings.orderservice.dto.OrderLineItemsDto;
import com.testingthings.orderservice.dto.OrderRequest;
import com.testingthings.orderservice.model.Order;
import com.testingthings.orderservice.model.OrderLineItems;
import com.testingthings.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;

    public  void placeOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
      List<OrderLineItems> orderLineItems=
              orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::matToDto)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);


        orderRepository.save(order);
    }

    private OrderLineItems matToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }
}
