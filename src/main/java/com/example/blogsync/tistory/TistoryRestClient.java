package com.example.blogsync.tistory;

import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TistoryRestClient {

	@Value("${tistory.rss-url}")
	private String rssUrl;

	public List<TistoryPost> fetchRecentPosts(int limit) {
		try {
			URL feedUrl = new URL(rssUrl);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			return feed.getEntries()
				.stream()
				.limit(5)
				.map(r -> new TistoryPost(
					HtmlUtils.htmlUnescape(r.getTitle()),
					r.getLink(),
					r.getPublishedDate()
						.toInstant()
						.atZone(ZoneId.of("Asia/Seoul"))
						.toLocalDateTime()
				))
				.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Tistory RSS 파싱 실패", e);
			return List.of();
		}
	}
}
