package com.ust.microservices.timesheet_reportService.service;

import com.ust.microservices.timesheet_reportService.model.Employee;
import com.ust.microservices.timesheet_reportService.model.ListEmployeewithProjects;
import com.ust.microservices.timesheet_reportService.model.ProjectVO;
import com.ust.microservices.timesheet_reportService.repository.ReportRepository;
import com.ust.microservices.timesheet_reportService.vo.EmployeeVO;
import com.ust.microservices.timesheet_reportService.vo.Project;
import com.ust.microservices.timesheet_reportService.vo.ReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ReportService {
    @Autowired
    private ReportService reportService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReportRepository reportRepository;

    private static String start;
    private static String end;
    private static Set<Integer> projectIds = new HashSet<>();
    private static Integer totalWorkedHrs = 0;

    // Storing TimeSheets
    public void save(ProjectVO p) {
        log.info("inside reportService #save Method");
        reportRepository.save(p);
    }

    // Fetching TimeSheets  By ProjectId
    public List<ProjectVO> findByProjectId(Integer pid) {
        log.info("inside reportService # findByProjectId");
        List<ProjectVO> byProjectId = reportRepository.findByProjectId(pid);
        return byProjectId;

    }

    /*Consuming TimeSheet MicroServices
    And Fetching Filled TimeSheet Between Two Given Dates For Single Employee using Employee ID */
    public ReportResponse filledTimeSheetByEmployee(Integer EmployeeId, String startDate, String endDate) {
        log.info("inside reportService # filledTimeSheetByEmployee");
        ListEmployeewithProjects listEmployeewithProjects = restTemplate.getForObject("http://localhost:8071/api/timesheet/shows/" + EmployeeId + "/" + startDate + "/" + endDate, ListEmployeewithProjects.class);

        if (listEmployeewithProjects != null) {
            List<ProjectVO> projects = listEmployeewithProjects.getProjects();
            start = startDate;
            end = endDate;
            if (projects != null) {
                projects.stream().forEach(project -> {
                            reportService.save(project);
                            projectIds.add(project.getProjectId());
                        }
                );

                ReportResponse reportResponse = settingProjectResponse(EmployeeId);
                reportRepository.deleteAll();
                return reportResponse;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Setting the values in response Object
    public ReportResponse settingProjectResponse(Integer eid) {
        log.info("inside reportService #settingProjectResponse Method");
        ReportResponse reportResponse = new ReportResponse();

        Set<Project> projects = this.contributionHrs();
        EmployeeVO employeeDetails = this.findEmployeeDetails(eid);

        reportResponse.setEmployee(employeeDetails);
        reportResponse.setProjectsSet(projects);
        totalWorkedHrs = 0;
        return reportResponse;

    }

    /* Cunsumming Employee MicroServices
Fetching Employee Details By Employee Id */
    public EmployeeVO findEmployeeDetails(Integer eid) {
        log.info("inside reportService #findEmployeeDetails Method");

        Employee employee = restTemplate.getForObject("http://localhost:8080/api/employee/get-one/" + eid, Employee.class);
        if (employee != null) {
            EmployeeVO emp = new EmployeeVO();
            emp.setEmployeeId(eid);
            emp.setEmployeeName(employee.getName());
            emp.setDesignation(employee.getDesignation());
            emp.setTotalWorkedHrs(totalWorkedHrs);
            return emp;
        } else {
            return null;
        }
    }

    /* Calculating Working Hrs For Per Project
     and also Total Working Hrs For Per Employee In Given Dates */
    public Set<Project> contributionHrs() {
        log.info("inside reportService #contributionHrs() Method");
        Set<Project> projectSet = new HashSet<>();
        if (projectIds.size() != 0) {
            Integer[] projectIdss = projectIds.toArray(new Integer[projectIds.size()]);


            for (Integer projectId : projectIdss) {
                if (projectId != null) {
                    List<ProjectVO> byProjectId = reportService.findByProjectId(projectId);
                    if (byProjectId != null) {
                        Integer contributionHrs = 0;


                        for (int i = 0; i < byProjectId.size(); i++) {
                            ProjectVO projectVO = byProjectId.get(i);
                            Integer contribution = projectVO.getContributionHrs();
                            contributionHrs = contribution + contributionHrs;
                            totalWorkedHrs = totalWorkedHrs + contribution;
                        }

                        if (byProjectId.get(0) != null && byProjectId.get(0).getBillable() != null)
                            projectSet.add(new Project(projectId, byProjectId.get(0).getProjectType(), contributionHrs, byProjectId.get(0).getBillable(), start, end));
                    }
                }
            }
        }
        return projectSet;

    }


}
