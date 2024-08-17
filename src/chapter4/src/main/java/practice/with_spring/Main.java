package practice.with_spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import practice.with_spring.models.Comment;
import practice.with_spring.services.CommentService;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(Config.class);

        CommentService service = context.getBean(CommentService.class);

        Comment comment = new Comment();
        comment.setAuthor("Anonymous");
        comment.setText("Comment with Spring!");

        service.publishComment(comment);
    }
}
