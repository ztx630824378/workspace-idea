package com.jk.dao;

import com.jk.model.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleMapper {
	@Select("SELECT roleid FROM  y_user_role where userid = #{userid}")
	List<String> editrole(Integer userid);
    @Delete("delete from y_user_role where userid = #{userid}")
	int deleteRoleById(Integer uidTwo);
	@Insert("insert into y_user_role(roleid,userid) values(#{roleid},#{userid})")
	int insert(UserRole urm);
}