/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.demo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        // -javaagent:/Users/format/Develop/edas-workspace/arms-java-agent/agent/target/arms-agent-1.7.0-SNAPSHOT/arms-bootstrap-1.7.0-SNAPSHOT.jar -Darms.licenseKey=deydyr0nqp@c10367b58c63e4d -Darms.appName=zuul-provider -Dprofiler.micro.service.canary.enable=true -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
        SpringApplication.run(ZuulApplication.class, args);
    }

}
