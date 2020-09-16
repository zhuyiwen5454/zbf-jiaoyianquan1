package com.zbf.user.service;

import com.zbf.user.entity.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuyiwen
 * @since 2020-09-12
 */
public interface IBaseUserService extends IService<BaseUser> {

    /**
     *
     * @param loginName
     * @return
     * 通过用户名查询
     */
    @Select("select * from base_user where loginName = #{loginName}")
    public BaseUser getBaseUserByLoginName(String loginName);

    /**
     * @param email
     * @return
     * 通过邮箱查询
     */
    @Select("select * from base_user where email = #{email}")
    public BaseUser getBaseUserByEmail(String email);

    /**
     * @param phone
     * @return
     * 通过手机号查询
     */
    @Select("select * from base_user where tel = #{tel}")
    public BaseUser getBaseUserByTel(String phone);

    /**
     * @param userName
     * @return
     * 通过用户名号查询
     */
    @Select("select * from base_user where userName = #{userName}")
    public BaseUser getBaseUserByUserName(String userName);
}
