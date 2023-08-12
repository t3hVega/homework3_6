package com.homework.homework36.mapper;

import com.homework.homework36.dto.AvatarDTO;
import com.homework.homework36.model.Avatar;
import org.springframework.stereotype.Component;

@Component
public class AvatarMapper {

    public AvatarDTO mapToDTO(Avatar avatar) {
        return new AvatarDTO(
                avatar.getId(),
                avatar.getFilePath(),
                avatar.getFileSize(),
                avatar.getMediaType(),
                avatar.getStudent().getId()
        );
    }
}
