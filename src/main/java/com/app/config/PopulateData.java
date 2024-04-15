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

package com.app.config;


import com.app.model.Module;
import com.app.model.Student;
import com.app.utils.BaseUtils;
import com.app.utils.SchoolUtils;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class PopulateData {
    private Faker faker;
    private Random random;

    public PopulateData(){
        faker = new Faker();
        random = new Random();
    }

    // Generate Modules
    public List<Module> generateModules(Integer amount){
        int counter = amount != null ? amount : random.nextInt(5, 15);
        List<Module> modules = new ArrayList<>();

        for(int i = 0; i < counter; i++){
            Module module = new Module();

            module.setCode(BaseUtils.generateModuleCode());
            module.setName(SchoolUtils.getRandomCourse());
            module.setCredits(random.nextInt(1, 5));

            // Add the the modules list...
            modules.add(module);
        }

        return modules;
    }

    // Generate Students
    public List<Student> generateStudents(Integer amount){
        int counter = amount != null ? amount : random.nextInt(5, 15);
        List<Student> students = new ArrayList<>();

        for(int i = 0; i < counter; i++){
            Student student = new Student();
            student.setId(BaseUtils.randomlyPickStudentID());
            student.setName(faker.name().fullName());
            student.setEmail(faker.internet().emailAddress());

            SchoolUtils.pick();
            student.setProgramme(SchoolUtils.PROGRAMME);
            student.setSchool(SchoolUtils.SCHOOL);

            // Add the the students list...
            students.add(student);
        }

        return students;
    }
}
