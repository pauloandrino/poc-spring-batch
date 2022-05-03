package com.example.plan.step;

import com.example.plan.domain.Student;
import com.example.plan.domain.Teacher;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnrollmentsJobStepConfig {

    @Value("${batch.studentsEnrollmentsJobStep.chunksize}")
    private int chunkSize;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step studentsEnrollmentsJobStep(
            ItemReader<Student> studentReader,
            ItemWriter studentWriter
    ) {
        return stepBuilderFactory
                .get("studantsEnrollmentsJobStep")
                .chunk(chunkSize)
                .reader(studentReader)
                .writer(studentWriter)
                .build();
    }


    @Bean
    public Step teacherEnrollmentsJobStep(
            ItemReader<Teacher> teacherReader,
            ItemWriter teacherWriter
    ) {
        return stepBuilderFactory
                .get("studantsEnrollmentsJobStep")
                .chunk(chunkSize)
                .reader(teacherReader)
                .writer(teacherWriter)
                .build();
    }

}
