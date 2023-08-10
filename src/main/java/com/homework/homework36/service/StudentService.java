package com.homework.homework36.service;

import com.homework.homework36.model.Avatar;
import com.homework.homework36.model.Student;
import com.homework.homework36.repository.AvatarRepository;
import com.homework.homework36.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    public List<Student> findByAge(Integer age) {
        return studentRepository.findByAge(age);
    }
    public List<Student> findByAgeBetween(Integer min, Integer max) {return studentRepository.findByAgeBetween(min, max);}
    public Integer count() {
        return studentRepository.getStudentCount();
    }
    public Float avgAge() {
        return studentRepository.getStudentAvgAge();
    }

    public List<Student> lastStudents() {
        return studentRepository.getLastStudents();
    }

}