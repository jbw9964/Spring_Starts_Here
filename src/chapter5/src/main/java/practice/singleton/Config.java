package practice.singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;

@Configuration
public class Config {
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    @Lazy
    public SingletonBean singleton() {
        return new SingletonBean();
    }
}

class SingletonBean {}