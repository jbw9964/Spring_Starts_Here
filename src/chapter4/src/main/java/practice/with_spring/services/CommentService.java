package practice.with_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import practice.with_spring.models.Comment;
import practice.with_spring.proxies.CommentNotificationProxy;
import practice.with_spring.repositories.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository repo;
    private final CommentNotificationProxy notificationProxy;

    @Autowired
    public CommentService(
            CommentRepository repo,
            @Qualifier("PushProxy") CommentNotificationProxy notificationProxy
    ) {
        this.repo              = repo;
        this.notificationProxy = notificationProxy;
    }

    public void publishComment(Comment comment) {
        repo.storeComment(comment);
        notificationProxy.sendComment(comment);
    }
}
