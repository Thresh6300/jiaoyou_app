package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.Instant.GroupJson;
import com.qiqi.jiaoyou_app.pojo.Lable;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.MemberVo;

import java.util.List;

/**
 * <p>
 * app会员表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IMemberService extends IService<Member> {

    ResultUtils login(Member member);

    ResultUtils membershipDetails(Integer id);

    ResultUtils yenValueRegister(Member member);

    ResultUtils editPassword(Member member);

    ResultUtils registrationRiders(Member member);

    ResultUtils recommendAFriend(Integer id,Integer numberOfReferrals);

    ResultUtils ridersMatch(Integer id, String longitude, String latitude, Integer pageNum, Integer pageSize, Integer sex, Integer startAge, Integer endAge, String type);

    ResultUtils soulMatch(Integer id, String longitude, String latitude, Integer pageNum, Integer pageSize, Integer sex, Integer startAge, Integer endAge, String type);

    ResultUtils charmList(Integer numberOfPeople,Integer type);
    List<MemberVo> charmList();
    ResultUtils prideList(Integer numberOfPeople,Integer type);
    List<MemberVo> prideList();
    ResultUtils updatePhone(Member member);

    ResultUtils verificationCode(Member member);

    ResultUtils updatePassWord(Member member);

    ResultUtils meteorShowerDetails(Integer pageSize,Integer pageNum,Integer id);

    ResultUtils newCustomLabel(Lable lable);

    ResultUtils modifyLabelSelection(Lable lable);

    ResultUtils modifyCustomValues(Lable lable);

    ResultUtils changeYourAvatarAndNickname(Member member);

    ResultUtils modifyCity(Member member);

    ResultUtils modifyPersonalData(Member member);

    ResultUtils canISynchronize(Integer id,Integer type);

    ResultUtils codeLogin(Member member);

    ResultUtils editBackGroundImage(Member member);

    ResultUtils logout(Integer memberId);

    ResultUtils pushId(Member member);

    ResultUtils customerList();

    ResultUtils faceContrast(String head, String image);

    ResultUtils registrationBlack(Member member);

    ResultUtils validationToken(String token);

    ResultUtils uploadAddress(Member member);

    ResultUtils invitationThisMonth(Integer memberId);

    ResultUtils addGroupMember(GroupJson groupJson);

    ResultUtils aopCalculateTheDistance(Integer memberId, Integer otherPartyId);

    ResultUtils isHavePhone(String phone);

    ResultUtils delFriend(Integer memberId,Integer otherId);

    ResultUtils registrationComplete(Lable lable);

    ResultUtils create_group(GroupJson groupJson);

    Member getInfoByToken(String Token);
}
