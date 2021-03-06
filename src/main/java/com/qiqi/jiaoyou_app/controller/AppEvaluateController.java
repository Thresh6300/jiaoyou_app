package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.pojo.Evaluate;
import com.qiqi.jiaoyou_app.pojo.OrderManage;
import com.qiqi.jiaoyou_app.service.EvaluateService;
import com.qiqi.jiaoyou_app.service.OrderManageService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评价(Evaluate)表控制层
 *
 * @author cfx
 * @since 2020-12-05 18:45:41
 */
@RestController
@RequestMapping("/jiaoyou_app/evaluate")
@Api(tags = " 评价管理")
public class AppEvaluateController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-05 18:45:41
     */
    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private OrderManageService orderManageService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public void list(Evaluate evaluate)
    {
    }

    @RequestMapping("insert")
    @ApiOperation(value = "添加", httpMethod = "POST")
    public ResultUtils insert(@RequestBody Evaluate evaluate)
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (evaluateService.insert(evaluate))
	  {
		OrderManage orderManage = orderManageService.selectById(evaluate.getEvaluateOrderId());
		if (orderManage != null)
		{
		    orderManage.setIsEvaluate("1");
		    orderManageService.updateById(orderManage);
		    return resultUtils;
		}
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;

    }



}