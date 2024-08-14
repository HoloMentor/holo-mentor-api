package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.models.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserInvitationRepository extends JpaRepository<UserInvitation, Long> {
}
