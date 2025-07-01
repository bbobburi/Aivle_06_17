package labcqrssummarize.config;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.annotation.PostConstruct;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @PostConstruct
    public void init() {
        System.out.println("✅ SecurityConfig 적용됨");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()   // 테스트 편의상 비활성화
            .authorizeRequests()
            .anyRequest().permitAll(); // 모든 요청 허용
    }
}
 
 