package com.alibaba.edas.carshop.itemcenter;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public interface ItemService {

    Item getItemById(long id);

    Item getItemByName(String name);

}
