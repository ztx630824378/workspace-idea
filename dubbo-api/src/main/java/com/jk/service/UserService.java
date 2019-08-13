package com.jk.service;

import com.jk.model.LogModel;
import com.jk.model.Role;
import com.jk.model.Tree;
import com.jk.model.Users;

import java.util.List;
import java.util.Map;

public interface UserService {

	List<Users> queryUser(Users users);

	List<Tree> getTreeAll(Integer userid);

	List<Tree> editpre(Integer roleid);

	int updateper(Integer[] perids, Integer roleid);

	int adduser(Users users);

	void addrole(Role role);

	void addpre(Tree pre);

	Users upduser(Integer userid);

	void updateuser(Users user);

	Role updrole(Integer roleid);

	void updaterole(Role role);

	Tree updpre(Integer id);

	void updatepre(Tree pre);

	void deleteuser(Integer userid);

	void deleterole(Integer roleid);

	void deletepre(Integer id);

	List<Role> getrolebyuserid(Integer userid);

	int updateUserRole(Integer[] roleIds, Integer uidTwo);

	List querypree(Tree pre, Integer page, Integer rows);
	Long querypreecount(Tree pre);

	List queryrole(Role role, Integer page, Integer rows);
	Long queryrolecount(Role role);

	List queryu(Users users, Integer page, Integer rows);
	Long queryucount(Users users);

    List<Users> querylist();
	void addlist(List<Users> list);

	Map queryLogList(LogModel log, Integer page, Integer rows,Users users);
}
