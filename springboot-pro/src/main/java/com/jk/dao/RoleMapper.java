package com.jk.dao;

import com.jk.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {
	
	@Insert("insert into y_role(rolename,text) values(#{rolename},#{text})")
	void addrole(Role role);
	@Select("select * from y_role where roleid = #{roleid} ")
	Role updrole(Integer roleid);
	@Update("update y_role set rolename=#{rolename},text=#{text} where roleid=#{roleid}")
	void updaterole(Role role);
	@Delete("delete from y_role where roleid=#{roleid}")
	void deleterole(Integer roleid);
	@Select("SELECT * FROM y_role")
	List<Role> editroleall();

	List queryrole(@Param("r") Role role, @Param("papa")int papa, @Param("rows")Integer rows);

	Long queryrolecount(@Param("r") Role role);
}