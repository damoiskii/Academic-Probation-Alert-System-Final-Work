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
@Table(name = "module_details")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ModuleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "module_details_id", nullable = false)
    private Long id;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "semester", nullable = false)
    private String semester;

    @ManyToOne
    @JoinColumn(name = "module_code")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "grade_points")
    private Double gradePoints;

    @Column(name = "complete")
    private Boolean complete;

    @Column(name = "redo")
    private Boolean redo;
}
