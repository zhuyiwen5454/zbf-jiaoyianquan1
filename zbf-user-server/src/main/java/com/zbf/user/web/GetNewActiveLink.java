package com.zbf.user.web;

import com.zbf.common.utils.FreemarkerUtils;
import com.zbf.user.entity.BaseUser;
import com.zbf.user.service.IBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 激活
 */
@RestController
public class GetNewActiveLink {

    @Autowired
    private IBaseUserService baseUserService;

    @RequestMapping("/getNewActiveLink")
    public String getNewActiveLink(String loginName){

        BaseUser baseUser = baseUserService.getBaseUserByLoginName(loginName);
        if(baseUser!=null){
            //如果loginName存在则注册成功  同时激活 将status修改为1
            baseUser.setStatus(1);
            baseUserService.saveOrUpdate(baseUser);
            System.out.println("激活成功");
            return "<a href=\"http://localhost:8080\">激活成功!</a>";
        }
        return "<a href='http://10.162.6.46:10000/user/getNewActiveLink?loginName=\"+loginName+\"'>激活失败!请重新激活!</a>";
    }

    @RequestMapping("/updatePassword")
    public boolean updatePassword(@RequestBody BaseUser baseUser){
        System.out.println("修改密码传参:"+baseUser);

        //根据用户名查询出修改的对象
        String userName = baseUser.getUserName();
        BaseUser baseUserByUserName = baseUserService.getBaseUserByUserName(userName);
        //修改密码
        baseUserByUserName.setPassWord(baseUser.getPassWord());
        //保存修改后的密码
        baseUserService.saveOrUpdate(baseUserByUserName);
        System.out.println("密码修改成功");
        return true;
    }
}
