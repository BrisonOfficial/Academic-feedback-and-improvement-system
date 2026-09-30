package com.afims.repository;
import com.afims.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findTop20ByUserIdOrderByCreatedAtDesc(Long id);
    long countByUserIdAndReadFlagFalse(Long id);
}
