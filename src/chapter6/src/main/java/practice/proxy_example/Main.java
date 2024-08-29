package practice.proxy_example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import practice.proxy_example.beans.AdvisedBean;
import practice.proxy_example.beans.NormalBean;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(
                Config.class
        );

        NormalBean normalBean = context.getBean(NormalBean.class);
        Config.showIdentity(normalBean);

        AdvisedBean advisedBean = context.getBean(AdvisedBean.class);
        Config.showIdentity(advisedBean);
    }
}
