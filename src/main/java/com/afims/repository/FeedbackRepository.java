package com.afims.repository;
import com.afims.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    boolean existsByStudentIdAndFacultyIdAndCourseAndCycleId(Long s,Long f,String c,Long cy);
    List<Feedback> findByStudentIdOrderByCreatedAtDesc(Long id);
    List<Feedback> findByFacultyIdOrderByCreatedAtDesc(Long id);
    @Query("select avg(f.overallRating) from Feedback f") Double averageRating();
    @Query("select count(f) from Feedback f") long total();
    @Query("select count(f) from Feedback f where f.overallRating >= 4") long positive();
}
