package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.Problem;
import com.qiqi.jiaoyou_app.mapper.ProblemMapper;
import com.qiqi.jiaoyou_app.service.IProblemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 常见问题表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public ResultUtils problemList(Integer pageSize, Integer pageNum, String keyWord) {
        ResultUtils resultUtils = new ResultUtils();
        Page page = new Page(pageNum, pageSize);
        page.setOptimizeCountSql(true);
        page.setSearchCount(true);
        List<Problem> problemList = problemMapper.selectPage(page, new EntityWrapper<Problem>().like(!StringUtils.isEmpty(keyWord), "title", keyWord).eq("enableState", 1).eq("deleteState", 2).orderBy("addTime",false));
        resultUtils.setData(problemList);
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        return resultUtils;
    }
}
