package com.alibabacloud.hipstershop.cartserviceprovider.repository;

import com.alibabacloud.hipstershop.cartserviceapi.domain.CartItem;
import com.alibabacloud.hipstershop.cartserviceprovider.utils.Common;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
@Service
public class RedisRepository {

    @Resource
    RedisTemplate<String, CartItem> redisTemplate;

    public boolean save(CartItem cartItem, String userId) {
        String key = Common.getRedisKey(userId).getKey();
        if (Objects.requireNonNull(redisTemplate.opsForSet().isMember(key, cartItem))) {
            List<CartItem> cartItems = getUserCartItems(userId);
            CartItem cartItem2 = cartItems.stream()
                    .filter(cartItem1 -> cartItem1.equals(cartItem)).findFirst().orElse(null);
            if (cartItem == null || cartItem2 ==null) {
                return false;
            }
            Objects.requireNonNull(cartItem)
                    .setQuantity(Objects.requireNonNull(cartItem2).getQuantity() + cartItem.getQuantity());
            redisTemplate.delete(key);
        }
        redisTemplate.opsForSet().add(key, cartItem);
        return true;
    }

    public List<CartItem> getUserCartItems(String userId) {
        return new ArrayList<>(Objects.requireNonNull(redisTemplate.opsForSet().members(Common.getRedisKey(userId).getKey())));
    }

    public boolean removeUserCartItems(String userId) {
        //移除购物车商品
        redisTemplate.delete(Common.getRedisKey(userId).getKey());
        return true;
    }

    public void warmUp(){
        redisTemplate.randomKey();
    }

}
