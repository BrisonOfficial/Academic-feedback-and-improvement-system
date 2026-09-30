package com.afims.repository;
import com.afims.entity.Question;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByActiveTrueOrderByDisplayOrderAsc();
}
