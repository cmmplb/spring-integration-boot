package io.github.cmmplb.kafka.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.kafka.service.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-05-26 16:04:02
 * @since jdk 1.8
 */

@Schema(name = "kafka演示")
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private ProducerService producerService;

    @Operation(summary = "简单消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/send")
    public void send2SimpleMessage() {
        String message = "发送简单消息";
        System.out.println(message);
        producerService.send2SimpleMessage(message);
    }

    @Operation(summary = "复杂消息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/map")
    public void send2EntityMessage() {
        String message = "复杂消息";
        System.out.println(message);
        producerService.send2ComplexMessage(message);
    }


}
