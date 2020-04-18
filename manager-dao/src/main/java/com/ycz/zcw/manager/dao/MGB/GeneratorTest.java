package com.ycz.zcw.manager.dao.MGB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 
 * @ClassName GeneratorTest
 * @Description TODO(���򹤳̵������ļ�)
 * @author Administrator
 * @Date 2020��4��18�� ����7:18:33
 * @version 1.0.0
 */
public class GeneratorTest {

    public static void main(String[] args) throws Exception {
        
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("���ɳɹ���");
    }

}
