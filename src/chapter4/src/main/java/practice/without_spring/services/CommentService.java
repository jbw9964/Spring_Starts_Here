package practice.without_spring.services;

import practice.without_spring.models.Comment;
import practice.without_spring.proxies.CommentNotificationProxy;
import practice.without_spring.repositories.CommentRepository;

public class CommentService {
    private final CommentRepository repo;
    private final CommentNotificationProxy notificationProxy;

    public CommentService(CommentRepository repo, CommentNotificationProxy notificationProxy) {
        this.repo              = repo;
        this.notificationProxy = notificationProxy;
    }

    public void publishComment(Comment comment) {
        repo.storeComment(comment);
        notificationProxy.sendComment(comment);
    }
}
