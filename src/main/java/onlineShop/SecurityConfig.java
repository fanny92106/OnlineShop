package onlineShop;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Environment env;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(env.getProperty("admin.email")).password(env.getProperty("admin.password")).authorities("ROLE_ADMIN");
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("Select emailId, password, enabled FROM users WHERE emailId = ?")
		.authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().formLogin().loginPage("/login").and()
		.authorizeRequests().antMatchers("/cart/**").hasAuthority("ROLE_USER")
		.antMatchers("/get*/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
		.antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")
		.anyRequest().permitAll()
		.and()
		.logout().logoutUrl("/logout");
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

}
