package com.example.blogsync.github;

import java.io.IOException;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
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

	public String getCurrentReadme() {
		try {
			GitHub gitHub = new GitHubBuilder()
				.withOAuthToken(token)
				.build();

			GHRepository gitHubRepository = gitHub.getUser(name).getRepository(repository);
			return new String(gitHubRepository.getFileContent("README.md", branch)
				.read()
				.readAllBytes());
		} catch (Exception e) {
			log.error("README.md 읽기 실패", e);
			return "";
		}
	}

	public void updateReadme(String newContent) {
		try {
			GitHub gitHub = new GitHubBuilder()
				.withOAuthToken(token)
				.build();

			GHRepository gitHubRepository = gitHub.getUser(name).getRepository(repository);
			GHContent content = gitHubRepository.getFileContent("README.md", branch);
			content.update(
				newContent,
				"블로그 최신 글 업데이트 [skip ci]",
				branch
			);
		} catch (IOException e) {
			log.error("README.md 업데이트 완료");
		}
	}

}
