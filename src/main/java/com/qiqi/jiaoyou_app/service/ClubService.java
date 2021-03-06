package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.Club;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.pojo.ClubNotice;
import com.qiqi.jiaoyou_app.util.ResultUtils;

import java.util.List;


/**
 * 俱乐部(Club)表服务接口
 *
 * @author cfx
 * @since 2020-11-27 09:36:34
 */
public interface ClubService extends IService<Club>
{

    /**
     * 通过ID查询单条数据
     *
     * @param club 主键
     * @return 实例对象
     * @author cfx
     * @since 2020-11-27 09:36:34
     */
    ResultUtils selectById(Club club);

    ResultUtils addClub(Club club);

    ResultUtils inviteBuddy(ClubBuddy clubBuddy);

    ResultUtils setSecretary(ClubBuddy clubBuddy);

    ResultUtils setClub(Club club);

    ResultUtils delBuddy(ClubBuddy clubBuddy);

    ResultUtils quitBuddy(ClubBuddy clubBuddy);

    ResultUtils clubList(Club club);

    ResultUtils isSecretaryStatus(ClubBuddy clubBuddy);

    ResultUtils dissolveClub(Club club);

    ResultUtils setClubBuddy(ClubBuddy clubBuddy);

    ResultUtils clubSearchList(Club club);

    ResultUtils applyfor(ClubNotice clubNotice);

    ResultUtils inClubStatus(ClubBuddy clubBuddy);

    ResultUtils addApplyfor(ClubNotice clubNotice);


    ResultUtils clubNotice(Integer userId);

    ResultUtils delNotice(Integer buddyNoticeId);

    ResultUtils ranking(Integer clubId, Integer type);

    ResultUtils isclubNotice(Integer userId);

    List<Club> getQuestionsTime(Integer[] clubIds);
}