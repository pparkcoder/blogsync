package com.example.blogsync.schduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

	@Scheduled(fixedDelay = 1000)
	public void test() {
		System.out.println("스프링 스케줄러 적용 테스트");
	}
}
