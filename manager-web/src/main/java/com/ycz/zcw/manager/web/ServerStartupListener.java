package com.ycz.zcw.manager.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @ClassName ServerStartupListener
 * @Description TODO(�����Ϊǰ�˵�·��������)
 * @author Administrator
 * @Date 2020��4��19�� ����4:03:30
 * @version 1.0.0
 */
public class ServerStartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
        ServletContext context = arg0.getServletContext();//��ȡContext�����Ķ���
        String path = context.getContextPath();//��ȡӦ��·��
        context.setAttribute("APP_PATH", path);//����·��

    }

}
