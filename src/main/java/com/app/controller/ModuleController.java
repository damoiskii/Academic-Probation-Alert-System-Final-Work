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

package com.app.controller;


import com.app.exception.ModuleFoundException;
import com.app.exception.ModuleNotFoundException;
import com.app.model.Module;
import com.app.service.ModuleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleServiceImpl moduleService;


    // Add module
    public Module addModule(Module module) throws ModuleFoundException {
       return moduleService.addModule(module);
    }

    // Get all modules
    public List<Module> getAllModules(){
        return moduleService.findAllModules();
    }

    // Delete all modules
    public void deleteAllModules(){
        moduleService.deleteAllModules();
    }

    // Search by keyword
    public List<Module> getAllModuleByKeyword(String keyword){
        return moduleService.findAllStudentsByKeyword(keyword);
    }

    // Delete module
    public void delete(String code) throws ModuleNotFoundException {
        moduleService.deleteModule(code);
    }

    public void delete(Module module){
        moduleService.deleteModule(module);
    }

    // Update module
    public Module updateModule(Module module, String oldName) throws ModuleFoundException, ModuleNotFoundException {
        // If old name is not the same as the new name
        if(!module.getName().trim().equalsIgnoreCase(oldName)){
            if(moduleService.nameFound(module.getName())){
                throw new ModuleFoundException("You cannot use this module name! Please choose another...");
            }
        }

        return moduleService.updateModule(module);
    }

    public Module updateModule(Module module) throws ModuleNotFoundException {
        return moduleService.updateModule(module);
    }
}
