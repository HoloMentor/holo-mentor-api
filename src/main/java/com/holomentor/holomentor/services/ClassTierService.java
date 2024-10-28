package com.holomentor.holomentor.services;

import com.holomentor.holomentor.repositories.InstituteClassTierRepository;
import com.holomentor.holomentor.repositories.InstituteClassTierStudentRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ClassTierService {
   @Autowired
    private InstituteClassTierRepository instituteClassTierRepository;
   @Autowired
    private InstituteClassTierStudentRepository instituteClassTierStudentRepository;

   public ResponseEntity<Object> create() {
       return Response.generate("tier created", HttpStatus.CREATED);
   }
}
