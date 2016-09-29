package com.yangyongli.phoenix.controller;

import com.yangyongli.phoenix.domain.User;
import com.yangyongli.phoenix.security.SecurityUtils;
import com.yangyongli.phoenix.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Created by yangyongli on 8/25/16.
 */
@Controller
public class LoginController {
    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Inject
    UserService userService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    HttpSession httpSession;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("j_username") String username,
                        @RequestParam("j_password") String password,
                        @RequestParam(value = "remember-me", required = false) String rememberMe) {
        log.debug("login with {}, {} , {}", username, password, rememberMe);

        boolean success = doLogin(username, password, "true".equals(rememberMe));
        if(success){
            return "redirect:/";
        }
        return "user/login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    @RequestMapping("/test")
    public String test() {
        SecurityContext securityContext = (SecurityContext)httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        if(null != securityContext){
            Authentication authentication = securityContext.getAuthentication();
            if(null != authentication){
                SecurityContext securityContext1 = SecurityContextHolder.getContext();
                securityContext1.setAuthentication(authentication);
            }
        }
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if(null == currentUserLogin || "anonymousUser".equals(currentUserLogin)){
            return "redirect:/login";
        }

        return "home/about";
    }


    public boolean doLogin(String username, String password, boolean rememberMe){
        try {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if(userDetails != null){
                String passwordFromDB = userDetails.getPassword();
                if(passwordEncoder.matches(password, passwordFromDB)){
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, userDetails, authorities);
                    securityContext.setAuthentication(token);
                    httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
