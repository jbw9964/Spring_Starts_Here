package practice.with_spring.repositories;

import org.springframework.stereotype.Repository;
import practice.with_spring.models.Comment;

@Repository
public class DBCommentRepository implements CommentRepository {
    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment : " + comment.getText());
    }
}
