package com.example.blogsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlogsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsyncApplication.class, args);
	}

	// @Bean
	// CommandLineRunner run(TistoryService tistoryService, GitHubReadmeUpdater readmeUpdater) {
	// 	return args -> {
	// 		System.out.println("=== Tistory → GitHub 동기화 테스트 ===");
	//
	// 		List<TistoryPost> posts = tistoryService.getRecentPosts();
	// 		if (posts.isEmpty()) {
	// 			System.out.println("글을 가져오지 못했습니다.");
	// 			return;
	// 		}
	//
	// 		posts.forEach(post -> System.out.println("- " + post.getTitle()));
	//
	// 		readmeUpdater.update(posts);
	// 		System.out.println("=== README.md 업데이트 완료 ===");
	// 	};
	// }
}
