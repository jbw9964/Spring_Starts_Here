package practice.proxy_example;

import org.springframework.context.annotation.*;
import practice.proxy_example.beans.AdvisedBean;
import practice.proxy_example.beans.NormalBean;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class Config {
    @Bean
    @Lazy
    public NormalBean normalBean()  {
        NormalBean bean = new NormalBean();
        showIdentity(bean);
        return bean;
    }

    @Bean
    @Lazy
    public AdvisedBean advisedBean()  {
        AdvisedBean bean = new AdvisedBean();
        showIdentity(bean);
        return bean;
    }

    public static void showIdentity(Object o)   {
        System.out.printf(
                "[%s] \t: 0x%08x\n",
                o.getClass().getSimpleName(), System.identityHashCode(o)
        );
    }
}

