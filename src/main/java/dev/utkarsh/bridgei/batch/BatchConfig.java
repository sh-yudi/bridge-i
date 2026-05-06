package dev.utkarsh.bridgei.batch;

import dev.utkarsh.bridgei.mapper.CustomerRowMapper;
import dev.utkarsh.bridgei.model.CustomerCloud;
import dev.utkarsh.bridgei.model.CustomerLegacy;
import dev.utkarsh.bridgei.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // 1. THE EXTRACTOR: Connects to AS/400 and reads rows one by one
    @Bean
    public ItemReader<CustomerLegacy> as400Reader(@Qualifier("as400DataSource") DataSource as400DataSource) {
        return new JdbcCursorItemReaderBuilder<CustomerLegacy>()
                .name("as400CustomerReader")
                .dataSource(as400DataSource)
                // NOTE: Replace YOUR_LIB with your actual AS/400 library later!
                .sql("SELECT CUSTID, NAME, STATUS, JOIN_DATE FROM shyudi081.CUSTOMER")
                .rowMapper(new CustomerRowMapper())
                .fetchSize(100)
                .build();
    }

    // 2. THE LOADER: Uses your Repository to save the cleaned data to PostgreSQL
    @Bean
    public ItemWriter<CustomerCloud> postgresWriter(CustomerRepository repository) {
        // We use lambda syntax here for clean, modern Java
        return items -> repository.saveAll(items);
    }

    // 3. THE STEP: Ties the Extract, Transform, and Load together
    @Bean
    public Step syncCustomerStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 ItemReader<CustomerLegacy> reader,
                                 ItemProcessor<CustomerLegacy, CustomerCloud> processor,
                                 ItemWriter<CustomerCloud> writer) {

        return new StepBuilder("syncCustomerStep", jobRepository)
                // "chunk(100)" means it grabs 100 AS/400 records, processes them,
                // and commits them to Postgres at once. High performance!
                .<CustomerLegacy, CustomerCloud>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // 4. THE JOB: The master wrapper that Spring Boot will automatically run
    @Bean
    public Job syncCustomerJob(JobRepository jobRepository, Step syncCustomerStep) {
        return new JobBuilder("syncCustomerJob", jobRepository)
                .start(syncCustomerStep)
                .build();
    }
}