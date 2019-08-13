package com.jk.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jk.dao.*;
import com.jk.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//import org.springframework.data.redis.core.RedisTemplate;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper um;
	@Autowired
	private TreeMapper pm;
	@Autowired
	private RoleMapper rm;
	@Autowired
	private RoleTreeMapper prm;
	@Autowired
	private UserRoleMapper rum;
	@Autowired
	private MongoTemplate mongoTemplate;

	//@Override	mysql判断用户名密码是否正确

	public List<Users> queryUser(Users users) {
		return um.queryUser(users);
	}
	
	//redis判断用户名密码是否正确
	/*@Override
	public int queryUser(Users users, HttpServletRequest request) {
		String key="user"+users.getUsername();
		if(redisTemplate.hasKey(key)){
			List<Users> list = redisTemplate.opsForList().range(key, 0, -1);
			if(list.get(0).getUserpwd().equals(users.getUserpwd())){
				request.getSession().setAttribute("users", list.get(0));
				return 1;
			}else{
				return 2;
			}
		}else{
			return 0;
		}
	}*/
	//五表联查查树
	@Override
	public List<Tree> getTreeAll(Integer userid) {
		return pm.getTreeAll(userid);
	}

	//差角色
	@Override
	public List queryrole(Role role, Integer page, Integer rows) {
		int papa=(page-1)*rows;
		return rm.queryrole(role,papa,rows);
	}
	@Override
	public Long queryrolecount(Role role) {
		return rm.queryrolecount(role);
	}

	@Override
	public List queryu(Users users, Integer page, Integer rows) {
		int papa=(page-1)*rows;
		return um.queryu(users,papa,rows);
	}

	@Override
	public Long queryucount(Users users) {
		return um.queryucount(users);
	}

	@Override
	public List<Users> querylist() {
		return um.querylist();
	}

	@Override
	public void addlist(List<Users> list) {
		um.addlist(list);
	}

	@Override
	public Map queryLogList(LogModel log, Integer page, Integer rows,Users users) {
		Map m=new HashMap();
		Criteria c=new Criteria();
		Query query = new Query();
		if(log != null && log.getLogname() != null && log.getLogname().length()>0){
			// java的 正则
			Pattern pattern = Pattern.compile("^.*"+log.getLogname()+".*$",Pattern.CASE_INSENSITIVE);
			c.and("logname").regex(pattern);
		}
		if(log != null && log.getEnddate() == null  && log.getStadate() != null){
			c.and("logtime").gte(log.getStadate());
		}
		if(log != null && log.getEnddate() != null  && log.getStadate() == null){
			c.and("logtime").lte(log.getEnddate());
		}
		if(log != null && log.getEnddate() != null  && log.getStadate() != null){
			c.and("logtime").lte(log.getEnddate()).gte(log.getStadate());
		}
		//说明不是超级管理员
		if(users != null && !users.getUsername().equals("张三")){
			c.and("userid").is(users.getUsername());
		}
		query.addCriteria(c);
		query.skip((page-1)*rows);//设置每页展示的开始条数
		query.limit(rows);        //设置每页展示的结束条数
		long count = mongoTemplate.count(query,   LogModel.class, "logdb");//总条数
		List<LogModel> list=mongoTemplate.find(query,  LogModel.class, "logdb");//分页展示的数据
		m.put("total", count);
		m.put("rows", list);
		return m;
	}

	//根据 角色id查询对应权限
	@Override
	public List<Tree> editpre(Integer roleid) {
		//查出  角色 id  所对应的 菜单 的id
		List<String> list =  prm.editpre(roleid);
		// 查询所有 的菜单 
		List<Tree>  listTwo = pm.editpreall();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < listTwo.size(); j++) {
				// 用  原来 选中的 菜单id  和所有的  菜单id  对比 ，对比成功 就选中
				if(list.get(i).equals(listTwo.get(j).getId().toString())){
					listTwo.get(j).setChecked("true");;
				}
			}
		}
		return listTwo;				
	}
	//编辑对应角色的对应权限
	@Override
	public int updateper(Integer[] perids, Integer roleid) {
		//先 删除 原有的 关联
		int i  = prm.deleteper(roleid);
		//再建立  新的 关联
		if(i >=0){
			for (int j = 0; j < perids.length; j++) {
				RoleTree rpm = new RoleTree();
				rpm.setTreeid(perids[j]);
				rpm.setRoleid(roleid);
				i = prm.insert(rpm);
			}
		}
		return i;		
	}
	//新增用户  mysql
	@Override
	public int adduser(Users users) {
		List<Users> list=um.queryyh(users);
		if(list!=null&&list.size()>0){
			return 6;//用户已存在
		}else{
			um.adduser(users);
			return 7;
		}
	}
	
	//新增用户  redis
	/*@Override
	public int adduser(Users users) {
		String key="user"+users.getUsername();
		if(redisTemplate.hasKey(key)){
			return 6;//用户已存在
		}else{
			um.adduser(users);
			redisTemplate.opsForList().leftPush(key, users);
			return 7;
		}
	}*/
	//新增角色
	@Override
	public void addrole(Role role) {
		rm.addrole(role);
	}
	//新增权限
	@Override
	public void addpre(Tree pre) {
		pm.addpre(pre);
	}
	//回显用户
	@Override
	public Users upduser(Integer userid) {
		return um.upduser(userid);
	}
	//修改用户
	@Override
	public void updateuser(Users user) {
		um.updateuser(user);
	}
	//回显角色
	@Override
	public Role updrole(Integer roleid) {
		return rm.updrole(roleid);
	}
	//修改角色
	@Override
	public void updaterole(Role role) {
		rm.updaterole(role);
	}
	//回显权限
	@Override
	public Tree updpre(Integer id) {
		return pm.updpre(id);
	}
	//修改权限
	@Override
	public void updatepre(Tree pre) {
		pm.updatepre(pre);
	}
	//删除用户
	@Override
	public void deleteuser(Integer userid) {
		um.deleteuser(userid);
	}
	//删除角色
	@Override
	public void deleterole(Integer roleid) {
		rm.deleterole(roleid);
	}
	//删除权限
	@Override
	public void deletepre(Integer id) {
		pm.deletepre(id);
	}
	
	//根据用户id查询对应角色
	@Override
	public List<Role> getrolebyuserid(Integer userid) {
		//查出  角色 id  所对应的 菜单 的id
				List<String> list =  rum.editrole(userid);
				// 查询所有 的菜单 
				List<Role>  listTwo = rm.editroleall();
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < listTwo.size(); j++) {
						// 用  原来 选中的 菜单id  和所有的  菜单id  对比 ，对比成功 就选中
						if(list.get(i).equals(listTwo.get(j).getRoleid().toString())){
							listTwo.get(j).setChecked("true");;
						}
					}
				}
				return listTwo;
	}
	//编辑对应用户的对应角色
	@Override
	public int updateUserRole(Integer[] roleIds, Integer uidTwo) {
		 //先 删除 该用户  原来 有的 角色
		int i = rum.deleteRoleById(uidTwo);
		//再新增 现在 要加的  角色
		if(i >=0){
			for (int j = 0; j < roleIds.length; j++) {
				UserRole urm = new  UserRole();
				urm.setRoleid(roleIds[j]);
				urm.setUserid(uidTwo);
				i = rum.insert(urm);
			}
		}
		return i;
	}

	@Override
	public List querypree(Tree pre, Integer page, Integer rows) {
		int papa=(page-1)*rows;
		return pm.querypree(pre,papa,rows);
	}

	@Override
	public Long querypreecount(Tree pre) {
		return pm.querypreecount(pre);
	}


}
