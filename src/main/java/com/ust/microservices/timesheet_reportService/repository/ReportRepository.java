package com.ust.microservices.timesheet_reportService.repository;

import com.ust.microservices.timesheet_reportService.model.ProjectVO;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<ProjectVO, Integer> {

    public List<ProjectVO>  findByProjectId(Integer pid);



}
