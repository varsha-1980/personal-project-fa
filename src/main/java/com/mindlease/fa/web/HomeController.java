package com.mindlease.fa.web;


import com.mindlease.fa.config.LocaleConfig;
import com.mindlease.fa.model.OrderDetails;
import com.mindlease.fa.model.User;
import com.mindlease.fa.service.OrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Locale;
import java.util.Optional;


/**          Home Controller has been created to set language based on user details             **/
@Controller
@Slf4j
public class HomeController {

    @Autowired
    OrderDetailsService service;

    @RequestMapping("/home")
    public ModelAndView displayHomePage(Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) {

        log.info("----------------Home Page ---------------------------");

        ModelAndView modelAndView = new ModelAndView();
        User user = null;
        Optional<User> userOps = service.findByEmail(principal.getName());

        log.info("{}------------id:::{}", principal.getName(), userOps.isPresent());

        if (userOps.isPresent()) {
            user = userOps.get();

            log.info(String.valueOf(user));
            log.info(String.valueOf(principal));

            // Setting language based on user details
            if (user.getLanguage() != null) {
                if (user.getLanguage().equalsIgnoreCase("de")) {
                    LocaleConfig localeConfig = new LocaleConfig();
                    localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.GERMAN);
                } else {
                    LocaleConfig localeConfig = new LocaleConfig();
                    localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.US);
                }
            }
        }
        modelAndView.setViewName("home");

        return modelAndView;

    }


}
