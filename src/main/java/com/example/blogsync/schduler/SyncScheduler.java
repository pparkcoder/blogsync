package com.example.blogsync.schduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.blogsync.github.GitHubReadmeUpdater;
import com.example.blogsync.tistory.TistoryPost;
import com.example.blogsync.tistory.TistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SyncScheduler {

	private final TistoryService tistoryService;
	private final GitHubReadmeUpdater gitHubReadmeUpdater;

	@Scheduled(cron = "${sync.cron}")
	public void sync() {
		log.info("===동기화시작===");
		List<TistoryPost> posts = tistoryService.getRecentPosts();
		if (posts.isEmpty()) {
			log.warn("가져온 글이 없습니다. 동기화 중단");
			return;
		}

		log.info("최신 글 {}개 감지됨", posts.size());
		gitHubReadmeUpdater.update(posts);
		log.info("===동기화 완료===");
	}
}
