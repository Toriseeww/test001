package com.example.test001.Web;

import com.example.test001.Service.MImplService;
import com.example.test001.Service.MonImplService;
import com.example.test001.Service.PhaImplService;
import com.example.test001.Service.UImplService;
import com.example.test001.model.M;
import com.example.test001.model.Mon;
import com.example.test001.model.Pha;
import com.example.test001.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@RequestMapping(value = "/user")
@Controller
public class UserController {
    @Autowired
    private UImplService uImplService;

    @Autowired
    private MImplService mImplService;

    @Autowired
    private PhaImplService phaImplService;

    @Autowired
    private MonImplService monImplService;

    @RequestMapping(value = "/update")
    public Object update(Model model, String  id, String username, String password, String permissions, String faculties, Integer phone, String dormitorynumber) {
        User user = new User();
        M userM = new M();

        user.setSid(id);
        user.setUsername(username);
        user.setPassword(password);
        if (permissions.equals("配送员")) {
            user.setPermissions("ROLE_DELI");
        } else if (permissions.equals("用户")) {
            user.setPermissions("ROLE_USER");
        }

        if (!(user.getPermissions()).equals("配送员") || !(user.getPermissions()).equals("用户")) {
            model.addAttribute("updateU", 0);
            return "User/UserUpdate";
        } else {
            userM.setUid(id);
            userM.setFaculties(faculties);
            userM.setPhone(phone);
            userM.setDormitorynumber(dormitorynumber);


            int i = uImplService.updateByPrimaryKey(user);
            int j = mImplService.updateByPrimaryKey(userM);

            model.addAttribute("updateU", i);
            model.addAttribute("updateM", j);

            return "User/UserUpdate";
        }

    }

    @RequestMapping(value = "/Order")
    public Object Order(Model model,String onum,String pid,String pcol,String price,String fees) throws SQLException {
        List<Pha> i=phaImplService.getPha();
        Pha pha=new Pha();

        List<String> strings=new ArrayList<>();
        Random r = new Random();

        if (i.size() == 0){
            onum= r.nextInt(1000)+"";
        }
        else {
            int p=0,m=0;
            for (int j = 0; j < i.size(); j++) {
                strings.add(i.get(j).getOnum());
            }
            while (p!=1){
                onum= r.nextInt(1000)+"";
                for (int j = 0; j < strings.size(); j++) {
                    if ((strings.get(j)).equals(onum)){
                        break;
                    }
                    m=j;
                }
                if (m == strings.size()-1) {
                    p=1;
                }
            }
        }

        if (Float.parseFloat(fees)<1){
            model.addAttribute("order",0);

            return "User/Order";
        }
        else {
            pha.setOnum(onum);
            pha.setPcol(pcol);
            pha.setPrice(price);
            pha.setPid(pid);
            pha.setState(null);
            pha.setAccept(null);
            pha.setFees(fees);
            pha.setMstate("0");

            int b=phaImplService.insert(pha);
            model.addAttribute("order",b);

            return "User/Order";
        }

    }

    @RequestMapping(value = "/people")
    public Object people(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);

