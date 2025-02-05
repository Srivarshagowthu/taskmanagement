//package com.sky.cloud.mapper;
//
//import com.sky.cloud.dto.ProcessDTO;
//import com.sky.cloud.entity.ProcessTask;
//
//public class ProcessTaskMapper {
//
//    // Convert ProcessDTO to ProcessEntity
//    public static ProcessTask toEntity(ProcessDTO dto) {
//        ProcessTask entity = new ProcessTask();
//        entity.setId(dto.getId());  // Map properties as needed
//        entity.setName(dto.getName());
//        entity.setDescription(dto.getDescription());
//        entity.setDefaultPriority(dto.getDefaultPriority());
//        return entity;
//    }
//
//    // Convert ProcessEntity to ProcessDTO
//    public static ProcessDTO toDTO(ProcessTask entity) {
//        ProcessDTO dto = new ProcessDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setDescription(entity.getDescription());
//        dto.setDefaultPriority(entity.getDefaultPriority());
//        return dto;
//    }
//}
