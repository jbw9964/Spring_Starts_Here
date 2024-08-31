package practice.advice_example;

import org.springframework.stereotype.Component;

@Component
public class Bean    {
    public String someMethod(Object arg)  {
        System.out.print("[AT BEAN] someMethod in Process with arg : ");
        System.out.println(arg.toString());
        return "someMethod";
    }
}
