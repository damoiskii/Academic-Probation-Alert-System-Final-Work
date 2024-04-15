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


import com.app.utils.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.projog.api.Projog;

import java.io.File;


@Configuration
public class PrologConfiguration {

    @Bean
    public Projog getProlog() {
        // Create a new Projog instance.
        Projog projog = new Projog();

        // Read Prolog facts and rules from a file to populate the "Projog" instance.
        projog.consultFile(new File("src/main/resources/prolog/" + FileUtils.PrologFilename));

        return projog;
    }
}
