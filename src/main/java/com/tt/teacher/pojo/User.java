package com.tt.teacher.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/** @作者：songce
*   @时间：2018/11/16 8:19
*   @描述：
*/
public class User implements Serializable{
    private String name;//用户的名字
    private MultipartFile photoFile;//存放头像的文件

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }



}
