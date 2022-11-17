package com.refactorizando.example.batch.job;

import com.refactorizando.example.batch.entity.RecordEntity;
import com.refactorizando.example.batch.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.util.Random;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RecordJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RecordRepository repository;

    @Bean
    public Job job() {

        return jobBuilderFactory
                .get("job")
                .start(stepBuilderFactory
                        .get("jobStep1").tasklet((contribution, chunkContext) -> {

                            log.info("Job was run");

                            byte[] array = new byte[20];
                            new Random().nextBytes(array);
                            String randomText = new String(array, Charset.forName("UTF-8"));

                            RecordEntity recordEntity = new RecordEntity();
                            recordEntity.setMessage("This is a random message " + randomText);

                            repository.save(recordEntity);

                            return RepeatStatus.FINISHED;}
                        )
                        .build()).build();
    }
}
