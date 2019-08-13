package com.jk.dao;

import com.jk.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UsersMapper {
	@Select("select * from y_user where username = #{user.username} and  userpwd= #{user.userpwd} ")
	List<Users> queryUser(@Param("user") Users users);
	@Insert("insert into y_user(username,userpwd) values(#{username},#{userpwd})")
	void adduser(Users users);
	@Select("select * from y_user where userid = #{userid} ")
	Users upduser(Integer userid);
	@Update("update y_user set username=#{username},userpwd=#{userpwd} where userid=#{userid}")
	void updateuser(Users user);
	@Delete("delete from y_user where userid=#{userid}")
	void deleteuser(Integer userid);
	@Select("select * from y_user where username=#{username}")
	List<Users> queryyh(Users users);

	List queryu(@Param("u") Users users, @Param("papa")int papa, @Param("rows")Integer rows);
	Long queryucount(@Param("u")Users users);
	@Select("select * from y_user ")
	List<Users> querylist();

	void addlist(List<Users> list);
}