package com.homework.homework36.repository;


import com.homework.homework36.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);
    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getStudentCount();
    @Query(value = "select avg(age) from student", nativeQuery = true)
    Float getStudentAvgAge();
    @Query(value = "select * from student order by id DESC limit 5", nativeQuery = true)
    List<Student> getLastStudents();
}
