package com.qiqi.jiaoyou_app.controller;

import com.qiqi.jiaoyou_app.pojo.Address;
import com.qiqi.jiaoyou_app.service.AddressService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (Address)表控制层
 *
 * @author cfx
 * @since 2020-12-03 16:17:54
 */
@RestController
@RequestMapping("/jiaoyou_app/address")
@Api(tags = "地址管理")
public class AddressController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 16:17:54
     */
    @Autowired
    private AddressService addressService;

    @GetMapping("getMyAddress")
    @ApiOperation("我的地址")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "addressUserId", value = "用户id", required = true, dataType = "Integer"),
    })
    public ResultUtils getMyAddress(Integer addressUserId)
    {

	  return addressService.getMyAddress(addressUserId);
    }

    @PostMapping("insert")
    @ApiOperation("添加收货地址")
    public ResultUtils insert(@RequestBody Address address)
    {

	  return addressService.add(address);
    }

    @PostMapping("update")
    @ApiOperation("修改收货地址")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "addressId", value = "id", required = true, dataType = "Integer"),
    })
    public ResultUtils update(@RequestBody Address address)
    {
	  return addressService.up(address);
    }

    @PostMapping("defaultAddress")
    @ApiOperation("设置默认收货地址")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "addressId", value = "id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "addressUserId", value = "用户id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "status", value = "收货地址状态0默认;1不默认", required = true, dataType = "String"),
    })
    public ResultUtils defaultAddress(@RequestBody Address address)
    {
	  return addressService.updateStatus(address);
    }

    @GetMapping("delete")
    @ApiOperation("删除收货地址")
    @ApiImplicitParam(name = "addressId", value = "id", required = true, dataType = "Integer")
    public ResultUtils delete(Integer addressId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (addressService.deleteById(addressId))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }
}