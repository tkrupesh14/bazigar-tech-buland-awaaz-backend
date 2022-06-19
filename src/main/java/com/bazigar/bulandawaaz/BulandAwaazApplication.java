package com.bazigar.bulandawaaz;

import com.bazigar.bulandawaaz.archiveAndSave.ArchiveRepository;
import com.bazigar.bulandawaaz.archiveAndSave.ArchiveService;
import com.bazigar.bulandawaaz.util.ArchiveRemover;
import com.bazigar.bulandawaaz.util.StoryDeleter;
import com.bazigar.bulandawaaz.twilio.TwilioConfig;
import com.bazigar.bulandawaaz.uploads.story.StoryRepository;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class BulandAwaazApplication {


	@Autowired
	private TwilioConfig twilioConfig;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private ArchiveService archiveService;

	@Autowired
	private ArchiveRepository archiveRepository;

	@PostConstruct
	public void initTwilio() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;



	@PostConstruct
	public void scheduleTask() {
		// This taskScheduler runs every hour
		taskScheduler.scheduleAtFixedRate(new StoryDeleter(storyRepository, archiveService), 3600000);
		// This taskScheduler runs every day
		taskScheduler.scheduleAtFixedRate(new ArchiveRemover(archiveRepository), 86400000);
	}

	public static void main(String[] args) throws IOException {

		SpringApplication. run(BulandAwaazApplication.class, args);
	}
}
