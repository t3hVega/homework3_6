package com.homework.homework36.controller;

import com.homework.homework36.model.Avatar;
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

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarService avatarService;


    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long studentId,
            @RequestParam MultipartFile avatar
    ) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ok().build();
    }

    @GetMapping(value = "/{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{studentId}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.findAvatar(studentId);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/count")
    public Integer getStudentCount() {
        return studentService.count();
    }

    @GetMapping("/avg-age")
    public Float getStudentAvgAge() {
        return studentService.avgAge();
    }

    @GetMapping("/last-students")
    public List<Student> getLastStudents() {
        return studentService.lastStudents();
    }

    @GetMapping("/avatars")
    public ResponseEntity<List<Avatar>> getAvatars(@RequestParam int pageNum, @RequestParam int pageSize, HttpServletResponse response) throws IOException{
        List<Avatar> avatars= avatarService.getAvatars(pageNum, pageSize);
        return ResponseEntity.ok(avatars);
    }
}