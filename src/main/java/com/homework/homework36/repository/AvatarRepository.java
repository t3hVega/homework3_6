package com.homework.homework36.repository;

import com.homework.homework36.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId (long id);
    List<Avatar> findAll();
}
