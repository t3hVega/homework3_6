package com.homework.homework36.service;

import com.homework.homework36.model.Faculty;
import com.homework.homework36.model.Student;
import com.homework.homework36.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Student addStudent(Student student) {
        logger.info("Добавляем студента - {}", student.getName());
        return studentRepository.save(student);
    }
    public Student findStudent(long id) {
        logger.info("Ищем информацию по id студента - {}", id);
        return studentRepository.findById(id).get();
    }
    public Student editStudent(Student student) {
        logger.info("Редактируем информацию студента - {}", student.getName());
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        logger.info("Удаляем студента по id - {}", id);
        studentRepository.deleteById(id);
    }
    public List<Student> findByAge(Integer age) {
        logger.info("Ищем студентов по возрасту - {} лет", age);
        return studentRepository.findByAge(age);
    }
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        logger.info("Ищем студентов в диапазоне от {} до {} лет", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }
    public Faculty findFacultyByStudentId(long id) {
        logger.info("Ищем факультет студента по его id - {}", id);
        return studentRepository.findById(id)
            .map(it -> it.getFaculty())
            .orElse(null);}
    public Integer count() {
        logger.info("Считаем всех студентов");
        return studentRepository.getStudentCount();
    }
    public Double avgAge() {
        logger.info("Считаем средний возраст студентов");
        return studentRepository.getStudentAvgAge();
    }

    public List<Student> lastStudents() {
        logger.info("Выводим последних пятерых студентов");
        return studentRepository.getLastStudents();
    }

    public List<Student> getStudentsByName(String name) {
        logger.info("Ищем студентов по имени - {}", name);
        return studentRepository.getStudentsByName(name);
    };

    public List<Student> AStudents() {
        logger.info("Ищем студентов по именам, начинающимся на 'А'");
        List<Student> studentList = studentRepository.findAll();
        List<Student> AStudents = studentList
                .stream()
                .filter(student -> student.getName().charAt(0) == 'A')
                .collect(Collectors.toList());
        return AStudents;
    }

    public Double avgAgeByStream() {
        logger.info("Считаем средний возраст студентов через стрим");
        List<Student> studentList = studentRepository.findAll();
        double avgAge = studentList
                .stream()
                .mapToDouble(s -> s.getAge())
                .average()
                .orElse(Double.NaN);;
        return avgAge;
    }

    private void displayName(int i) {
        try {
            Thread.sleep(1000);
        }   catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(studentRepository.findAll().get(i).getName());
    }

    private synchronized void displayNameSync(int i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        System.out.println(studentRepository.findAll().get(i).getName());
    }

    public void getNamesByThread() {

        logger.info("Ищем имена студентов через потоки");
        displayName(0);
        displayName(1);

        new Thread(() -> {
            displayName(2);
            displayName(3);
        }).start();

        new Thread(() -> {
            displayName(4);
            displayName(5);
        }).start();
    }

    public void getNamesByThreadSync() {

        logger.info("Ищем имена студентов через синхронизированные потоки");
        displayNameSync(0);
        displayNameSync(1);

        new Thread(() -> {
            displayNameSync(2);
            displayNameSync(3);
        }).start();

        new Thread(() -> {
            displayNameSync(4);
            displayNameSync(5);
        }).start();
    }
}