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

package com.app.prolog;


import com.app.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.projog.api.Projog;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class PrologHelper {
    private final Projog projog;

    // This will be used to instantiate the new prolog object in the prolog engine
    public Projog create(){
        return projog;
    }

    // This will be used to re-instantiate (for re-consultation) the prolog object in the prolog engine to capture any updates made to the file
    public Projog recreate(){
        // Create a new Projog instance.
        Projog projog = new Projog();

        // Read Prolog facts and rules from a file to populate the "Projog" instance.
        projog.consultFile(new File("src/main/resources/prolog/" + FileUtils.PrologFilename));

        return projog;
    }
}
