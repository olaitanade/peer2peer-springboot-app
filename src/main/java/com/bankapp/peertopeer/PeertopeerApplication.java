package com.bankapp.peertopeer;

import com.bankapp.peertopeer.repository.Peer2PeerApplicationRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PeertopeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeertopeerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(Peer2PeerApplicationRepo repository) {
		return (args) -> {
			repository.initialize();
		};
	}
}
