package com.secret.handle.impl;

import com.secret.enums.applet.RedisDelayQueueEnum;
import com.secret.handle.RedisDelayQueueHandle;
import com.secret.utils.RedisDelayQueueUtil;
import com.secret.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动延迟队列
 */
@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {
 
	@Resource
	private RedisDelayQueueUtil redisDelayQueueUtil;

   ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 50, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000), Executors.defaultThreadFactory());
	@Override
	public void run(String... args) {

		executorService.execute(() -> {
			while (true){
				try {
					RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
					for (RedisDelayQueueEnum queueEnum : queueEnums) {
						Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
						if (value != null) {

							RedisDelayQueueHandle redisDelayQueueHandle = SpringUtils.getBean(queueEnum.getBeanId());
							redisDelayQueueHandle.execute(value);
						}
					}
				} catch (InterruptedException e) {
					log.error("Redis delay queue is interrupted abnormally {}", e.getMessage());
				}
			}
		});
		log.info("Redis delay queue started successfully");
	}
}
