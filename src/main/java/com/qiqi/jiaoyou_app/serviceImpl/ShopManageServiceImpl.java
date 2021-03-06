package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.ShopManageMapper;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.service.CollectService;
import com.qiqi.jiaoyou_app.service.ShopManageService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理(ShopManage)表服务实现类
 * shopManage
 *
 * @author cfx
 * @since 2020-12-03 13:37:19
 */
@Slf4j
@Service("shopManageService")
public class ShopManageServiceImpl extends ServiceImpl<ShopManageMapper, ShopManage> implements ShopManageService
{
    @Autowired
    private ShopManageMapper shopManageMapper;
    @Autowired
    private CollectService collectService;

    @Override
    public ResultUtils shopList(ShopManage shopManage)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());

	  EntityWrapper<ShopManage> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("shop_is_top", false).orderBy("id",false).eq("shop_status", "1").like(StringUtils.isNotBlank(shopManage.getCity()), "city", shopManage.getCity());

	  Page page = new Page(shopManage.getPageNum(), shopManage.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<ShopManage> shopManages = shopManageMapper.selectPage(page, wrapper);
	  if (shopManages == null || shopManages.size() == 0)
		return resultUtils;
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  resultUtils.setData(shopManages);
	  return resultUtils;
    }

    @Override
    public ResultUtils shopDetail(Integer id, Integer userId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ShopManage());
	  ShopManage shopManage = shopManageMapper.selectById(id);
	  if (shopManage == null)
		return resultUtils;
	  EntityWrapper<Collect> wrapper = new EntityWrapper<>();
	  wrapper.eq("collect_shop_id", id).eq("collect_user_id", userId);
	  int i = collectService.selectCount(wrapper);
	  if (i > 0)
		shopManage.setIsLike("0");
	  resultUtils.setData(shopManage);
	  return resultUtils;
    }

}