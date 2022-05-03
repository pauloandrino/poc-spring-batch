package com.example.plan.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@EnableBatchProcessing
@Configuration
public class EnrollmentsJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job enrollmentsJob(@Qualifier("studentsEnrollmentsJobStep") Step studantsEnrollmentsJobStep,
                              @Qualifier("teacherEnrollmentsJobStep") Step teacherEnrollmentsJobStep) {
        return jobBuilderFactory
                .get("enrollmentsJob")
                .start(studantsEnrollmentsJobStep)
                .next(teacherEnrollmentsJobStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }


    @Bean
    public Job enrollmentsParallelJob(@Qualifier("studentsEnrollmentsJobStep") Step studantsEnrollmentsJobStep,
                                      @Qualifier("teacherEnrollmentsJobStep") Step teacherEnrollmentsJobStep) {
        return jobBuilderFactory
                .get("enrollmentsParallelJob")
                .start(stepsParalelos(studantsEnrollmentsJobStep, teacherEnrollmentsJobStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow stepsParalelos(Step studantsEnrollmentsJobStep, Step teacherEnrollmentsJobStep) {
        Flow studentsStep  = new FlowBuilder<Flow>("studentsStep")
                .start(studantsEnrollmentsJobStep)
                .build();

        Flow stepsParalelos = new FlowBuilder<Flow>("teacherStep")
                .start(teacherEnrollmentsJobStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(studentsStep)
                .build();

        return stepsParalelos;

    }




}
