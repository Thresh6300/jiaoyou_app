package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.vo.MemberVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * app会员表 Mapper 接口
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    List<Member> recommendAFriend(Map<String, Integer> map);

    List<Member> soulMatch(Map<String, Object> map);

    List<Member> ridersMatch(Map<String, Object> map);

    List<MemberVo> charmList(Map<String, Object> map);

    List<MemberVo> prideList(Map<String, Object> map);
}
