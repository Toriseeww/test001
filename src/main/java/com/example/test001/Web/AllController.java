package com.example.test001.Web;

import com.example.test001.Service.MImplService;
import com.example.test001.Service.MonImplService;
import com.example.test001.Service.PhaImplService;
import com.example.test001.Service.UImplService;
import com.example.test001.mapper.MonMapper;
import com.example.test001.model.M;
import com.example.test001.model.Mon;
import com.example.test001.model.Pha;
import com.example.test001.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AllController {
    @Autowired
    private UImplService uImplService;

    @Autowired
    private MImplService mImplService;

    @Autowired
    private PhaImplService phaImplService;

    @Autowired
    private MonImplService monImplService;

    @RequestMapping(value = "/insertMessage")
    public Object insertMessage(){
        return "InsertMe";
    }

    @RequestMapping(value = "/selectMessage")
    public Object selectMessage(){
        return "Root/SelectMe";
    }

    @RequestMapping(value = "/All")
    public String manage(Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);

        String role = user.getPermissions();      // 通过登录用户的 id 得到用户的角色
        // 如果是用户登录，返回用户界面
        if (role.equals("ROLE_USER")){
            return "UserA";
        }
        else if (role.equals("ROLE_ROOT")){
            return "Root";
        }
        else if (role.equals("ROLE_DELI")){
            return "Deli";
        }
        return "/All";
    }

    @RequestMapping(value = "/USERAll")
    public Object USERAll(){
        return "UserA";
    }

    @RequestMapping(value = "/ROOTAll")
    public Object ROOTAll(){
        return "Root";
    }

    @RequestMapping(value = "/DeliAll")
    public Object DeliAll(){
        return "Deli";
    }

    @RequestMapping(value = "/LookMe")
    public Object LookMe(){
        return "Deli/lookMe";
    }

    @RequestMapping(value = "/deleteOrdersMe")
    public Object deleteOrdersMe(Model model){
        List<Pha> phas=phaImplService.getPha();
        List<Pha> phas1=new ArrayList<>();

            for (int i = 0; i < phas.size(); i++) {
            if ((phas.get(i).getState())!=null&&(phas.get(i).getState()).equals("1")){
                    phas1.add(phas.get(i));
                }
            }

            if (phas1.size()==0){
                return "Root/oError";
            }else
            {
                model.addAttribute("M",phas1);
                return "Root/deleteOM";
            }
    }

    @RequestMapping(value = "/OrdersMe")
    public Object OrdersMe(Model model,Authentication authentication){
        String i=authentication.getName();
        model.addAttribute("i",i);

        return "User/OrderMe";
    }

    @RequestMapping(value = "/login")
    public Object login(){
        return "login";
    }

    @RequestMapping(value = "/loginFail")
    public Object loginFail(){
        return "loginFail";
    }

    @RequestMapping(value = "/insert")
    public Object insert(Model model, String  id, String username, String password, String permissions, String faculties, Integer phone, String dormitorynumber) throws DataAccessException{
        User user = new User();
        M userM = new M();
        Mon mon=new Mon();

        user.setSid(id);
        user.setUsername(username);
        user.setPassword(password);
        if (permissions.equals("配送员"))
        {
            user.setPermissions("ROLE_DELI");
        }else {
            user.setPermissions("ROLE_USER");
        }

        userM.setUid(id);
        userM.setFaculties(faculties);
        userM.setPhone(phone);
        userM.setDormitorynumber(dormitorynumber);

        mon.setUid(id);
        mon.setMoney("0");

        if (permissions.equals("配送员") || permissions.equals("用户")) {
            try{
                int i = uImplService.insert(user);
                int j=  mImplService.insert(userM);
                int k=  monImplService.insert(mon);

                model.addAttribute("InsertU",i);
            }catch (DataAccessException | SQLException e){
                model.addAttribute("InsertU",0);
            }
            return "UserInsert";
        }else {
            model.addAttribute("InsertU", 0);
            return "UserInsert";
        }
        }
}
