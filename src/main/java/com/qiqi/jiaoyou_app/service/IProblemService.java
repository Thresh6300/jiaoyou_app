package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Problem;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 常见问题表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IProblemService extends IService<Problem> {

    ResultUtils problemList(Integer pageSize, Integer pageNum, String keyWord);
}
