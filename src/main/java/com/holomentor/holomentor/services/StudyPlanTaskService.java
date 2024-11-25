package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
import com.holomentor.holomentor.dto.studyPlan.StudyPlanCreateTaskDTO;
import com.holomentor.holomentor.dto.studyPlanTask.StudyPlanTaskCreateDTO;
import com.holomentor.holomentor.dto.studyPlanTask.StudyPlanTaskUpdateDTO;
import com.holomentor.holomentor.models.ClassTierStudyPlanTask;
import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.repositories.ClassTierStudyPlanTaskRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudyPlanTaskService {

    @Autowired
    private ClassTierStudyPlanTaskRepository classTierStudyPlanTaskRepository;

    public ResponseEntity<Object> create(StudyPlanTaskCreateDTO body) {
        ClassTierStudyPlanTask task = new ClassTierStudyPlanTask();

        task.setClassId(body.getClassId());
        task.setInstituteId(body.getInstituteId());
        task.setStudyPlanId(body.getStudyPlanId());
        task.setTitle(body.getTitle());
        task.setDescription(body.getDescription());

        classTierStudyPlanTaskRepository.save(task);

        return Response.generate("class tier study plan task created", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getPlanTasks(Long classId, Long studyPlanId) {
        List<ClassTierStudyPlanTask> studyPlanTasks = classTierStudyPlanTaskRepository.findByClassIdAndStudyPlanId(classId, studyPlanId);

        return Response.generate("class tier study plan tasks", HttpStatus.OK, studyPlanTasks);
    }

    public ResponseEntity<Object> delete(Long taskId) {
        Optional<ClassTierStudyPlanTask> studyPlanTasks = classTierStudyPlanTaskRepository.findById(taskId);
        if (studyPlanTasks.isEmpty()) {
            return Response.generate("class tier study plan task not found", HttpStatus.NOT_FOUND);
        }

        classTierStudyPlanTaskRepository.delete(studyPlanTasks.get());

        return Response.generate("class tier study plan task details deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> update(Long id, StudyPlanTaskUpdateDTO body) {
        Optional<ClassTierStudyPlanTask> studyPlanTask = classTierStudyPlanTaskRepository.findById(id);
        if (studyPlanTask.isEmpty()) {
            return Response.generate("class tier study plan task not found", HttpStatus.NOT_FOUND);
        }
        ClassTierStudyPlanTask institute = studyPlanTask.get();
        institute.setTitle(body.getTitle());
        institute.setDescription(body.getDescription());

        classTierStudyPlanTaskRepository.save(institute);
        return Response.generate("class tier study plan task details have been updated.", HttpStatus.OK);
    }
}