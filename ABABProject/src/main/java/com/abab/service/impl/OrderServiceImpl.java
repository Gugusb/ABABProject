package com.abab.service.impl;

import com.abab.util.ConstUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abab.entity.Order;
import com.abab.service.OrderService;
import com.abab.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 故故sb
* @description 针对表【order】的数据库操作Service实现
* @createDate 2022-09-09 09:06:27
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Override
    public void updateOrderToSuccessPay(Long out_trade_no){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_no", out_trade_no);

        Order order = this.getOne(queryWrapper);

        order.setStatus(ConstUtil.ORDER_PAID);
        order.setPaymentTime(new Date());
        order.setUpdated(new Date());
        order.setDeliverTime(new Date());
        this.updateById(order);
    }

}




