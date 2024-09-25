package com.example.excelfileuploaders.service;

import com.example.excelfileuploaders.entity.Student;
import com.example.excelfileuploaders.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.getAll();
    }
}
