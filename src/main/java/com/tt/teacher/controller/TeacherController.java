package com.tt.teacher.controller;

import com.tt.teacher.pojo.User;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/** @作者：songce
*   @时间：2018/11/16 8:19
*   @描述：
*/
@Controller
public class TeacherController {
    @RequestMapping("/ok")
    public String ok(){
        return "ok";
    }
    @GetMapping("/teac_put")
    public String teac_put() {
        return "teac_put";
    }

    @GetMapping("/teac_Object")
    public String teac_Object() {
        return "teac_Object";
    }
    /**
     * @作者：matao
     * @时间：2018/11/16 0015 下午 9:37
     * @方法名：fileLoad
     * @描述：该方法，可以实现文件上传。注意事项如下
     * @温馨提示：
     *  1.请将页面表单提交方式改为post
     *  2.加入enctype设置为multipart/form-data
     *  3.参数@RequestParam("filePhoto")，要和前端文件控件相同
     */
    @PostMapping(value = "/fileLoad")
    public String fileLoad(HttpServletRequest request, @RequestParam("filePhoto") MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            //1.path：为上传的目录，参数可以改为项目要求的(可改)
            String path = request.getServletContext().getRealPath("/upload/");
            //2.可以打印出，自己去检测是否正确(不可改)
            System.out.println(path);
            //3.查看原文件的名称(不可改)
            String fileName = file.getOriginalFilename();
            //4.根据目录和文件名，进行判断和创建(不可改)
            File filePath = new File(path,fileName);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            //5.将前端接收的file文件，传入刚创建的目录文件中(不可改)
            file.transferTo(new File(path+ File.separator+fileName));
            //6.上传成功跳转的页面(可改)
            return "ok";
        }
        //7.上传失败跳转的页面(可改)
        return "no";
    }
    /**
     * @作者：songce
     * @时间：2018/11/16 0015 下午 11:00
     * @方法名：objectLoad
     * @描述：该方法，可以实现使用对象方式接收上传文件
     * @温馨提示：
     *  1.请将页面表单提交方式改为post
     *  2.加入enctype设置为multipart/form-data
     *  3.因为是对象接收，需要在对应的对象中加入private MultipartFile photoFile;
     *  4.@ModelAttribute User user,前端表单中的控件名称必须与User对象中的属性对应
     */

    @PostMapping(value = "/objectLoad")
    public String objectLoad(HttpServletRequest request, @ModelAttribute User user, Model model) throws IOException {
        if (!user.getPhotoFile().isEmpty()){
            //1.path：为上传的目录，参数可以改为项目要求的(可改)
            String path = request.getServletContext().getRealPath("/upload/");
            //2.可以打印出，自己去检测是否正确(不可改)
            System.out.println(path);
            //3.查看原文件的名称(不可改)
            String fileName = user.getPhotoFile().getOriginalFilename();
            //4.根据目录和文件名，进行判断和创建(不可改)
            File filePath = new File(path,fileName);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            //5.将前端接收的file文件并传入user对象中，传入刚创建的目录文件中(不可改)
            user.getPhotoFile().transferTo(new File(path+File.separator+fileName));
            //6.可以将user存放到request,session等请求中，进行其他操作(可改)
            request.setAttribute("user",user);
            //7.上传成功跳转的页面(可改)
            return "ok";
        }
        //8.上传失败跳转的页面(可改)
        return "no";
    }

    /**
     * @作者：songce
     * @时间：2018/11/16 0015 下午 11:05
     * @方法名：downLoad
     * @描述：该方法，可以实现文件下载
     * @注意事项：
     *  1.下载工具类，只需提供下载文件的路径(path) 与 名字(filename)
     */
    @GetMapping("/downLoad")
    public ResponseEntity<byte[]> downLoad(HttpServletRequest request, @RequestParam("filename") String filename, @RequestHeader("User-Agent") String userAgent, Model model) throws IOException {
        //1.path：为下载的目录，参数可以改为项目要求的(可改)
        String path = request.getServletContext().getRealPath("/upload/");
        File file = new File(path+File.separator+filename);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(file.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        filename = URLEncoder.encode(filename,"UTF-8");
        if(userAgent.indexOf("MSIE")>0){
            builder.header("Content-Disposition","attachment;filename="+filename);
        }else {
            builder.header("Content-Disposition","attachment;filename*=UTF-8''"+filename);
        }
        return builder.body(FileUtils.readFileToByteArray(file));
    }

}
