package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.AdminUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


/***
 * 用户数据借口层
 * @author cw
 * @date 2023.9.18
 */
@Mapper
public interface AdminUserMapper {

    @Select({
            "select",
            "admin_user_id, login_user_name, login_password, nick_name, locked",
            "from tb_admin_user",
            "where login_user_name = #{userName,jdbcType=VARCHAR} AND login_password=#{password,jdbcType=VARCHAR} AND locked = 0"
    })
    @Results(id = "BaseResultMap", value = {
            @Result(column = "admin_user_id", property = "adminUserId", id = true),
            @Result(column = "login_user_name", property = "loginUserName"),
            @Result(column = "login_password", property = "loginPassword"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "locked", property = "locked")
    })
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    @Select({
            "select",
            "admin_user_id, login_user_name, login_password, nick_name, locked",
            "from tb_admin_user",
            "where admin_user_id = #{adminUserId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    AdminUser selectByPrimaryKey(Integer adminUserId);

    // 插入和更新的方法可以继续添加...
    @Insert({
            "insert into tb_admin_user (admin_user_id, login_user_name, login_password, ",
            "nick_name, locked)",
            "values (#{adminUserId,jdbcType=INTEGER}, #{loginUserName,jdbcType=VARCHAR}, #{loginPassword,jdbcType=VARCHAR},",
            "#{nickName,jdbcType=VARCHAR}, #{locked,jdbcType=TINYINT})"
    })
    int insert(AdminUser record);

    @InsertProvider(type = AdminUserSqlProvider.class, method = "insertSelective")
    int insertSelective(AdminUser record);

    @UpdateProvider(type = AdminUserSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AdminUser record);

    @Update({
            "update tb_admin_user",
            "set login_user_name = #{loginUserName,jdbcType=VARCHAR},",
            "login_password = #{loginPassword,jdbcType=VARCHAR},",
            "nick_name = #{nickName,jdbcType=VARCHAR},",
            "locked = #{locked,jdbcType=TINYINT}",
            "where admin_user_id = #{adminUserId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AdminUser record);

}

// 需要定义一个SqlProvider来处理动态SQL
class AdminUserSqlProvider {

    public String insertSelective(AdminUser record) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into tb_admin_user (");
        if (record.getAdminUserId() != null) {
            sql.append("admin_user_id, ");
        }
        if (record.getLoginUserName() != null) {
            sql.append("login_user_name, ");
        }
        // ...继续其他字段...
        sql.setLength(sql.length() - 2); // 去除最后的逗号
        sql.append(") values (");
        if (record.getAdminUserId() != null) {
            sql.append("#{adminUserId,jdbcType=INTEGER}, ");
        }
        if (record.getLoginUserName() != null) {
            sql.append("#{loginUserName,jdbcType=VARCHAR}, ");
        }
        // ...继续其他字段的值...
        sql.setLength(sql.length() - 2); // 去除最后的逗号
        sql.append(")");
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AdminUser record) {
        StringBuilder sql = new StringBuilder();
        sql.append("update tb_admin_user set ");
        if (record.getLoginUserName() != null) {
            sql.append("login_user_name = #{loginUserName,jdbcType=VARCHAR}, ");
        }
        // ...继续其他字段...
        sql.setLength(sql.length() - 2); // 去除最后的逗号
        sql.append(" where admin_user_id = #{adminUserId,jdbcType=INTEGER}");
        return sql.toString();
    }
}
