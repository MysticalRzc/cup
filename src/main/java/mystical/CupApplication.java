package mystical;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("mystical.cup.dao.mapper")
@SpringBootApplication
public class CupApplication {

	public static void main(String[] args) {
		SpringApplication.run(CupApplication.class, args);
	}
}