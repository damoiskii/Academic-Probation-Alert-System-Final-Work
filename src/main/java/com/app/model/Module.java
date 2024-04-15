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
@Table(name = "modules")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = { "details" })
@ToString
public class Module {
    @Id
    @Column(name = "module_code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module", fetch = FetchType.LAZY)
    @OrderBy("semester ASC, year ASC")
    private Set<ModuleDetail> details;
}
