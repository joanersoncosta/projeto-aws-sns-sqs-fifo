package com.example.demo.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.AwsConfigProperties;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.sns.SnsClient;

@RestController
@RequestMapping("/publica")
@RequiredArgsConstructor
@Log4j2
public class TesteSNSSQSAPI {
	private final SnsClient snsClient;
	private final AwsConfigProperties awsConfigProperties;

	@GetMapping("/msg/{msg}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void postPublicaMensagemMessage(@PathVariable(name = "msg") String mensagem) {
		log.info("[start] TesteSNSSQSAPI - postPublicaMensagem");
		enviaMensagem(mensagem);
		log.debug("[finish] TesteSNSSQSAPI - postPublicaMensagem");
	}

	private void enviaMensagem(String mensagem) {
		log.info("[start] TesteSNSSQSAPI - enviaMensagem");
		snsClient.publish(request -> request.topicArn(awsConfigProperties.getMessageTopic())
                .message(mensagem)
                .messageGroupId("test-message")
                .messageDeduplicationId(UUID.randomUUID().toString()));
		log.debug("[finish] TesteSNSSQSAPI - enviaMensagem");
	}

	@SqsListener("sqs-queue-ff.fifo")
	public void listenerMensagem(@Payload String mensagem) {
		log.info("FIFO mensagem recebida: " + mensagem);
	}
}