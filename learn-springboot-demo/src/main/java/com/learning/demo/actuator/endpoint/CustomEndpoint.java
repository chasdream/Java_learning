package com.learning.demo.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  自定义端点
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/3/8
 */
@Endpoint(id = "customEndpoint")
@Component
public class CustomEndpoint {

    private String name = "default";

    @ReadOperation
    public String getName() {
        return "{\"name\":\""+ name +"\"}";
    }

    @DeleteOperation
    public String delName() {
        name = "";
        return "{\"name\":\""+ name +"\"}";
    }

    @WriteOperation
    public String writeName() {
        name = "writeName";
        return "{\"name\":\""+ name +"\"}";
    }
}
