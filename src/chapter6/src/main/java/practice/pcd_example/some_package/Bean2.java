package practice.pcd_example.some_package;

import org.springframework.stereotype.Component;

@Component
public class Bean2 {
    public void singleArgMethod(Integer num)    {
        System.out.println("[AT BEAN2] : singleArgMethod (Integer) = " + num);
    }

    public void singleArgMethod(Bean2 bean2) {
        System.out.println("[AT BEAN2] : singleArgMethod (Bean2) = " + bean2);
    }
}
