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

package com.app.repository;

import com.app.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
    Module findByCodeEqualsIgnoreCase(String code);
    Module findByNameEqualsIgnoreCase(String name);

    List<Module> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrderByNameAscCodeAsc(String code, String name);
}
