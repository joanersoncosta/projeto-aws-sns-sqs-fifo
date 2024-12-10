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
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class SqsConfigLocal {
    private final AwsConfigProperties awsSqsProperties;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create("http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/test-queue.fifo"))
                .region(Region.of(awsSqsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsSqsProperties.getAccesskey(), awsSqsProperties.getSecretkey())))
                .build();
    }
}