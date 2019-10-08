package com.alibaba.edas.carshop.itemcenter;

import java.util.List;

public interface OrderService {

    List<Item> findItemListByOrderId(int orderId);

}
