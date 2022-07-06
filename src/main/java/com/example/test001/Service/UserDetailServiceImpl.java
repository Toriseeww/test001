package com.example.test001.Service;

import com.example.test001.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UImplService uImplService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String sid) throws UsernameNotFoundException {
        User Admin=uImplService.getAdmin(sid);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (Admin == null){
            return null;
        }
        else if((Admin.getPermissions()).equals("ROLE_ROOT")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ROOT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_DELI"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        else if((Admin.getPermissions()).equals("ROLE_DELI")){
            authorities.add(new SimpleGrantedAuthority("ROLE_DELI"));
        }
        else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        org.springframework.security.core.userdetails.User user =new org.springframework.security.core.userdetails.User(Admin.getSid(),
                passwordEncoder.encode(Admin.getPassword()),
                authorities);
        System.out.println(user.getAuthorities()+"   "+user.getUsername()+"  "+passwordEncoder.encode(user.getPassword())+"  ");
        return user;
    }
}
