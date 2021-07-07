package com.ust.microservices.timesheet_reportService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private String designation;
    private String profile_pic;
}