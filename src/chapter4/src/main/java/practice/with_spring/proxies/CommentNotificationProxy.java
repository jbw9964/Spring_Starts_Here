package practice.with_spring.proxies;

import practice.with_spring.models.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
