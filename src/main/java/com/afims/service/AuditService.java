package com.afims.service;
import com.afims.entity.*;
import com.afims.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
@Service public class AuditService {
    final AuditLogRepository repo;
    public AuditService(AuditLogRepository r) {
        repo=r;
    }
    public void log(User u,String a,String e,Long id,String ip) {
        repo.save(new AuditLog(u,a,e,id,ip));
    }
}
