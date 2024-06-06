package com.example.soll.backend.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        executeSqlScript("SeoulParkingLot.sql");
        executeSqlScript("NationalParkingLot.sql");
    }

    private void executeSqlScript(String scriptPath) throws Exception {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource(scriptPath));
        resourceDatabasePopulator.execute(dataSource);
    }
}
