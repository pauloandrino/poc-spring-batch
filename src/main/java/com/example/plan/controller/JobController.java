package com.example.plan.controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

//    @Autowired
//    @Qualifier("enrollmentsJob") Job job;


    @Autowired
    @Qualifier("enrollmentsParallelJob") Job job;

    @GetMapping("/jobLauncher")
    public void handle() throws Exception{
        Map<String, JobParameter> parameter = new HashMap<>();
        parameter.put("fileName", new JobParameter("enrollement.xlsx"));
        jobLauncher.run(job, new JobParameters(parameter));
    }
}
