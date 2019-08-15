package com.jk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jk.model.LogModel;
import com.jk.model.Role;
import com.jk.model.Tree;
import com.jk.model.Users;
import com.jk.service.UserService;
import com.jk.util.ExportExcel;
import com.jk.util.TreeNoteUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("tologin")
    public String  tologin(){
        return "login";
    }
    @RequestMapping("tozc")
    public String  tozc(){
        return "zc";
    }
    @RequestMapping("toindex")
    public String  toindex(){
        return "index";
    }
    @RequestMapping("toshowuser")
    public String  toshowuser(){
        return "showuser";
    }
    @RequestMapping("toshowrole")
    public String  toshowrole(){
        return "showrole";
    }
    @RequestMapping("toshowtree")
    public String  toshowtree(){
        return "showtree";
    }
    @RequestMapping("toadduser")
    public String  toadduser(){
        return "adduser";
    }
    @RequestMapping("toaddrole")
    public String  toaddrole(){
        return "addrole";
    }
    @RequestMapping("toaddtree")
    public String  toaddtree(){
        return "addtree";
    }
    @RequestMapping("toshowlog")
    public String  toshowlog(){
        return "showlog";
    }

    @Reference
    private UserService us;

    @Autowired
    private RedisTemplate redisTemplate;

    //查询用户名密码是否正确
    @RequestMapping("queryUser")
    @ResponseBody
    public int queryUser(Users users, HttpServletRequest request){
        List<Users> list=us.queryUser(users);
        System.out.println("111111111111");
        if(list==null||list.size()==0){
            return 0;
        }else{
            request.getSession().setAttribute("users", list.get(0));
            return 1;
        }
    }
    //注销
    @RequestMapping("zx")
    @ResponseBody
    public void zx(HttpServletRequest request){
        request.removeAttribute("users");
    }
    //五表联查	查权限
    @RequestMapping("getTreeAll")
    @ResponseBody
    public List<Tree> getTreeAll(HttpServletRequest request){
        List<Tree> list = new ArrayList<>();
        Users user = (Users) request.getSession().getAttribute("users");
        //1、定义缓存key
        String key="tree"+user.getUserid();
        if(redisTemplate.hasKey(key)){
            System.out.println("=====走缓存=======");
            //3、a 存在 ：从缓存中获取，返回list
            list = (List<Tree>) redisTemplate.opsForValue().get(key);
        }else{
            System.out.println("=====走数据库=======");
            //3、 b 不存在：
            //先从数据库查询、
            list = us.getTreeAll(user.getUserid());
            list = TreeNoteUtil.getFatherNode(list);
            //放入缓存中、返回list
            redisTemplate.opsForValue().set(key, list);
            //设置过期时间
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
        }
        return list;
    }
    //查用户
    @RequestMapping("queryu")
    @ResponseBody
    public Map queryu(Users users, Integer page, Integer rows){
        List list=us.queryu(users,page,rows);
        Long l=us.queryucount(users);
        Map map=new HashMap();
        map.put("rows", list);
        map.put("total", l);
        return map;
    }
    //查角色
    @RequestMapping("queryrole")
    @ResponseBody
    public   Map  queryrole(Role role,Integer page, Integer rows){
        List list=us.queryrole(role,page,rows);
        Long l=us.queryrolecount(role);
        Map map=new HashMap();
        map.put("rows", list);
        map.put("total", l);
        return map;
    }

    //查权限
    @RequestMapping("querypree")
    @ResponseBody
    public   Map  querypree(Tree pre,Integer page, Integer rows){
        List list=us.querypree(pre,page,rows);
        Long l=us.querypreecount(pre);
        Map map=new HashMap();
        map.put("rows", list);
        map.put("total", l);
        return map;
    }

    //根据 角色id查询对应权限
    @RequestMapping("editpre")
    @ResponseBody
    public  List<Tree>  editpre(Integer roleid){
        List<Tree> list = us.editpre(roleid);
        //自己调用自己（递归）
        list = TreeNoteUtil.getFatherNode(list);
        return list;
    }
    //根据 用户id查询对应角色
    @RequestMapping("getrolebyuserid")
    @ResponseBody
    public  List<Role>  getrolebyuserid(Integer userid){
        List<Role> list = us.getrolebyuserid(userid);
        return list;
    }
    //编辑权限
    @RequestMapping("updateper")
    @ResponseBody
    public  String updateper(Integer[] perids,Integer roleid){
        int i = us.updateper(perids,roleid);
        if(i<1){
            return "0";
        }
        return "1";
    }
    //编辑角色
    @RequestMapping("updateUserRole")
    @ResponseBody
    public  int  updateUserRole(Integer[]  roleIds, Integer uidTwo){
        int i =  us.updateUserRole(roleIds,uidTwo);
        return i;
    }
    //新增用户、注册
    @RequestMapping("adduser")
    @ResponseBody
    public int adduser(Users users){
        return us.adduser(users);
    }
    //新增角色
    @RequestMapping("addrole")
    @ResponseBody
    public String addrole(Role role){
        us.addrole(role);
        return "666";
    }
    //新增权限
    @RequestMapping("addpre")
    @ResponseBody
    public String addpre(Tree pre){
        us.addpre(pre);
        return "666";
    }
    //回显用户
    @RequestMapping("upduser")
    public String upduser(Model model, Integer userid){
        Users user=us.upduser(userid);
        model.addAttribute("user", user);
        return "upduser";
    }
    //修改用户
    @RequestMapping("updateuser")
    @ResponseBody
    public String updateuser(Users user){
        us.updateuser(user);
        return "666";
    }
    //回显角色
    @RequestMapping("updrole")
    public String updrole(Model model,Integer roleid){
        Role role=us.updrole(roleid);
        model.addAttribute("role", role);
        return "updrole";
    }
    //修改角色
    @RequestMapping("updaterole")
    @ResponseBody
    public String updaterole(Role role){
        us.updaterole(role);
        return "666";
    }
    //回显权限
    @RequestMapping("updpre")
    public String updpre(Model model,Integer id){
        Tree pre=us.updpre(id);
        model.addAttribute("per", pre);
        return "updtree";
    }
    //修改权限
    @RequestMapping("updatepre")
    @ResponseBody
    public String updatepre(Tree pre){
        us.updatepre(pre);
        return "666";
    }
    //删除用户
    @RequestMapping("deleteuser")
    @ResponseBody
    public String deleteuser(Integer userid){
        us.deleteuser(userid);
        return "666";
    }
    //删除角色
    @RequestMapping("deleterole")
    @ResponseBody
    public String deleterole(Integer roleid){
        us.deleterole(roleid);
        return "666";
    }
    //删除权限
    @RequestMapping("deletepre")
    @ResponseBody
    public String deletepre(Integer id){
        us.deletepre(id);
        return "666";
    }

    @RequestMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response) {
        //导出的excel的标题
        String title = "用户列表";
        //导出excel的列名
        String[] rowName = {"用户编号", "用户名称", "用户密码"};
        //导出的excel数据
        List<Object[]> dataList = new ArrayList<Object[]>();
        //查询的数据库的书籍信息
        List<Users> list = us.querylist();
        //循环书籍信息
        for (Users users : list) {
            Object[] obj = new Object[rowName.length];
            obj[0] = users.getUserid();
            obj[1] = users.getUsername();
            obj[2] = users.getUserpwd();
            dataList.add(obj);
        }
        ExportExcel exportExcel = new ExportExcel(title, rowName, dataList, response);
        try {
            exportExcel.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("importExcel")
    @ResponseBody
    public void importExcel(MultipartFile file, HttpServletResponse response){
        //获得上传文件上传的类型
        String contentType = file.getContentType();
        //上传文件的名称
        String fileName = file.getOriginalFilename();
        //获得文件的后缀名
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件的新的路径
        //生成新的文件名称
        String filePath = "./src/main/resources/templates/fileupload/";
        //创建list集合接收excel中读取的数据
        List<Users> list =new ArrayList<>();
        try {
            uploadFile(file.getBytes(), filePath, fileName);
            //通过文件忽的WorkBook
            FileInputStream fileInputStream = new FileInputStream(filePath+fileName);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            //通过workbook对象获得sheet页 有可能不止一个sheet
            for(int i=0 ;i<workbook.getNumberOfSheets();i++){
                //获得里面的每一个sheet对象
                Sheet sheetAt = workbook.getSheetAt(i);
                //通过sheet对象获得每一行
                for(int j=3;j<sheetAt.getPhysicalNumberOfRows();j++){
                    //创建一个book对象接收excel的数据
                    Users users=new Users();
                    //获得每一行的数据
                    Row row = sheetAt.getRow(j);
                    //获得每一个单元格的数据
                    if(row.getCell(1)!=null && !"".equals(row.getCell(1))){
                        users.setUsername(this.getCellValue(row.getCell(1)));
                    }
                    if(row.getCell(2)!=null && !"".equals(row.getCell(2))){
                        users.setUserpwd(this.getCellValue(row.getCell(2)));
                    }
                    list.add(users);
                }
            }
            us.addlist(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //上传文件的方法
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(Cell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }
    //判断从Excel文件中解析出来数据的格式
    private static String getCellValue(XSSFCell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case HSSFCell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }

    @RequestMapping("queryLogList")
    @ResponseBody
    public  Map queryLogList(LogModel log, Integer page, Integer rows,HttpServletRequest request) {
        Users users = (Users) request.getSession().getAttribute("users");
        return us.queryLogList(log, page, rows,users);
    }
}
