package com.example.blogsync.github;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.blogsync.tistory.TistoryPost;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubReadmeUpdater {

	private static final String START_MARKER = "<!-- BLOG-POST-LIST:START -->";
	private static final String END_MARKER = "<!-- BLOG-POST-LIST:END -->";
	private final GithubService githubService;

	public void update(List<TistoryPost> posts) {
		String currentReadme = githubService.getCurrentReadme();
		String updatedReadme = replaceBlogSection(currentReadme, posts);

		if (currentReadme.equals(updatedReadme)) {
			return; // 변경사항 없는 경우 업데이트 미진행
		}

		githubService.updateReadme(updatedReadme);
	}

	private String replaceBlogSection(String readme, List<TistoryPost> posts) {
		String newSection = buildBlogSection(posts);

		if (readme.contains(START_MARKER) && readme.contains(END_MARKER)) {
			int startIndex = readme.indexOf(START_MARKER) + START_MARKER.length();
			int endIndex = readme.indexOf(END_MARKER);
			return readme.substring(0, startIndex) + "\n" + newSection + readme.substring(endIndex);
		}

		return readme + "\n\n" + START_MARKER + "\n" + newSection + END_MARKER + "\n";
	}

	private String buildBlogSection(List<TistoryPost> posts) {
		StringBuilder sb = new StringBuilder();
		posts.forEach(post ->
			sb.append(String.format("- [%s](%s)\n",
				post.getTitle(),
				post.getLink()))
		);
		return sb.toString();
	}
}
