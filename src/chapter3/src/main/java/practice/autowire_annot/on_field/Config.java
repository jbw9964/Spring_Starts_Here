package practice.autowire_annot.on_field;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
// If specific packages are not defined, scanning will occur recursively
// beginning with the package of the class that declares this annotation.
public class Config {
}
