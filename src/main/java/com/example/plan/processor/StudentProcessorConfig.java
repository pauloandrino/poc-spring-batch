package com.example.plan.processor;

import com.example.plan.domain.Student;
import org.springframework.stereotype.Component;

import javax.batch.api.chunk.ItemProcessor;

@Component
public class StudentProcessorConfig implements ItemProcessor {
    @Override
    public Student processItem(Object o) throws Exception {
        System.out.println("PROCESSOR ");
        return (Student) o;
    }
}
