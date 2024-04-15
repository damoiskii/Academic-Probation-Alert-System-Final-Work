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

@Entity
@Table(name = "probation")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Probation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "probation_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentID;

    @Column(name = "gpa", nullable = false)
    private Double gpa;

    @Column(name = "school", nullable = false)
    private String school;

    @Column(name = "programme", nullable = false)
    private String programme;
}
