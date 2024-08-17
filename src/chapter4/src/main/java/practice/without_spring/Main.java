package practice.without_spring;

import practice.without_spring.models.Comment;
import practice.without_spring.proxies.CommentNotificationProxy;
import practice.without_spring.proxies.EmailCommentNotificationProxy;
import practice.without_spring.repositories.CommentRepository;
import practice.without_spring.repositories.DBCommentRepository;
import practice.without_spring.services.CommentService;

public class Main {
    public static void main(String[] args) {
        CommentRepository repo
                = new DBCommentRepository();
        CommentNotificationProxy notificationProxy
                = new EmailCommentNotificationProxy();

        CommentService service
                = new CommentService(repo, notificationProxy);

        Comment comment = new Comment();
        comment.setAuthor("Anonymous");
        comment.setText("My First Comment!");

        service.publishComment(comment);
    }
}
