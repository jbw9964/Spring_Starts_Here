package practice.with_spring.proxies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import practice.with_spring.models.Comment;

@Component
@Qualifier("PushProxy")
public class CommentPushNotificationProxy implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending PUSH notification for comment : " + comment.getText());
    }
}
