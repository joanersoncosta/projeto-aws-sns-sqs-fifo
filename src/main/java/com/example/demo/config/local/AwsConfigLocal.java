package com.example.demo.config.local;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.demo.config.AwsConfigProperties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class AwsConfigLocal {
    private final AwsConfigProperties awsSqsProperties;

    @Bean(name = "snsClientLocal")
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.US_EAST_1) 
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")))
                .build();
    }
    
    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(awsSqsProperties.getEndereco()))
                .region(Region.of(awsSqsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsSqsProperties.getAccesskey(), awsSqsProperties.getSecretkey())))
                .build();
    }
}