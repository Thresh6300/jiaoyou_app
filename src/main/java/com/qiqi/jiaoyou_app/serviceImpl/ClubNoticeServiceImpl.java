package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.ClubNoticeMapper;
import com.qiqi.jiaoyou_app.pojo.ClubNotice;
import com.qiqi.jiaoyou_app.service.ClubNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (ClubNotice)表服务实现类
 * clubNotice
 *
 * @author cfx
 * @since 2020-12-14 17:11:57
 */
@Slf4j
@Service("clubNoticeService")
public class ClubNoticeServiceImpl extends ServiceImpl<ClubNoticeMapper, ClubNotice> implements ClubNoticeService
{

}