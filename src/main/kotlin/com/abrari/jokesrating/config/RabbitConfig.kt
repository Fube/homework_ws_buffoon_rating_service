package com.abrari.jokesrating.config

import com.abrari.jokesrating.services.RatingService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class RabbitConfig {

    @Value("\${spring.rabbitmq.host}")
    private lateinit var HOST_NAME:String

    @Value("\${fube.rabbitmq.jokes.queue}")
    private lateinit var QUEUE_NAME:String

    @Value("\${fube.rabbitmq.jokes.exchange-name}")
    private lateinit var EXCHANGE_NAME:String

    @Value("\${fube.rabbitmq.jokes.jokes-topic}")
    private lateinit var TOPIC_NAME:String

    @Autowired
    private lateinit var ratingService: RatingService;

    @RabbitListener(queues = ["jokes"], ackMode = "AUTO")
    fun processMessage(message:ByteArray) {

        try{
            val msg = String(message, Charsets.UTF_8)
            val asUUID = UUID.fromString(msg)
            ratingService.deleteAllFor(asUUID)
            println("Delete all ratings for $msg")
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
    }

    @Bean
    fun getQueue(): Queue = Queue(QUEUE_NAME, false)

    @Bean
    fun getExchange(): TopicExchange = TopicExchange(EXCHANGE_NAME)

    @Bean
    fun getBinding(): Binding = BindingBuilder.bind(getQueue()).to(getExchange()).with(TOPIC_NAME)


}