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

package com.app.view.listeners;


import com.app.exception.ModuleNotFoundException;
import com.app.model.Module;

import java.util.List;


public interface ModuleTabListener {
    boolean addModule(Module module);
    boolean updateModule(Module module, String oldName);

    List<Module> generateModules(int amount);
    List<Module> refreshTable();
    List<Module> search(String keyword);

    void deleteModules();
    void deleteModule(String moduleCode) throws ModuleNotFoundException;
    void deleteModule(Module module);

    boolean isCodeFound(String module);
}
