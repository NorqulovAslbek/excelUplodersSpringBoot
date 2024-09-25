package com.example.excelfileuploaders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Student {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String name;
//    private Integer age;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime localDateTime;
    private Integer number1;
    private Integer number2;
    @Column(length = 1000)
    private String shot;
    private Double summa1;
    private Double summa2;
}
