package com.roamer.school.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA配置
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 13:58
 */
@Configuration
@EntityScan(basePackages = "com.roamer.school.**.entity")
@EnableJpaRepositories(basePackages = "com.roamer.school.**.dao")
public class JpaConfiguration {
}
