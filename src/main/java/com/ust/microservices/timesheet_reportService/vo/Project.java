package com.ust.microservices.timesheet_reportService.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private Integer projectId;
    private String projectType;
    private Integer totalContributionHrs;
    private String billable;
    private String startDate;
    private String endDate;

}
