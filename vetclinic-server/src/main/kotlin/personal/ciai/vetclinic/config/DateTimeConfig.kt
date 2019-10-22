package personal.ciai.vetclinic.config

import java.time.format.DateTimeFormatter
import java.util.TimeZone
import javax.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService

@Configuration
class DateTimeConfig {

    @Bean
    fun conversionService(): FormattingConversionService {
        val conversionService = DefaultFormattingConversionService(false)

        val registrar = DateTimeFormatterRegistrar()
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm:ss"))
        registrar.registerFormatters(conversionService)

        return conversionService
    }

    @PostConstruct
    fun setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+1"))
    }
}
