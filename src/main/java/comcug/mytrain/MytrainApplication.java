package comcug.mytrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MytrainApplication {

    public static void main(String[] args) {  //这里要把module改成jdk8,不然报错
        SpringApplication.run(MytrainApplication.class, args);
    }

}
