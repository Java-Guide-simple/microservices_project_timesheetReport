package com.ust.microservices.timesheet_reportService.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {

    private Integer employeeId;
    private String employeeName;
    private String designation;
    private Integer TotalWorkedHrs;

}
