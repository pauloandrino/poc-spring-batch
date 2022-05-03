package com.example.plan.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentWriter {
    @Bean
    public ItemWriter fileStudentWriter()  {
        return items -> items.forEach(System.out::println);
    }

}
