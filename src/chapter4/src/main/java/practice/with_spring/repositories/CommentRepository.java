package practice.with_spring.repositories;

import practice.with_spring.models.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
