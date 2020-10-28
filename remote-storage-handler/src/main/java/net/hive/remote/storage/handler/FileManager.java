package net.hive.remote.storage.handler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    @Autowired
    @Qualifier("rfpConnectionStr")
    private String rfpConnectionStr;

    /**
     *
     * @return
     */
    private List<String> getFilesList(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(rfpConnectionStr+"/fileProvider/get/filesList");
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                byte[] data = new byte[1024*1024*1024];
                entity.getContent().read(data);
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                List<String> filesList = (List) is.readObject();
                return filesList;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<File> getRequiredFiles(){
        List<File> files = new ArrayList<>();
        for(String path : getFilesList()){
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(rfpConnectionStr+"/fileProvider/get/"+path);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    byte[] data = new byte[1024*1024*1024];
                    entity.getContent().read(data);
                    File file = new File(path, data);
                    files.add(file);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }
}
