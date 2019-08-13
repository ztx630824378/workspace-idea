package com.jk.dao;

import com.jk.model.Tree;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TreeMapper {
	List<Tree> getTreeAll(Integer userid);
	@Select("SELECT * FROM y_tree")
	List<Tree> editpreall();
	@Insert("insert into y_tree(name,pid,text,url,state) values (#{name},#{pid},#{text},#{url},#{state})")
	void addpre(Tree pre);
	@Select("select * from y_tree where id = #{id} ")
	Tree updpre(Integer id);
	@Update("update y_tree set name=#{name},pid=#{pid},text=#{text},url=#{url},state=#{state}  where id=#{id}")
	void updatepre(Tree pre);
	@Delete("delete from y_tree where id=#{id}")
	void deletepre(Integer id);

    List querypree(@Param("p") Tree pre, @Param("papa")int papa, @Param("rows")Integer rows);
	Long querypreecount(@Param("p")  Tree pre);

}