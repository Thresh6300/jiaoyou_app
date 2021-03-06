package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 喜欢(Collect)表数据库访问层
 *
 * @author cfx
 * @since 2020-12-03 15:30:45
 */
@Repository
public interface CollectMapper extends BaseMapper<Collect>
{
    List<ShopManage> collectMyList(Collect collect);

    Integer count(Collect collect);

    List<ShopManage> searchList(ShopManage shopManage);

    Integer searchCount(ShopManage shopManage);

}