package remote.file.provider.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import remote.file.provider.manager.FileProviderManager;
import remote.file.provider.spring.cotroller.FileProviderController;

import java.nio.file.*;

@Configuration
@ComponentScan(basePackages = {"remote.file.provider"})
@PropertySource(value = {"file:/home/arthur/projects/remote-file-provider/src/main/resources/rfp.properties"})
public class SpringConfig {

    /**
     *
     */
    @Value("${rfh.root.dir}")
    private String rootDir;


    /**
     *
     * @return
     */
    @Bean("rootDir")
    public Path getRootPath(){
       return Paths.get(rootDir).toAbsolutePath().normalize();
    }

    @Bean("fileProviderManager")
    public FileProviderManager getFileProviderManager(){
        return new FileProviderManager();
    }




}