        return "/User/People";
    }

    @RequestMapping(value = "/lookNOrder")
    public Object lookOrder(Model model,Authentication authentication){
        String i=authentication.getName();
        List<Pha> x=phaImplService.getPcol(i);
        List<Pha> p=new ArrayList<>();

        for (int j = 0; j < x.size(); j++) {
            if ((x.get(j).getState())==null&&(x.get(j).getMstate())!=null&&(x.get(j).getMstate()).equals("1")){
                p.add(x.get(j));
            }
        }

        if (p.size() == 0){
            return "User/oderNull";
        }
        else {
            model.addAttribute("p",p);
            return "User/looKOrder";
        }
    }

    @RequestMapping(value = "/lookCOrder")
    public Object lookCOrder(Model model,Authentication authentication){
        String i=authentication.getName();
        List<Pha> x=phaImplService.getPcol(i);
        List<Pha> p=new ArrayList<>();

        for (int j = 0; j < x.size(); j++) {
            if ((x.get(j).getState())!=null&&(x.get(j).getState()).equals("0")){
                p.add(x.get(j));
            }
        }

        if (p.size() == 0){
            return "User/oderNull";
        }
        else {
            model.addAttribute("p",p);
            return "User/looKCOrder";
        }
    }

    @RequestMapping(value = "/deleteOrders")
    public Object deleteOrders(Model model,String onum){
        int i=phaImplService.deleteByPrimaryKey(onum);
        model.addAttribute("o",i);

        return "User/deleteOrder";
    }

    @RequestMapping(value = "/deleteNOrders")
    public Object deleteNOrders(Model model,String onum){
        Pha pha=phaImplService.getPid(onum);
        Mon mon=monImplService.getMon(pha.getPid());

        float a=Float.parseFloat(mon.getMoney())+Float.parseFloat(pha.getFees())+Float.parseFloat(pha.getPrice());
        mon.setMoney(String.valueOf(a));

        monImplService.updateByPrimaryKey(mon);
        int i=phaImplService.deleteByPrimaryKey(onum);
        model.addAttribute("o",i);

        return "User/deleteOrder";
    }

    @RequestMapping(value = "/updatepm")
    public Object updatep(Model model,String onum){
        model.addAttribute("o",onum);
        return "User/updateM";
    }


    @RequestMapping(value = "/updateP")
    public Object update(Model model,String onum,String pcol,String price){
        Pha pha=phaImplService.getPid(onum);
        pha.setPcol(pcol);
        pha.setPrice(price);

        int i=phaImplService.update(pha);
        model.addAttribute("o",i);

        return "User/updateP";
    }

    @RequestMapping(value = "/updateAcSt")
    public Object updateAcSt(Model model,String onum){
        Pha pha=phaImplService.getPid(onum);
        String q=pha.getAccept();
        Mon mon=monImplService.getMon(q);



        if (!(pha.getState()).equals("0")){
            model.addAttribute("o",0);
            return "User/updateAcSt";
        }
        else {
            float a=Float.parseFloat(pha.getFees())+Float.parseFloat(mon.getMoney())+Float.parseFloat(pha.getPrice());
            mon.setMoney(String.valueOf(a));

            pha.setState("1");
            int i=phaImplService.updateAcSt(pha);
            monImplService.updateByPrimaryKey(mon);

            model.addAttribute("o",i);
            return "User/updateAcSt";
        }

    }

    @RequestMapping(value = "/updateMessage")
    public Object updateMessage(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);
        return "User/UpdateMe";
    }

    @RequestMapping(value = "/lookPay")
    public Object lookPay(Model model,Authentication authentication){
        String i=authentication.getName();
        List<Pha> x=phaImplService.getPcol(i);
        List<Pha> p=new ArrayList<>();

        for (int j = 0; j < x.size(); j++) {
            if ((x.get(j).getMstate()).equals("0")){
                p.add(x.get(j));
            }
        }

        if (p.size() == 0){
            return "User/oderNull";
        }
        else {
            model.addAttribute("p",p);
            return "User/lookPay";
        }
    }

    @RequestMapping(value = "/pay")
    public Object pay(Model model,String onum) throws InterruptedException {
        Thread.sleep(1000);
        Pha pha=phaImplService.getPid(onum);
        String o=pha.getPid();
        Mon mon=monImplService.getMon(o);

        float p=Float.parseFloat(pha.getPrice())+Float.parseFloat(pha.getFees());

        if ((mon.getMoney())==null||(Float.parseFloat(mon.getMoney()))<p){
            model.addAttribute("o",0);
            return "User/payResult";
        }
        else {
            float m=Float.parseFloat(mon.getMoney())-p;
            mon.setMoney(String.valueOf(m));
            pha.setMstate("1");

            int i=phaImplService.updateMs(pha);
            monImplService.updateByPrimaryKey(mon);
            model.addAttribute("o",i);

            return "User/payResult";
        }
    }

    @RequestMapping(value = "/Money")
    public Object Money(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        Mon mon=monImplService.getMon(i);

        model.addAttribute("U",user);
        model.addAttribute("N",mon);
        return "User/Money";
    }

    @RequestMapping(value = "/recharge")
    public Object recharge(Model model,String money,String uid) throws InterruptedException {
        Thread.sleep(1000);
        Mon mon=monImplService.getMon(uid);

        if (!isInteger(money)||(Float.parseFloat(money)>=100)){
            model.addAttribute("o",0);
            return "User/rechargeResult";
        }
        else {
            float m=Float.parseFloat(mon.getMoney())+Float.parseFloat(money);
            mon.setMoney(String.valueOf(m));

            int i=monImplService.updateByPrimaryKey(mon);
            model.addAttribute("o",i);
            return "User/rechargeResult";
        }
    }

    @RequestMapping(value = "/rechargeM")
    public Object rechargeM(Model model,String uid){
            Mon mon=monImplService.getMon(uid);
            model.addAttribute("M",mon);
            return "User/rechargeM";
    }

    @RequestMapping(value = "/WithdrawalsM")
    public Object WithdrawalsM(Model model,String uid){
            Mon mon=monImplService.getMon(uid);
            model.addAttribute("M",mon);
            return "User/WithdrawalsM";
    }

    @RequestMapping(value = "/Withdrawals")
    public Object Withdrawals(Model model,String money,String uid) throws InterruptedException {
        Thread.sleep(1000);
        Mon mon=monImplService.getMon(uid);

        if ((Float.parseFloat(mon.getMoney())<Float.parseFloat(money))){
            model.addAttribute("o",0);
            return "User/Withdrawals";
        }
        else {
            float m=Float.parseFloat(mon.getMoney())-Float.parseFloat(money);
            mon.setMoney(String.valueOf(m));

            int i=monImplService.updateByPrimaryKey(mon);
            model.addAttribute("o",i);
            return "User/Withdrawals";
        }
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
