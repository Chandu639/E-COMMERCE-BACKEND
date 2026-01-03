import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("local")
@EnableJpaRepositories(basePackages = "com.ecommerce.repo")
@EntityScan(basePackages = "com.ecommerce.model")
public class JpaConfig {
}
