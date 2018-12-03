package com.tdmh.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Liuxia on 2018/11/30.
 */
@Component
@Data
public class YmlProtites {
    @Value("${lyims.serverIp}")
    private String  lyimsServerIp;

    @Value("${lyims.serverPort}")
    private String  lyimsServerPort;
}
