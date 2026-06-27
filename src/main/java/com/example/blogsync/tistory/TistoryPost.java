package com.example.blogsync.tistory;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TistoryPost {
	private String title;
	private String link;
	private LocalDateTime publishedAt;
}
