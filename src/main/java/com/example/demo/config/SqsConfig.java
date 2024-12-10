package com.example.demo.config;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Profile("!local")
public class SqsConfig {
    private final AwsConfigProperties awsSqsProperties;

    @Bean
    public QueueMessagingTemplate messagingTemplate() {
    	return new QueueMessagingTemplate(buildAmazonSQSAsync());
    }

	private AmazonSQSAsync buildAmazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder
				.standard()
				.withRegion(Regions.US_EAST_1)
				.withCredentials(
						new AWSStaticCredentialsProvider(
								new BasicAWSCredentials(awsSqsProperties.getAccesskey(), awsSqsProperties.getSecretkey())))
				.build();
				
	}
}