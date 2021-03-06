package com.qiqi.jiaoyou_app.controller;



import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.constants.PathParam;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Api(tags = {"上传接口"})
@RestController
@RequestMapping("/jiaoyou_app/file")
@CrossOrigin(allowCredentials = "true")
public class UploadFileController {


    @ApiOperation(value = "单图上传接口(已完成)")
    @PostMapping("/UploadFile")
    public ResultUtils UploadPicture(HttpServletResponse response, HttpServletRequest request, @RequestParam("file") MultipartFile files) throws IOException {
        ResultUtils resultUtils = new ResultUtils();
        //遍历并保存文件
        try {
            String filename = files.getOriginalFilename();
            String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date());
            String fileNames = newFileName + new Random().nextInt(1000) + "." + fileExt;
            // 获得文件上传日期,作为目录名一部分
            String suffix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String filePath = PathParam.uploadpath + suffix + "/" + fileNames;
            String filepaths ="/upload/" + suffix + "/" + fileNames;
            File localFile = new File(filePath);
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            files.transferTo(localFile);
            resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
            resultUtils.setMessage("上传成功");
            resultUtils.setData(filepaths);
            resultUtils.setCode(2);
        } catch (Exception e) {
            e.printStackTrace();
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage("上传文件失败");
            resultUtils.setCode(1);
        }
        return resultUtils;
    }



    @ApiOperation(value = "多图上传接口(已完成)")
    @PostMapping("/UploadFiles")
    public ResultUtils UploadPictures(HttpServletResponse response, HttpServletRequest request, @RequestParam("file") MultipartFile[] files) throws IOException {
        ResultUtils resultUtils = new ResultUtils();
        //遍历并保存文件
        try {
            String paths = "";
            for (int i = 0; i < files.length;i++){
                String filename = files[i].getOriginalFilename();
                String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Date());
                String fileNames = newFileName + new Random().nextInt(1000) + "."  + fileExt;;
                // 获得文件上传日期,作为目录名一部分
                String suffix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String filePath = PathParam.uploadpath + suffix + "/" + fileNames;
                String filepaths ="/upload/" + suffix + "/" + fileNames;
                File localFile = new File(filePath);
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                files[i].transferTo(localFile);
                resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
                resultUtils.setMessage("上传成功");
                resultUtils.setCode(2);
                if (i == files.length - 1){
                    paths += filepaths;
                }else {
                    paths += filepaths + ",";
                }

            }
            resultUtils.setData(paths);
        } catch (Exception e) {
            e.printStackTrace();
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage("上传文件失败");
            resultUtils.setCode(1);
        }
        return resultUtils;
    }
}
