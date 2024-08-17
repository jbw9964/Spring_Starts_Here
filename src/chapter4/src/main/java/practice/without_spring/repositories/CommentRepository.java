package practice.without_spring.repositories;

import practice.without_spring.models.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
