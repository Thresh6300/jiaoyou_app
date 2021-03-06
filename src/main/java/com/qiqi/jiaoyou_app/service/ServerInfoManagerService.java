package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.ServerInfoManager;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * 服务管理信息表(ServerInfoManager)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 15:45:55
 */
public interface ServerInfoManagerService extends IService<ServerInfoManager>
{


    ResultUtils geServerType();

    ResultUtils serverList(ServerInfoManager serverInfoManager);

    ResultUtils serverDetail(Integer id,Integer userId);
}