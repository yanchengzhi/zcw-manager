package com.ycz.zcw.manager.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @ClassName ServerStartupListener
 * @Description TODO(这个作为前端的路径监听器)
 * @author Administrator
 * @Date 2020年4月19日 下午4:03:30
 * @version 1.0.0
 */
public class ServerStartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
        ServletContext context = arg0.getServletContext();//获取Context上下文对象
        String path = context.getContextPath();//获取应用路径
        context.setAttribute("APP_PATH", path);//设置路径

    }

}
