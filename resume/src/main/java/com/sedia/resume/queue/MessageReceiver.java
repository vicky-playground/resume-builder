// package com.sedia.resume.queue;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.amqp.rabbit.annotation.RabbitHandler;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
// @Slf4j
// @RabbitListener(queues = "resume")
// public class MessageReceiver {
//
// @RabbitHandler
// public void receive(String in) {
// log.info(" [x] Received '{}'", in);
// }
//
// // @RabbitHandler
// // public void receive2(MessageSender.Man in) {
// // System.out.println(" [x] Received2 '" + in + "'");
// // }
// }