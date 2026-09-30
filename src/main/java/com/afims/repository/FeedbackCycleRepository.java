package com.afims.repository;
import com.afims.entity.FeedbackCycle;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FeedbackCycleRepository extends JpaRepository<FeedbackCycle,Long> {
    Optional<FeedbackCycle> findFirstByStatusOrderByStartDateDesc(FeedbackCycle.Status status);
}
