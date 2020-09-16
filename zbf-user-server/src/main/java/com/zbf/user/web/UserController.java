package com.zbf.user.web;

import com.alibaba.nacos.common.util.Md5Utils;
import com.zbf.common.utils.MailQQUtils;
import com.zbf.common.utils.RanDomUtils;
import com.zbf.user.api.GetResCodeDao;
import com.zbf.user.entity.BaseUser;
import com.zbf.user.service.IBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * @author: zhuyiwen
 * 作者: zhuyiwen
 * 日期: 2020/9/7  23:54
 * 描述:
 */
@RestController
public class UserController {

    @Autowired
    private GetResCodeDao getCodeDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private IBaseUserService baseUserService;

    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    // 手机验证正则比对
    public boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }

    /**
     * 向手机号发送验证码
     * @param tel
     */
    @RequestMapping("getResCode")
    public void getResCode(@RequestParam("tel") String tel){
        if (isPhone(tel)){
            System.out.println(tel);
            getCodeDao.sendCode(tel);
        }
    }

    /**
     * 下一步功能,判断输入验证码和redis中的验证码是否一致
     * @param tel
     * @param code
     * @return
     */
    @RequestMapping("first")
    public boolean first(String tel,String code){
        // 从redis中获取去code值
        String code1 = redisTemplate.opsForValue().get("code");
        // 判断从前端传过来的值是否跟redis里存入的值相同
        // 如果相同删除redis中的数据
        if(code.equals(code1)){
            return true;
        }else{
            // 不一样就不通过
            System.out.println("手机号或验证码错误");
            return false;
        }
    }

    /**
     * 注册
     * @param baseUser
     * @return
     */
    @RequestMapping("register")
    public boolean register(@RequestBody BaseUser baseUser){
        System.out.println("baseUser:"+baseUser);
        System.out.println("注册de方法");

        // 获取前台传过来的密码
        String passWord = baseUser.getPassWord();
        // 密码加严
        baseUser.setSalt("zhuyiwen");
        String pw = passWord+"zhuyiwen";
        // 密码加密
        String md5 = Md5Utils.getMD5(pw, "utf8");
        // 存入加密的密码
        baseUser.setPassWord(md5);
        // 添加用户数据
        // 通过用户名查询用户信息
        BaseUser loginName = baseUserService.getBaseUserByLoginName(baseUser.getLoginName());
        // 通过邮箱查询用户信息
        BaseUser email = baseUserService.getBaseUserByEmail(baseUser.getEmail());
        // 通过手机号查询用户信息
        BaseUser phone = baseUserService.getBaseUserByTel(baseUser.getTel());
        //设置激活状态为未激活状态用0表示 (默认值)
        baseUser.setStatus(0);
        // 从redis中获取去code值
        String code1 = redisTemplate.opsForValue().get("code");
        //将获取到的值存入baseUser对象
        baseUser.setActiCode(code1);
        // 优化注册(存在重复的则注册失败)
        if(loginName == null && email == null && phone == null){
            baseUserService.save(baseUser);
            System.out.println("注册的信息已经保存到数据库");
            //像邮箱发送邮件
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MailQQUtils.sendMessage("545419768@qq.com","惠宜家商城","http://localhost:9090",baseUser.getLoginName());
                }
            });
            thread.start();
            System.out.println("激活邮件已发送");
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是手机还是邮箱
     * @param telEmail
     * @return
     */
    @RequestMapping("firstForget")
    public boolean firstForget(String telEmail){
        System.out.println(telEmail+"----------------------");
        // 如果是手机号就向手机号发送短信
        if(RanDomUtils.isPhone(telEmail)){
            //getCodeDao.sendCode(telEmail);
            System.out.println("向手机发送验证码");
        } else if(RanDomUtils.isEmail(telEmail)){
            /**
             * 如果不是手机号就向邮箱发送验证码,调用发送邮箱MailQQUtils的sendCode方法发送验证码
             */
            System.out.println("向邮箱发送验证码");
            String fourRandom = RanDomUtils.getFourRandom();
            redisTemplate.opsForValue().set("code",fourRandom);
            MailQQUtils.sendCode(telEmail,fourRandom,"http://localhost:9090");
        }
        return false;
    }
}
