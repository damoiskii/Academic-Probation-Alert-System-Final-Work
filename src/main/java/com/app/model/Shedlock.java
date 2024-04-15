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

import java.sql.Timestamp;

@Entity
@Table(name = "shedlock")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Shedlock { // Creating table in db as mentioned at: https://reflectoring.io/spring-scheduler/
    @Id
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "lock_until", nullable = false)
    private Timestamp lockUntil;

    @Column(name = "locked_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lockedAt;

    @Column(name = "locked_by", nullable = false, length = 255)
    private String lockedBy;
}