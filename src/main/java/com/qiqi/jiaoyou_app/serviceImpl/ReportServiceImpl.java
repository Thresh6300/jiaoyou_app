package com.qiqi.jiaoyou_app.serviceImpl;

import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.SystemMessageMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.Report;
import com.qiqi.jiaoyou_app.mapper.ReportMapper;
import com.qiqi.jiaoyou_app.pojo.SystemMessage;
import com.qiqi.jiaoyou_app.service.IReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SystemMessageMapper systemMessageMapper;

    @Override
    public ResultUtils report(Report report) {
        ResultUtils resultUtils = new ResultUtils();
        Member member = memberMapper.selectById(report.getReportInformantId());
        Member member1 = memberMapper.selectById(report.getReportBeiInformantId());
        report.setReportInformantHead(member.getHead());
        report.setReportInformantNickName(member.getNickName());
        report.setReportInformantPhone(member.getPhone());
        report.setReportBeiInformantHead(member1.getHead());
        report.setReportBeiInformantNickName(member1.getNickName());
        report.setReportBeiInformantPhone(member1.getPhone());
        report.setReportAddTime(new Timestamp(System.currentTimeMillis()));
        report.setReportExamineState(1);
        Integer insert = reportMapper.insert(report);
        if (insert <= 0){
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage(Constant.REPORT_FAILURE);
        }else {
            resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
            resultUtils.setMessage(Constant.SUCCESSFUL_REPORT);
            //在这里增加举报成功的信息到数据库,是在systemmessage表里面
		SystemMessage systemMessage = new SystemMessage();
		systemMessage.setAddTime(new Date());
		systemMessage.setEnableState(1);
		systemMessage.setDeleteState(2);
		systemMessage.setTitle("举报结果");
		systemMessage.setContext("收到您在"+new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒" ).format(new Date())+"提交的对用户\""+member.getNickName()+"\"的举报，Clevel裁判系统将尽快进行审核处理，感谢您的积极举报，我们对破坏平台环境的行为决不姑息!");
		systemMessage.setMemberId(report.getReportInformantId());
		systemMessageMapper.insert(systemMessage);

        }
        return resultUtils;
    }
}
