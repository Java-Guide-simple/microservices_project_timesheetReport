package com.ust.microservices.timesheet_reportService.controller;


import com.ust.microservices.timesheet_reportService.service.ReportService;
import com.ust.microservices.timesheet_reportService.vo.ReportResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })



    // Generating  Report of Employee for Time Duration (from StartDate To EndDate)
    @ApiOperation(value = "Generate Report of Employee for Time Duration (from StartDate To EndDate)",
            response = ReportResponse.class, tags = "Generate Report")

    @GetMapping("/show/report/{EmployeeId}/{startDate}/{endDate}") // yyyy-MM-DD
    public ReportResponse filledTimeSheetByEmployee(@PathVariable Integer EmployeeId, @PathVariable String startDate, @PathVariable String endDate) {
        log.info("Inside ReportController # mehtod Name  filledTimeSheetByEmployee");
        ReportResponse reportResponse = reportService.filledTimeSheetByEmployee(EmployeeId, startDate, endDate);

        return reportResponse;
    }

}

