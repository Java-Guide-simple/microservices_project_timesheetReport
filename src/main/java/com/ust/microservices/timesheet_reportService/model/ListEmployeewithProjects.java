package com.ust.microservices.timesheet_reportService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListEmployeewithProjects {

    private Integer employeeId;
    private List<ProjectVO> projects;
}
