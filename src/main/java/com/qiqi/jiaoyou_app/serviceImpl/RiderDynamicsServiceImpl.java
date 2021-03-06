package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IRiderDynamicsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.DateUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车友圈动态 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class RiderDynamicsServiceImpl extends ServiceImpl<RiderDynamicsMapper, RiderDynamics> implements IRiderDynamicsService {

    @Autowired
    private RiderDynamicsMapper riderDynamicsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private GoodFriendMapper goodFriendMapper;
    @Autowired
    private DynamicCommentsOfCarUsersMapper dynamicCommentsOfCarUsersMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private FabulousMapper fabulousMapper;

    @Override
    public ResultUtils ridersDynamicList(Integer pageSize, Integer pageNum, Integer id) {
        //好友动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
        ResultUtils resultUtils = new ResultUtils();
        //分页
        Page page = new Page(pageNum, pageSize);
        page.setOptimizeCountSql(true);
        page.setSearchCount(true);
        //过滤已被封号的会员
        List<Member> list = memberMapper.selectList(new EntityWrapper<Member>().eq("enableSate", 1));
        Integer [] integers1 = new Integer[list.size()];
        for (int i = 0;i < list.size();i++){
            integers1[i] = list.get(i).getId();
        }
        List<RiderDynamics> riderDynamics = riderDynamicsMapper.selectPage(page, new EntityWrapper<RiderDynamics>().in("memerId",integers1).orderBy("addTime", false));
        for (RiderDynamics riderDynamics1 : riderDynamics) {
            riderDynamics1.setAddTimeStr(DateUtils.getShortTime(riderDynamics1.getAddTime()));
        }
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setData(riderDynamics);
        resultUtils.setCount((int) page.getTotal());
        resultUtils.setData1(page);
        return resultUtils;
    }

    @Override
    public ResultUtils myRidersDynamicList(Integer pageSize, Integer pageNum, Integer id) {
        //我的动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
        ResultUtils resultUtils = new ResultUtils();
        //查询出会员信息
        Member member = memberMapper.selectById(id);
        //查询出该会员下的列表（分页）
        Page page = new Page(1, 10);
        page.setOptimizeCountSql(true);
        page.setSearchCount(true);
        //查询并set进member
        List<RiderDynamics> riderDynamics = riderDynamicsMapper.selectPage(page, new EntityWrapper<RiderDynamics>().eq("memerId", id).orderBy("addTime", false));
        member.setRiderDynamicsList(riderDynamics);
        for (RiderDynamics riderDynamics1 : riderDynamics) {
            riderDynamics1.setAddTimeStr(DateUtils.getShortTime(riderDynamics1.getAddTime()));
        }
        //设置返回值
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(member);
        resultUtils.setCount((int) page.getTotal());
        resultUtils.setData1(page);
        return resultUtils;
    }

    @Override
    public ResultUtils dynamicDetails(Integer pageSize, Integer pageNum, Integer id, String token) {
        //动态详情：用户头像，昵称，性别，年龄，所在城市，标题，发布时间，动态内容，点赞量，评论量；评论列表：用户头像，昵称，评论时间，评论内容（文字），操作（发表评论，转发）
        ResultUtils resultUtils = new ResultUtils();
        //获取到该动态
        RiderDynamics riderDynamics = riderDynamicsMapper.selectById(id);
        if (riderDynamics == null) {
            resultUtils.setMessage(Constant.DYNAMIC_NOT_FOUND);
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        //获取到该动态的评论内容(分页)
        Page page = new Page(pageNum, pageSize);
        page.setOptimizeCountSql(true);
        page.setSearchCount(true);
        //查询出一级评论
        List<DynamicCommentsOfCarUsers> dynamicCommentsOfCarUsersList = dynamicCommentsOfCarUsersMapper.dynamicDetails(riderDynamics.getId());
        while (dynamicCommentsOfCarUsersList.remove(null)) {
            ;//巧妙利用循环删除
        }
        riderDynamics.setDynamicCommentsOfCarUsers(dynamicCommentsOfCarUsersList);
        List<Member> tokens = memberMapper.selectList(new EntityWrapper<Member>().eq("token", token));
        Integer id1 = tokens.get(0).getId();
        List<Member> list = new ArrayList<>();
        List<Fabulous> fabulous1 = fabulousMapper.selectList(new EntityWrapper<Fabulous>().eq("dynamicId", riderDynamics.getId()).eq("type", 2));
        Integer[] integers = new Integer[fabulous1.size()];
        if (fabulous1.size() > 0) {
            for (int i = 0; i < fabulous1.size(); i++) {
                integers[i] = fabulous1.get(i).getMemberId();
            }
            List<Member> id2 = memberMapper.selectList(new EntityWrapper<Member>().in("id", integers));
            for (Member member : id2) {
                if (member.getId().equals(id1)) {
                    riderDynamics.setIsLike(1);
                } else {
                    riderDynamics.setIsLike(2);
                }
                list.add(member);
            }
        } else {
            riderDynamics.setIsLike(2);
        }
        riderDynamics.setList(list);
        riderDynamics.setAddTimeStr(DateUtils.getShortTime(riderDynamics.getAddTime()));
        //设置返回值
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(riderDynamics);
        resultUtils.setCount((int) page.getTotal());
        return resultUtils;
    }

    @Override
    public ResultUtils del(Integer id) {
        ResultUtils resultUtils = new ResultUtils();
        Integer integer = riderDynamicsMapper.deleteById(id);
        if (integer <= 0) {
            resultUtils.setMessage(Constant.DELETION_FAILED);
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        resultUtils.setMessage(Constant.DELETION_SUCCEEDED);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        return resultUtils;
    }
}
