package practice.without_spring.proxies;

import practice.without_spring.models.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
