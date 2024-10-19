package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassTopic;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsAndMaterialsProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassTopicRepository extends JpaRepository<InstituteClassTopic, Long> {
    Optional<InstituteClassTopic> findById(Long id);

    @Query("SELECT ict.id as id, " +
            "ict.instituteId as instituteId, " +
            "ict.classId as classId, " +
            "ict.name as name, " +
            "ict.createdAt as createdAt, " +
            "icst as subTopics " +
            "FROM InstituteClassTopic ict " +
            "LEFT JOIN InstituteClassSubTopic icst ON icst.topicId = ict.id " +
            "WHERE ict.classId = :classId")
    List<InstituteClassTopicsWithSubTopicsProjection> findAllClassTopicsWithSubTopics(Long classId);

    @Query("SELECT ict.id as id, " +
            "ict.instituteId as instituteId, " +
            "ict.classId as classId, " +
            "ict.name as name, " +
            "ict.createdAt as createdAt, " +
            "icst as subTopics, " +
            "icm as materials " +
            "FROM InstituteClassTopic ict " +
            "LEFT JOIN InstituteClassSubTopic icst ON icst.topicId = ict.id " +
            "LEFT JOIN InstituteClassMaterial icm ON icm.subTopicId = icst.id " +
            "WHERE ict.classId = :classId")
    List<InstituteClassTopicsWithSubTopicsAndMaterialsProjection> findAllClassTopicsWithSubTopicsAndMaterials(Long classId);

    List<InstituteClassTopic> findAllByClassId(Long classId);
}
