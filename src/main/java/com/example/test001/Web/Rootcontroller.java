package com.example.test001.Web;

import com.example.test001.Service.MImplService;
import com.example.test001.Service.PhaImplService;
import com.example.test001.Service.UImplService;
import com.example.test001.model.M;
import com.example.test001.model.Pha;
import com.example.test001.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/root")
@Controller
public class Rootcontroller {
    @Autowired
    private UImplService uImplService;

    @Autowired
    private MImplService mImplService;

    @Autowired
    private PhaImplService phaImplService;

    @RequestMapping(value = "/delete")
    public Object delete(Model model,String id){
        User user=uImplService.getAdmin(id);
        if (user == null){
            model.addAttribute("deleteu",0);
        }
        else if ((uImplService.getAdmin(id).getPermissions()).equals("ROLE_ROOT")){
            model.addAttribute("deleteu",0);
        }
        else {
            int i = uImplService.deleteByPrimaryKey(id);
            mImplService.deleteByPrimaryKey(id);
            phaImplService.deleteByPrimaryKey(id);
            model.addAttribute("deleteu",i);
        }

        return "Root/UserDelete";
    }

    @RequestMapping(value = "/select")
    public Object select(Model model,String id){
        User user=uImplService.getAdmin(id);
        if (user == null){
            return "Root/rootError";
        }
        else {
            User i = uImplService.selectByPrimaryKey(id);
            M j = mImplService.selectByPrimaryKey(id);

            model.addAttribute("selecteu",i);
            model.addAttribute("selectem",j);
            return "Root/UserSelect";
        }
    }

    @RequestMapping(value = "/meAll")
    public Object meAll(Model model,String id){
        M ms=mImplService.getMid(id);

        model.addAttribute("M",ms);
        return "Root/meAll";
    }

    @RequestMapping(value = "/coAll")
    public Object coAll(Model model){
        List<User> users=uImplService.UserAll();
        List<User> u=new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (!(users.get(i).getPermissions()).equals("ROLE_ROOT")){
                u.add(users.get(i));
            }
        }
        model.addAttribute("User",u);
        return "Root/coAll";
    }

    @RequestMapping(value = "/deleteOrders")
    public Object deleteOrders(Model model,String onum){
        List<Pha> pha=phaImplService.getPcol(onum);

        int i=phaImplService.deleteByPrimaryKey(onum);
        model.addAttribute("o",i);

        return "Root/deleteOrder";
    }

    @RequestMapping(value = "/people")
    public Object people(Model model, Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);

        return "/Root/People";
    }

    @RequestMapping(value = "/update")
    public Object update(Model model, String  id, String username, String password, String faculties, Integer phone, String dormitorynumber) {
        User user = new User();
        M userM = new M();

        user.setSid(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setPermissions("ROLE_ROOT");

        userM.setUid(id);
        userM.setFaculties(faculties);
        userM.setPhone(phone);
        userM.setDormitorynumber(dormitorynumber);

        int i = uImplService.updateByPrimaryKey(user);
        int j = mImplService.updateByPrimaryKey(userM);

        model.addAttribute("updateU",i);
        model.addAttribute("updateM",j);

        return "User/UserUpdate";
    }

    @RequestMapping(value = "/updateMessage")
    public Object updateMessage(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);
        return "Root/UpdateMe";
    }
}
