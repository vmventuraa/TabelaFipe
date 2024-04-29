package br.com.alura.TabelFipe;

import br.com.alura.TabelFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();

		principal.exibeMenu();
	}
}
