package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.ServerInfoManagerMapper;
import com.qiqi.jiaoyou_app.mapper.ServerInfoTypeMapper;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ServerInfoManager;
import com.qiqi.jiaoyou_app.pojo.ServerInfoType;
import com.qiqi.jiaoyou_app.service.CollectService;
import com.qiqi.jiaoyou_app.service.ServerInfoManagerService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务管理信息表(ServerInfoManager)表服务实现类
 * serverInfoManager
 *
 * @author cfx
 * @since 2020-12-03 15:45:55
 */
@Slf4j
@Service("serverInfoManagerService")
public class ServerInfoManagerServiceImpl extends ServiceImpl<ServerInfoManagerMapper, ServerInfoManager> implements ServerInfoManagerService
{
    @Autowired
    private ServerInfoManagerMapper serverInfoManagerMapper;
    @Autowired
    private ServerInfoTypeMapper serverInfoTypeMapper;
    @Autowired
    private CollectService collectService;

    @Override
    public ResultUtils geServerType()
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  List<ServerInfoType> serverInfoTypes = serverInfoTypeMapper.selectList(new EntityWrapper<ServerInfoType>().orderBy("server_sort").eq("server_status", "1"));
	  if (serverInfoTypes == null)
		return resultUtils;
	  ServerInfoType serverInfoType = new ServerInfoType();
	  serverInfoType.setServerType("0");
	  serverInfoType.setServerTitle("商品");
	  serverInfoType.setServerIcon("/upload/2020-12-17/20201217160839371.jpg");
	  serverInfoTypes.add(serverInfoType);
	  resultUtils.setData(serverInfoTypes);
	  return resultUtils;
    }

    @Override
    public ResultUtils serverList(ServerInfoManager serverInfoManager)
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  EntityWrapper<ServerInfoManager> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("server_is_top", false).orderBy("id",false).eq("server_status", "1")
			.eq(serverInfoManager.getServerTypeId()!=null,"server_type_id", serverInfoManager.getServerTypeId())
	            .like(StringUtils.isNotBlank(serverInfoManager.getCity()),"city", serverInfoManager.getCity());
	  if (StringUtils.isNotBlank(serverInfoManager.getSearchValue()))
	  {
		wrapper.like("business_name",serverInfoManager.getSearchValue())
			    .or().like("server_title",serverInfoManager.getSearchValue())
			    .or().like("place",serverInfoManager.getSearchValue());
	  }
	  Page page = new Page(serverInfoManager.getPageNum(), serverInfoManager.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<ServerInfoManager> serverInfoManagers = serverInfoManagerMapper.selectPage(page, wrapper);
	  if (serverInfoManagers == null)
		return resultUtils;
	  resultUtils.setData(serverInfoManagers);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils serverDetail(Integer id, Integer userId)
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  ServerInfoManager serverInfoManager = serverInfoManagerMapper.selectById(id);
	  if (serverInfoManager == null)
		return resultUtils;
	  EntityWrapper<Collect> wrapper = new EntityWrapper<>();
	  wrapper.eq("collect_server_id", id).eq("collect_user_id", userId);
	  int i = collectService.selectCount(wrapper);
	  if (i > 0)
		serverInfoManager.setIsLike("0");
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(serverInfoManager);
	  return resultUtils;
    }
}