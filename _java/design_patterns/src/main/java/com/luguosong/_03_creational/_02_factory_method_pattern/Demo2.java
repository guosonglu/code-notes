package com.luguosong._03_creational._02_factory_method_pattern;

import com.luguosong.util.XMLUtil;

/**
 * 通过反射和配置文件，将客户端改进为满足开闭原则
 *
 * @author luguosong
 * @date 2022/2/22 15:27
 */
public class Demo2 {
    public static void main(String[] args) {
        LoggerFactory factory;
        Logger logger;
        factory = (LoggerFactory) XMLUtil.getBean("_java/design_patterns/src/main/java/com/luguosong/_03_creational/_02_factory_method_pattern/config.xml").get(0); //getBean()的返回类型为Object，需要进行强制类型转换
        logger = factory.createLogger();
        logger.writeLog();
    }
}
