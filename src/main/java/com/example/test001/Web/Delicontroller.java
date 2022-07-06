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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/deli")
public class Delicontroller {

    @Autowired
    private MImplService mImplService;

    @Autowired
    private PhaImplService phaImplService;

    @Autowired
    private UImplService uImplService;

    @Autowired
    private MonImplService monImplService;

    @RequestMapping("/look")
    public Object select(Model model, String id){
        List<Pha> pha=phaImplService.getPcol(id);
        List<Pha> phas1=new ArrayList<>();

        for (int i = 0; i < pha.size(); i++) {
            if ((pha.get(i).getAccept())==null&&(pha.get(i).getMstate()).equals("1")){
                phas1.add(pha.get(i));
            }
        }

        if (phas1.size() == 0){
            return "Deli/deliError";
        }
        else {
            M j = mImplService.selectByPrimaryKey(id);
            model.addAttribute("selectep", phas1);
            model.addAttribute("selectem", j);
            return "Deli/look";
        }
    }

    @RequestMapping(value = "/update")
    public Object update(Model model, String  id, String username, String password, String permissions, String faculties, Integer phone, String dormitorynumber) {
        User user = new User();
        M userM = new M();

        user.setSid(id);
        user.setUsername(username);
        user.setPassword(password);
        if (permissions.equals("配送员"))
        {
            user.setPermissions("ROLE_DELI");
        }else {
            user.setPermissions("ROLE_USER");
        }

        if (!(user.getPermissions()).equals("配送员") || !(user.getPermissions()).equals("用户")) {
            model.addAttribute("updateU", 0);
            return "Deli/UserUpdate";
        } else {
            userM.setUid(id);
            userM.setFaculties(faculties);
            userM.setPhone(phone);
            userM.setDormitorynumber(dormitorynumber);

            int i = uImplService.updateByPrimaryKey(user);
            int j = mImplService.updateByPrimaryKey(userM);

            model.addAttribute("updateU", i);
            model.addAttribute("updateM", j);

            return "Deli/UserUpdate";
        }
    }


    @RequestMapping(value = "/updateMessage")
    public Object updateMessage(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);
        return "Deli/UpdateMe";
    }

    @RequestMapping(value = "/people")
    public Object people(Model model, Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        M m=mImplService.getMid(i);

        model.addAttribute("U",user);
        model.addAttribute("M",m);

        return "Deli/People";
    }

    @RequestMapping(value = "/lookAll")
    public Object lookAll(Model model){
        List<Pha> phas=phaImplService.getPha();
        List<Pha> phas1=new ArrayList<>();

        for (int i = 0; i < phas.size(); i++) {
            if ( phas.get(i).getAccept()== null&&(phas.get(i).getMstate()).equals("1")){
                phas1.add(phas.get(i));
            }
        }

        if (phas1.size() == 0){
            return "Deli/AllError";
        }
        else {
            model.addAttribute("M",phas1);
            return "Deli/lookAll";
        }
    }

    @RequestMapping(value = "/lookL")
    public Object lookL(Model model,Authentication authentication){
        String o=authentication.getName();
        List<Pha> phas=phaImplService.getPha();
        List<Pha> phas1=new ArrayList<>();

        for (int i = 0; i < phas.size(); i++) {
            if ((phas.get(i).getAccept())!=null&&!(phas.get(i).getState()).equals("1")&&(phas.get(i).getMstate()).equals("1")&&(phas.get(i).getAccept()).equals(o)){
                phas1.add(phas.get(i));
            }
        }

        if (phas1.size() == 0){
            return "Deli/AllError";
        }
        else {
            model.addAttribute("M",phas1);
            return "Deli/lookL";
        }
    }

    @RequestMapping(value = "/lookAllM")
    public Object lookAllM(Model model,String pid){
        M p=mImplService.getMid(pid);
        User user=uImplService.getAdmin(pid);
        model.addAttribute("P",p);
        model.addAttribute("U",user);

        return "Deli/lookAllM";
    }

    @RequestMapping(value = "/updateAcSt")
    public Object updateAcSt(Model model,String onum,Authentication authentication){
        String i=authentication.getName();
        Pha pha=phaImplService.getPid(onum);
        pha.setAccept(i);
        pha.setState("0");
        int j=phaImplService.updateAcSt(pha);

        model.addAttribute("o",j);
        return "Deli/updateAcSt";
    }

    @RequestMapping(value = "/updateCAcSt")
    public Object updateCAcSt(Model model,String onum){
        Pha pha=phaImplService.getPid(onum);
        pha.setAccept(null);
        pha.setState(null);
        int j=phaImplService.updateAcSt(pha);

        model.addAttribute("o",j);
        return "Deli/updateCAcSt";
    }

    @RequestMapping(value = "/Money")
    public Object Money(Model model,Authentication authentication){
        String i=authentication.getName();
        User user=uImplService.getAdmin(i);
        Mon mon=monImplService.getMon(i);

        model.addAttribute("U",user);
        model.addAttribute("N",mon);
        return "Deli/Money";
    }

    @RequestMapping(value = "/rechargeM")
    public Object rechargeM(Model model,String uid){
        Mon mon=monImplService.getMon(uid);
        model.addAttribute("M",mon);
        return "Deli/rechargeM";
    }

    @RequestMapping(value = "/recharge")
    public Object recharge(Model model,String money,String uid) throws InterruptedException {
        Thread.sleep(1000);
        Mon mon=monImplService.getMon(uid);

        if (!isInteger(money)||(Float.parseFloat(money)>=100)){
            model.addAttribute("o",0);
            return "Deli/rechargeResult";
        }
        else {
            float m=Float.parseFloat(mon.getMoney())+Float.parseFloat(money);
            mon.setMoney(String.valueOf(m));

            int i=monImplService.updateByPrimaryKey(mon);
            model.addAttribute("o",i);
            return "Deli/rechargeResult";
        }
    }

    @RequestMapping(value = "/WithdrawalsM")
    public Object WithdrawalsM(Model model,String uid){
        Mon mon=monImplService.getMon(uid);
        model.addAttribute("M",mon);
        return "Deli/WithdrawalsM";
    }

    @RequestMapping(value = "/Withdrawals")
    public Object Withdrawals(Model model,String money,String uid) throws InterruptedException {
        Thread.sleep(1000);
        Mon mon=monImplService.getMon(uid);

        if ((Float.parseFloat(mon.getMoney())<Float.parseFloat(money))){
            model.addAttribute("o",0);
            return "Deli/Withdrawals";
        }
        else {
            float m=Float.parseFloat(mon.getMoney())-Float.parseFloat(money);
            mon.setMoney(String.valueOf(m));

            int i=monImplService.updateByPrimaryKey(mon);
            model.addAttribute("o",i);
            return "Deli/Withdrawals";
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
