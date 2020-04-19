package com.feiniaojin.earlyhome.generator.basic;

import com.feiniaojin.earlyhome.generator.basic.core.CoreGenerator;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BasicGeneratorApplication implements CommandLineRunner {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(BasicGeneratorApplication.class);
  }

  @Resource
  private CoreGenerator coreGenerator;

  @Override
  public void run(String... args) throws Exception {
      coreGenerator.generate();
  }
}
