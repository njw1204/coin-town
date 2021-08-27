package kr.njw.springstudy2.order.service;

import kr.njw.springstudy2.order.model.Order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
