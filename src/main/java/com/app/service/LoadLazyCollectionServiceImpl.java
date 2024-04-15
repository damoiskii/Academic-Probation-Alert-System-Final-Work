/*
----------------------------------------------------------------------------------
                    Module: Artificial Intelligence (CMP4011)
                    Lab Tutor: Mr. Howard James
                    Class Group: Tuesdays @6pm
                    Year: 2023/2024 Semester 2
                    Assessment: Programming Group Project
                    Group Members:
                        Damoi Myers - 1703236
----------------------------------------------------------------------------------
*/

package com.app.service;

import com.app.model.Module;
import com.app.model.Student;
import com.app.repository.ModuleRepository;
import com.app.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class LoadLazyCollectionServiceImpl implements LoadLazyCollectionService{
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;


    /*
        Source of help/idea: https://stackoverflow.com/a/32276916/15978450

        Examples:
        Hibernate.initialize(passenger);

        or

        passenger.getFollowedBuses().size();
        passenger.getRoles().size();
     */

    @Override
    public Student start(Student s) {
        Optional<Student> userOptional = studentRepository.findById(s.getId());

        if(userOptional.isPresent()) {
            Hibernate.initialize(userOptional.get());
            return userOptional.get();
        }

        Student student = studentRepository.findByEmailEqualsIgnoreCase(s.getEmail());
        Hibernate.initialize(student);

        return student;
    }

    @Override
    public Module start(Module m) {
        Optional<Module> userOptional = moduleRepository.findById(m.getCode());

        if(userOptional.isPresent()) {
            Hibernate.initialize(userOptional.get());
            return userOptional.get();
        }

        Module module = moduleRepository.findByCodeEqualsIgnoreCase(m.getCode());
        Hibernate.initialize(module);

        return module;
    }
}
