package com.afims.service;
import com.afims.entity.Notification;
import com.afims.entity.User;
import com.afims.repository.NotificationRepository;
import org.springframework.stereotype.Service;
@Service public class NotificationService {
    private final NotificationRepository repo;
    public NotificationService(NotificationRepository r) {
        repo=r;
    }
    public void send(User user,String title,String message,String type) {
        if(user!=null)repo.save(new Notification(user,title,message,type));
    }
}
