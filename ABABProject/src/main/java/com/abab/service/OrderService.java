package com.abab.service;

import com.abab.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 故故sb
* @description 针对表【order】的数据库操作Service
* @createDate 2022-09-09 09:06:27
*/
public interface OrderService extends IService<Order> {

    void updateOrderToSuccessPay(Long out_trade_no);
}
