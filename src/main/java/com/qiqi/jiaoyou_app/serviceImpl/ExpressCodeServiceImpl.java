package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.ExpressCodeMapper;
import com.qiqi.jiaoyou_app.pojo.ExpressCode;
import com.qiqi.jiaoyou_app.service.ExpressCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 快递公司编码(ExpressCode)表服务实现类
 * expressCode
 *
 * @author cfx
 * @since 2020-12-05 14:07:30
 */
@Slf4j
@Service("expressCodeService")
public class ExpressCodeServiceImpl extends ServiceImpl<ExpressCodeMapper, ExpressCode> implements ExpressCodeService
{

}