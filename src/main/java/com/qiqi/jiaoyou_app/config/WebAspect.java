package com.qiqi.jiaoyou_app.config;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import com.qiqi.jiaoyou_app.util.JsonUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Component
@Aspect
public class WebAspect
{

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 切入点
     * 匹配com.example.controller1包及其子包下的所有类的所有方法
     */
    @Pointcut("execution( * com.qiqi.jiaoyou_app.controller.*.aop*(..))")
    public void executePackage()
    {
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("executePackage()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
	  //获取参数
	  Object[] args = proceedingJoinPoint.getArgs();
	  //获取token
	  String token = (String) Arrays.asList(args).get(0);
	  System.out.println(token);
	  ResultUtils resultUtils = memberService.validationToken(token);
	  // if (resultUtils.getStatus() != 200)
		// return resultUtils;
	  // 获取目标方法的返回值

	  //author:nan
	  //time:20210119
	  //如果这个用户的的账号是,被封闭的状态下,那么这里返回false
	  List<Member> memberList = memberMapper.selectList(new EntityWrapper<Member>().eq("token", token));

	  System.out.println("tokenValue"+ token);

	  // memberMapper.updateById(memberList.get(0));
	  if(memberList.size() > 0){
		//启用状态 1：启用2：禁用
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int ia = 0;ia<memberList.size();ia++){
		    if(memberList.get(ia).getEnableSate() == 2){
			  System.out.println("用户已被封号,"+token+","+sdf.format(new Date()));
			  ResultUtils resultUtils1 = new ResultUtils();
			  resultUtils1.setMessage("用户已被封号"+","+sdf.format(new Date()));
			  resultUtils1.setCode(700);
			  resultUtils1.setStatus(700);
			  resultUtils1.setUserStatus("LoginError");
			  return resultUtils1 ;
		    }else{
			  System.out.println("用户账号状态正常,"+token+","+sdf.format(new Date()) );
		    }
		}
	  }

	  return (ResultUtils) proceedingJoinPoint.proceed();
    }
}