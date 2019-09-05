import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@EnableAspectJAutoProxy(proxyTargetClass=true)
@Configuration
class CommonConfig {
  @Bean
  fun messageSource(): MessageSource {
    val messageSource = ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages");
    messageSource.setDefaultEncoding("utf-8");
    return messageSource;
  }
}
