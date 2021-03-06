package com.qiqi.jiaoyou_app.serviceImpl;

import com.qiqi.jiaoyou_app.pojo.Account;
import com.qiqi.jiaoyou_app.mapper.AccountMapper;
import com.qiqi.jiaoyou_app.service.IAccountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台管理员表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
