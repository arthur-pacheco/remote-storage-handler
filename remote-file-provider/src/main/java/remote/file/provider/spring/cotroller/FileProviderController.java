package remote.file.provider.spring.cotroller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import remote.file.provider.manager.FileProviderManager;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/fileProvider")
public class FileProviderController {

    /**
     *
     */
    private Logger LOGGER = Logger.getLogger(FileProviderController.class.getName());

    /**
     *
     */
    @Autowired
    @Qualifier("fileProviderManager")
    private FileProviderManager fileProviderManager;

    /**
     *
     * @param fileName
     * @param request
     * @return
     */
    @GetMapping("/get/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = null;
        try {
            resource = fileProviderManager.loadFileAsResource(fileName);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOGGER.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    /**
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/get/filesList")
    public ResponseEntity<Resource> getFilesList() throws IOException {
        List<String> filesList = new ArrayList<>();
        for (final File fileEntry : fileProviderManager.getRootDir().listFiles()) {
                filesList.add(fileEntry.getAbsolutePath());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(filesList);
        oos.flush();
        Resource resource = new ByteArrayResource(bos.toByteArray());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

    }

}

