package com.jk.dao;

import com.jk.model.RoleTree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleTreeMapper {
		@Delete("delete from y_role_tree where roleid = #{roleid}")
		int deleteper(Integer roleid);
	    @Select("SELECT treeid FROM  y_role_tree where roleid = #{roleid}")
		List<String> editpre(Integer roleid);
	    @Insert("insert into y_role_tree(treeid,roleid) values(#{treeid},#{roleid})")
		int insert(RoleTree rpm);
}