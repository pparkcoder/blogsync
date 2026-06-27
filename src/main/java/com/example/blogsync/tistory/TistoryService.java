package com.example.blogsync.tistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TistoryService {

	@Value("${sync.recent-post-count}")
	private int recentPostCount;
	private final TistoryRestClient tistoryRestClient;

	public List<TistoryPost> getRecentPosts() {
		return tistoryRestClient.fetchRecentPosts(recentPostCount);
	}

}
