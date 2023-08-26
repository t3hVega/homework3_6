package com.homework.homework36.controller;

import com.homework.homework36.model.Avatar;
import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.service.AvatarService;
import com.homework.homework36.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/student")
public class StudentController {



    private final StudentService studentService;


    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ok().build();
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<Student>> findStudents(@RequestParam(required = false) Integer age) {
        if (age != null) {
            return ok(studentService.findByAge(age));
        }
        return ok(Collections.emptyList());
    }

    @GetMapping("/by-age-between")
    public ResponseEntity<List<Student>> findStudentsBetween(@RequestParam(required = false) Integer min,
                                                             @RequestParam(required = false) Integer max) {
        if (min != null && max != null) {
            return ok(studentService.findByAgeBetween(min, max));
        }
        return ok(Collections.emptyList());
    }

    @GetMapping("{id}/faculty")
    public Faculty findFacultyByStudentId(@PathVariable long id) {
        return studentService.findFacultyByStudentId(id);
    }

    @GetMapping("/name/{name}")
    public List<Student> findStudentsByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/count")
    public Integer getStudentCount() {
        return studentService.count();
    }

    @GetMapping("/avg-age")
    public Double getStudentAvgAge() {
        return studentService.avgAge();
    }

    @GetMapping("/last-students")
    public List<Student> getLastStudents() {
        return studentService.lastStudents();
    }

    @GetMapping("/a-students")
    public List<Student> getAStudents() {
        return studentService.AStudents();
    }

    @GetMapping("/avg-age-stream")
    public Double getStudentAvgAgeByStream() {
        return studentService.avgAgeByStream();
    }

    @GetMapping("/students-names-thread")
    public void getStudentNamesStream() {
        studentService.getNamesByThread();
    }

    @GetMapping("/students-names-thread-sync")
    public void getStudentNamesThreadSync() {
        studentService.getNamesByThreadSync();
    }

}