package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.CustomQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
// import java.util.M

@Repository
public interface QuizRepository extends JpaRepository<CustomQuiz, Long> {
    List<CustomQuiz> findByUserIdAndClassIdOrderByCreatedAtDesc(Long userId, Long classId);

    // New method to get the count of MCQ quizzes
    @Query("SELECT COUNT(m.id) " +
            "FROM QuizQuestion m " +
            "LEFT JOIN InstituteClass ic ON m.classId = ic.id " +
            "WHERE ic.teacherId = :teacherId " +
            "AND ic.instituteId = :instituteId")
    long countQuizzes(Long teacherId, Long instituteId);

}
