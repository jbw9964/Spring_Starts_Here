package practice.without_spring.repositories;

import practice.without_spring.models.Comment;

public class DBCommentRepository implements CommentRepository {
    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment : " + comment.getText());
    }
}
