package com.afims.repository;
import com.afims.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ImprovementPlanRepository extends JpaRepository<ImprovementPlan,Long> {
    List<ImprovementPlan> findByFacultyIdOrderByUpdatedAtDesc(Long id);
    long countByStatus(ImprovementPlan.Status status);
}
