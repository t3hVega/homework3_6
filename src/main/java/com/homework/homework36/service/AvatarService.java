package com.homework.homework36.service;

import com.homework.homework36.model.Avatar;
import com.homework.homework36.model.Student;
import com.homework.homework36.repository.AvatarRepository;
import com.homework.homework36.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Path path = saveToDisk(student, avatarFile);
        saveToDB(student, path, avatarFile);
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path saveToDisk(Student student, MultipartFile avatarFile) throws IOException {
        Path filePath = Path.of(
                avatarsDir,
                student.getId() + "." + getExtensions(avatarFile.getOriginalFilename())
        );

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        return filePath;
    }

    private void saveToDB (Student student, Path filePath, MultipartFile avatarFile) throws IOException{
        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(long studentId) {
        return avatarRepository
                .findByStudentId(studentId)
                .orElse(new Avatar());
    }

    public List<Avatar> getAvatars(int pageNum, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
