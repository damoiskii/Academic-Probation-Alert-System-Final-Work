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

import com.app.exception.ModuleFoundException;
import com.app.exception.ModuleNotFoundException;
import com.app.model.Module;

import java.util.List;


public interface ModuleService {
    Module addModule(Module module) throws ModuleFoundException;
    Module updateModule(Module module) throws ModuleNotFoundException;

    List<Module> findAllModules();
    void deleteAllModules();
    void deleteModule(String id) throws ModuleNotFoundException;
    void deleteModule(Module module);

    List<Module> findAllStudentsByKeyword(String keyword);

    boolean codeFound(String code);
    boolean nameFound(String name);
}
