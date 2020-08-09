package com.fh.controller;

import com.fh.common.JsonData;
import com.fh.entity.po.Area;
import com.fh.entity.po.User;
import com.fh.service.UserService;
import com.fh.utlis.JWT;
import com.fh.utlis.OSSUtils;
import com.fh.utlis.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("addUser")
    public JsonData addUser(User user){
        user.setRegisterdate(new Date());
        userService.addUser(user);
        return JsonData.getJsonSuccess("注册成功");
    }



    @RequestMapping("selectArea")
    public  JsonData selectArea(){
        List<Area> list =  userService.selectArea();
        return  JsonData.getJsonSuccess(list);
    }
    @RequestMapping("queryByName")
    public  Map queryByName(String name){
       User user =  userService.queryByName(name);
        Map map=new HashMap();
        if(user==null){
            map.put("valid",true);

        }else {
            map.put("valid",false);
        }
        return  map;
    }
    @RequestMapping("uploadFIle")
    @ResponseBody
    public Map uploadFIle(@RequestParam("img") MultipartFile img, HttpServletRequest request) throws Exception {
        InputStream ins = img.getInputStream();
        File toFile = new File(img.getOriginalFilename());
        inputStreamToFile(ins,toFile);
        String s = OSSUtils.uploadFile(toFile);
        Map map=new HashMap();
        map.put("imgPath",s);
        return  map;
    }
    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @RequestMapping("loginUser")
    public JsonData loginUser(String name ,String password){
       User user =  userService.selectByName(name);
        Map loginMap = new HashMap();

      if(name!=null &&name!="" && name.equals(user.getName())){
          if(password!=null&&password.equals(user.getPassword())){
                  User user1 = new User();
                  user1.setPassword(password);
                  String sign = JWT.sign(user1, 1000 * 60 * 60 * 24);
                  RedisUse.set(name+"",sign,60*30);
                  String token = Base64.getEncoder().encodeToString((name + "," + password).getBytes());
                  loginMap.put("status",200);
                  loginMap.put("token",token);
                  loginMap.put("message","登录成功");
          }else {
              loginMap.put("status",400);
              loginMap.put("message","请输入正确的密码");
          }
      }else {
          loginMap.put("status",300);
          loginMap.put("message","账号为空或者不存在");
      }
        return JsonData.getJsonSuccess(loginMap);
    }


    @RequestMapping("queryUser")
    public JsonData queryUser(){
       List<User> list =  userService.queryUser();
        return JsonData.getJsonSuccess(list);
    }




}
