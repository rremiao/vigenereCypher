package com.rremiao.vigenerecypher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rremiao.vigenerecypher.service.KasinskiService;
import com.rremiao.vigenerecypher.service.TriggerService;

@SpringBootApplication
public class VigenerecypherApplication implements CommandLineRunner {

	@Autowired
	TriggerService triggerService;

	@Autowired
	KasinskiService kasinskiService;
	
	public static void main(String[] args) {
		SpringApplication.run(VigenerecypherApplication.class, args);
	}

	@Override
	public void run(String ...args) throws Exception {
		String s = "esteposttemasenhaparaumproblemadaminhadisciplinadesegurançadesistemas";
		kasinskiService.kasiskiTest(s);
		// triggerService.baseApplication();
	}

}
