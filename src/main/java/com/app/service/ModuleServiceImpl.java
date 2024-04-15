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
import com.app.repository.ModuleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public Module addModule(Module module) throws ModuleFoundException {
        // Check if the module code exist
        if(codeFound(module.getCode())) throw new ModuleFoundException("Module code already exist!");

        // Check if the module name exist
        if(nameFound(module.getName())) throw new ModuleFoundException("Module name already exist!");

        return moduleRepository.saveAndFlush(module);
    }

    @Override
    public Module updateModule(Module module) throws ModuleNotFoundException {
        Module m = moduleRepository.findByNameEqualsIgnoreCase(module.getName());

        if(m == null) throw new ModuleNotFoundException("Module with the name " + module.getName() + " not found!");

        return moduleRepository.saveAndFlush(module);
    }

    @Override
    public List<Module> findAllModules() {
        return moduleRepository.findAll(Sort.by("name").ascending().and(Sort.by("code").ascending()));
    }

    @Override
    public void deleteAllModules() {
        moduleRepository.deleteAll();
    }

    @Override
    public void deleteModule(String code) throws ModuleNotFoundException {
        Module module = moduleRepository.findByCodeEqualsIgnoreCase(code);

        if(module == null) throw new ModuleNotFoundException("Module with the code " + code + " not found!");

        moduleRepository.delete(module);
    }

    @Override
    public void deleteModule(Module module) {
        moduleRepository.delete(module);
    }

    @Override
    public List<Module> findAllStudentsByKeyword(String keyword) {
        return moduleRepository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrderByNameAscCodeAsc(keyword, keyword);
    }

    @Override
    public boolean codeFound(String code) {
        Module module = moduleRepository.findByCodeEqualsIgnoreCase(code);

        return module != null;
    }

    @Override
    public boolean nameFound(String name) {
        Module module = moduleRepository.findByNameEqualsIgnoreCase(name);

        return module != null;
    }
}
