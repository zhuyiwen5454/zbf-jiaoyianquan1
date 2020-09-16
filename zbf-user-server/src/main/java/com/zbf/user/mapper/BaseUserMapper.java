package com.zbf.user.mapper;

import com.zbf.user.entity.BaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhuyiwen
 * @since 2020-09-12
 */
@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {



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
