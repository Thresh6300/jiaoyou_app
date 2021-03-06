package com.qiqi.jiaoyou_app.config;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.controller.award.BaseController;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.server.response.ResponseCode;
import com.qiqi.jiaoyou_app.server.response.msg.MsgSendMsgResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author : 邹智敏
 * @data : 2018/3/30
 * @Description :  切面答应日志
 **/
@Aspect
@Component
public class HttpAspect extends BaseController
{

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    private Long startTime = 0L;

    private Long endTime = 0L;

    @Pointcut("execution(public * com.qiqi.jiaoyou_app.controller.*.*(..)))")
    public void valid()
    {
    }

    /**
     * 前置通知
     */
    @Before("valid()")
    public void doBefore(JoinPoint joinPoint)
    {

	  logger.info("<=========================================开始请求====================================>");

	  startTime = System.currentTimeMillis();
	  ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	  HttpServletRequest request = attributes.getRequest();

	  /*在这里进行token的解析和用户登录的时间的更新操作
	  * */

	  Signature signature = joinPoint.getSignature();
	  MethodSignature methodSignature = (MethodSignature) signature;
	  String[] parameterNames = methodSignature.getParameterNames();

	  Object[] args = joinPoint.getArgs();

	  for (int i = 0; i < args.length; i++)
	  {
		if ("token".equals(parameterNames[i]))
		{
		    /*如果用户的token存在，那么久更新这个token的最近登录时间*/
		    Boolean bl = UpdateOnline(args[i].toString());
		    System.out.println(parameterNames[i] + "::" + args[i].toString() + "::" + bl);
		    if (!bl)
		    {
			  UpdateOnline(args[i].toString());
		    }
		}

	  }


	  //url
	  logger.info("[请\t\t求\t路\t径]{}", "[" + request.getRequestURL() + "]");

	  for (int i = 0; i < args.length; i++)
	  {
		logger.info("[请\t\t求\t参\t数]{}", "[" + parameterNames[i] + ":" + args[i] + "]");
	  }

	  //method
	  logger.info("[请\t\t求\t方\t法]{}", "[" + request.getMethod() + "]");

	  //ip
	  logger.info("[远\t\t程\t地\t址]{}", "[" + request.getRemoteAddr() + "]");

	  //类方法
	  logger.info("[请\t\t求\t方\t法]{}", "[" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "]");

	  String str = joinPoint.getArgs().toString();
	  // System.out.println(str);
	  //参数
	  logger.info("[参\t\t数\t类\t型]{}", "[" + str.substring(str.lastIndexOf(".") + 1, str.lastIndexOf(";")) + "]");
    }

    /**
     * 后置通知
     */
    @After("valid()")
    public void doAfter()
    {
	  endTime = System.currentTimeMillis();

	  //参数
	  logger.info("[消\t\t耗\t时\t长]{}", "[" + (endTime - startTime) + "ms" + "]");

	  logger.info("<=========================================结束请求====================================>");
    }

    /**
     * 通知后返回的数据
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "valid()")
    public void doAfterReturning(Object object)
    {
	  try
	  {
		/*            logger.info("[返\t\t回\t参\t数]\n{}", ForMatJSONStr.format(JSON.toJSONString(object)));*/
	  }
	  catch (NullPointerException e)
	  {
		logger.info("[异\t\t常\t信\t息]{}", "[" + e.getMessage() + "]");
	  }
    }
}
