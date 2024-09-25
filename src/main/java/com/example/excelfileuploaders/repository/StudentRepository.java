package com.example.excelfileuploaders.repository;

import com.example.excelfileuploaders.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT * FROM student", nativeQuery = true)
    List<Student> getAll();
}
