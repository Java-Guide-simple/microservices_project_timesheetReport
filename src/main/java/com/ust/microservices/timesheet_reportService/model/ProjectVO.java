package com.ust.microservices.timesheet_reportService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class ProjectVO {


    private Integer projectId;
    private String projectType;
    private Integer contributionHrs;
    private String billable;

}
