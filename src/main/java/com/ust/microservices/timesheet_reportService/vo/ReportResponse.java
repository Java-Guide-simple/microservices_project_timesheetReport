package com.ust.microservices.timesheet_reportService.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

   private EmployeeVO employee;
    private Set<Project> projectsSet;

}
