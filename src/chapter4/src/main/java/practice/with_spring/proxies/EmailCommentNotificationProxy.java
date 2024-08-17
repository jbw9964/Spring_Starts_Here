package practice.with_spring.proxies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import practice.with_spring.models.Comment;

@Primary
@Component
@Qualifier("EmailProxy")
public class EmailCommentNotificationProxy implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending EMAIL notification for comment : " + comment.getText());
    }
}
