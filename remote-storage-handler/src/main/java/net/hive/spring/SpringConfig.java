package net.hive.spring;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"net.hive.remote"})
@PropertySource(value = { "file:/etc/hrsh/conf/hrsh.properties"})
public class SpringConfig {

    @Value("${num.splits}")
    private int num_splits;

    @Value("${rfp.connection.str}")
    private String rfpConnectionStr;


    @Bean("rfpConnectionStr")
    public String getRfpConnectionStr(){
        return rfpConnectionStr;
    }

    @Bean("num_splits")
    public int getSplits(){
        return num_splits;
    }



}
