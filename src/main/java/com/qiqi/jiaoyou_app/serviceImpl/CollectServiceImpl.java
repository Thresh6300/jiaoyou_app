package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.CollectMapper;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.service.CollectService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 喜欢(Collect)表服务实现类
 * collect
 *
 * @author cfx
 * @since 2020-12-03 15:30:46
 */
@Slf4j
@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService
{
    @Autowired
    private CollectMapper collectMapper;


    @Override
    public ResultUtils add(Collect collect)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  Integer insert = collectMapper.insert(collect);
	  if (insert == 0)
		return resultUtils;
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  return resultUtils;
    }

    @Override
    public ResultUtils cancelCollec(Collect collect)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  EntityWrapper<Collect> wrapper = new EntityWrapper<>();
	  wrapper.eq(collect.getCollectUserId()!=null,"collect_user_id",collect.getCollectUserId())
			.eq(collect.getCollectShopId()!=null,"collect_shop_id",collect.getCollectShopId())
			.eq(collect.getCollectServerId()!=null,"collect_server_id",collect.getCollectServerId());

	  Integer insert = collectMapper.delete(wrapper);
	  if (insert == 0)
		return resultUtils;
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  return resultUtils;

    }

    @Override
    public ResultUtils collectMyList(Collect collect)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  List<ShopManage> shopManages = collectMapper.collectMyList(collect);
	  int total = collectMapper.count(collect);
	  int pages = (int) Math.ceil((double) total / (double) collect.getPageSize());
	  if (shopManages == null)
		return resultUtils;
	  resultUtils.setData(shopManages);
	  resultUtils.setCount(total);
	  resultUtils.setPages(pages);
	  return resultUtils;
    }

    //服务商品搜索部分
    //author:nan
    //time:20210119
    @Override
    public ResultUtils searchList(ShopManage shopManage)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  // shopManage.setOrderByColumn(StringUtils.humpToLine(shopManage.getOrderByColumn()));
	  List<ShopManage> shopManages = collectMapper.searchList(shopManage);
	  Integer total = collectMapper.searchCount(shopManage);
	  int pages = (int) Math.ceil((double) total / (double) shopManage.getPageSize());
	  if (shopManages == null)
		return resultUtils;
	  resultUtils.setData(shopManages);
	  resultUtils.setCount(total);
	  resultUtils.setPages(pages);
	  return resultUtils;

    }

}