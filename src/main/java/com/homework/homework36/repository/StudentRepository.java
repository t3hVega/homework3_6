package com.homework.homework36.repository;


import com.homework.homework36.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);
    List<Student> getStudentsByName(String name);
    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer getStudentCount();
    @Query(value = "select avg(age) from students", nativeQuery = true)
    Double getStudentAvgAge();
    @Query(value = "select * from students order by id DESC limit 5", nativeQuery = true)
    List<Student> getLastStudents();
}
