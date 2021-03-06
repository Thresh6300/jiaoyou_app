package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * 喜欢(Collect)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 15:30:46
 */
public interface CollectService extends IService<Collect>
{


    ResultUtils add(Collect collect);

    ResultUtils cancelCollec(Collect collect);

    ResultUtils collectMyList(Collect collect);

    ResultUtils searchList(ShopManage shopManage);
}