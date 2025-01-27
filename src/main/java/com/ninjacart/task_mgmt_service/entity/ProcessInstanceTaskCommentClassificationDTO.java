package com.ninjacart.task_mgmt_service.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceTaskCommentClassificationDTO {
    private Integer id;
    private String comment;
    private String classificationName;
    private Integer classificationId;
    private String classificationAliasName;
    private Integer processInstanceTaskId;
}
