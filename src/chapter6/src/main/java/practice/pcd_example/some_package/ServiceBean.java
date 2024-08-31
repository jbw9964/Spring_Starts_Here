package practice.pcd_example.some_package;

import org.springframework.stereotype.Service;

@Service
public class ServiceBean {
    public String weiredMethod()    {
        System.out.println("[At ServiceBean] : Service method in progress");
        return "SERVICE!!";
    }
}
