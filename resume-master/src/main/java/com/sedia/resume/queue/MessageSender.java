// package com.sedia.resume.queue;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.amqp.core.Queue;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
//
// @Slf4j
// public class MessageSender {
//
// @Autowired
// private RabbitTemplate template;
//
// @Autowired
// private Queue queue;
//
// public void send(String message) {
// template.convertAndSend(queue.getName(), message);
// log.info("將字串: [{}], 放入 queue", message);
// }
//
// // public void sendObj(){
// // Man a = new Man();
// // a.setAge(15);
// // a.setName("wade");
// // template.convertAndSend(queue.getName(), a);
// // System.out.println(" [x] Sent2 '" + a + "'");
// // }
// //
// // @Getter
// // @Setter
// // @ToString
// // static class Man implements Serializable {
// // String name;
// // Integer age;
// // }
// }