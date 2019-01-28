package org.teomant.anotherlearningproject.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.teomant.anotherlearningproject.game.Server;
import org.teomant.anotherlearningproject.services.FighterService;
import org.teomant.anotherlearningproject.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TestIntercepter implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Autowired
    FighterService fighterService;

    @Autowired
    Server server;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(request.getRequestURI());

        if (request.getUserPrincipal() != null
                && userService.findUserByUsername(request.getUserPrincipal().getName()).getFighterEntity() == null
                && !request.getRequestURI().contains("/createFigter")) {
            response.sendRedirect(request.getContextPath() + "/createFigter");
            return false;
        }

        if (request.getUserPrincipal() != null
                && userService.findUserByUsername(request.getUserPrincipal().getName()).getFighterEntity() != null
                && request.getRequestURI().contains("/createFigter")) {
            response.sendRedirect(request.getContextPath() + "/userInfo");
            return false;
        }

        if (request.getUserPrincipal() != null
                && server.inFight(userService
                .findUserByUsername(request.getUserPrincipal().getName()).getFighterEntity())
                && !( request.getRequestURI().contains("/currentFight")
                || request.getRequestURI().contains("/api"))) {
            response.sendRedirect(request.getContextPath() + "/currentFight");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
