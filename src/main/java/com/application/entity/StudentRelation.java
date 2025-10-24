package com.application.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sce_stud_relation" , schema = "sce_student")
public class StudentRelation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_relation_id") // --- Map to DB column ---
    private int studentRelationId;
    
    @Column(name = "student_relation_type") // --- Map to DB column ---
    private String studentRelationType;
    
    // --- FIX: RENAME THIS FIELD to match the repository method ---
    @Column(name = "is_active") // --- Map to DB column ---
    private int isActive;
}
