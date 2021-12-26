// package com.sedia.resume.config;
//
// import com.sedia.resume.queue.MessageReceiver;
// import com.sedia.resume.queue.MessageSender;
// import org.springframework.amqp.core.Queue;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class AmqpConfig {
//
// @Bean
// public Queue resumeQueue() {
// return new Queue("resume");
// }
//
// @Bean
// public MessageReceiver receiver() {
// return new MessageReceiver();
// }
//
// @Bean
// public MessageSender sender() {
// return new MessageSender();
// }
// }
