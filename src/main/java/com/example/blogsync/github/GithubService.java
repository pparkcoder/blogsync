package com.example.blogsync.github;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GithubService {

	@Value("${github.token}")
	private String token;

	@Value("${github.name}")
	private String name;

	@Value("${github.repository}")
	private String repository;

	@Value("${github.branch}")
	private String branch;

	private static final String API_URL = "https://api.github.com";

	private RestClient restClient() {
		return RestClient.builder()
			.baseUrl(API_URL)
			.defaultHeader("Authorization", "Bearer " + token)
			.defaultHeader("Accept", "application/vnd.github+json")
			.defaultHeader("X-GitHub-Api-Version", "2022-11-28")
			.build();
	}

	private Map<String, Object> getReadmeInfo() {
		try {
			return restClient().get()
				.uri("/repos/{name}/{repository}/contents/README.md?ref={branch}", name, repository, branch)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {
				});
		} catch (Exception e) {
			log.error("README.md 읽기 실패", e);
			return Map.of();
		}
	}

	public String getCurrentReadme() {
		Map<String, Object> info = getReadmeInfo();
		if (info.isEmpty())
			return "";

		String encoded = (String)info.get("content");
		return new String(Base64.getMimeDecoder().decode(encoded));
	}

	public void updateReadme(String newContent) {
		try {
			Map<String, Object> info = getReadmeInfo();
			String sha = (String)info.get("sha");

			String encoded = Base64.getEncoder().encodeToString(newContent.getBytes());

			Map<String, Object> body = new HashMap<>();
			body.put("message", "블로그 최신 글 업데이트");
			body.put("content", encoded);
			body.put("sha", sha);
			body.put("branch", branch);

			restClient().put()
				.uri("/repos/{name}/{repository}/contents/README.md", name, repository)
				.body(body)
				.retrieve()
				.toBodilessEntity();

			log.info("README.md 업데이트 완료");

		} catch (Exception e) {
			log.error("README.md 업데이트 실패", e);
		}
	}
}