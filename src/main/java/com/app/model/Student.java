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

package com.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Student {
    @Id
    @Column(name = "student_id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "school", nullable = false, length = 300)
    private String school;

    @Column(name = "programme", nullable = false, length = 300)
    private String programme;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
    private Set<ModuleDetail> modules;
}
