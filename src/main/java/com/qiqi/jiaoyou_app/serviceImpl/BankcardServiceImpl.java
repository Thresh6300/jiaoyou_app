package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.Bankcard;
import com.qiqi.jiaoyou_app.mapper.BankcardMapper;
import com.qiqi.jiaoyou_app.service.IBankcardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 银行卡表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class BankcardServiceImpl extends ServiceImpl<BankcardMapper, Bankcard> implements IBankcardService {

    @Autowired
    private BankcardMapper bankcardMapper;

    @Override
    public ResultUtils addBankcard(Bankcard bankcard) {
        ResultUtils resultUtils = new ResultUtils();
        bankcard.setAddTime(new Timestamp(System.currentTimeMillis()));
        Integer insert = bankcardMapper.insert(bankcard);
        if (insert <= 0){
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage("绑定银行卡失败");
        }
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage("绑定银行卡成功");
        return resultUtils;
    }

    @Override
    public ResultUtils bankCardList(Integer pageSize, Integer pageNum, Integer id) {
        ResultUtils resultUtils = new ResultUtils();
        Page page = new Page(pageNum, pageSize);
        page.setOptimizeCountSql(true);
        page.setSearchCount(true);
        List<Bankcard> memberId = bankcardMapper.selectPage(page, new EntityWrapper<Bankcard>().eq("memberId", id));
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(memberId);
        resultUtils.setCount((int)page.getTotal());
        return resultUtils;
    }
}
