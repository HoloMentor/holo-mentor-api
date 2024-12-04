package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.projections.SystemStatsProjection;
import com.holomentor.holomentor.projections.user.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<UserProjection> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndIsDeleted(String fname, String lname, String email, Boolean isDeleted, Pageable pageable);

    @Query(value =  "SELECT " +
            "  (SELECT COUNT(*) FROM Institute) AS instituteCount, " +
            "  SUM(CASE WHEN role = 'TEACHER' THEN 1 ELSE 0 END) AS teacherCount, " +
            "  SUM(CASE WHEN role = 'STUDENT' THEN 1 ELSE 0 END) AS studentCount, " +
            "  SUM(CASE WHEN role = 'STAFF' THEN 1 ELSE 0 END) AS staffCount " +
            "FROM UserInstitute")
    SystemStatsProjection findAllStats();
}
