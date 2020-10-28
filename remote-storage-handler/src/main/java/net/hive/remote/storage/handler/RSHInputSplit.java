package net.hive.remote.storage.handler;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobConf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RSHInputSplit extends FileSplit{

    /**
     *
     */
    JobConf conf;

    /**
     *
     */
    int split_id;

    /**
     *
     */
    List<File> files = new ArrayList<>();

    /**
     *
     */
    long files_size = 0;

    /**
     *
     * @param split_id
     * @param conf
     */
    public RSHInputSplit(int split_id, JobConf conf) {
        super(FileInputFormat.getInputPaths(conf)[0], 0, 0, (String[]) null);
        this.conf = conf;
        this.split_id = split_id;
    }

    /**
     *
     * @return
     */
    @Override
    public long getLength() {
        return files_size;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public String[] getLocations() throws IOException {
        return null;
    }

    /**
     *
     * @return
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     *
     *
     */
    public void addFile(File f){
        files.add(f);
        files_size += f.getSize();
    }

    /**
     *
     */
    public void addAll(List<File> list){
        for(File f: list){
            addFile(f);
        }
    }

    /**
     *
     */
    @Override
    public void readFields(DataInput in) throws IOException {

        super.readFields(in);

        int split_id = in.readInt();
        int counter = in.readInt();
        int size = in.readInt();

        final byte[] bytes = new byte[size];

        in.readFully(bytes);

        List<File> files = new ArrayList<>();
        String[] tmp = (new String(bytes)).split(";");
        for(String s : tmp) {
            File f = new File("", s.getBytes());
            files.add(f);
        }

        files.clear();
        files.addAll(files);

        this.split_id = split_id;
    }

    /**
     *
     */
    @Override
    public void write(DataOutput out) throws IOException {

        super.write(out);

        StringBuffer sb = new StringBuffer();

        for (File f : files) {
            if (sb.length() > 0) {
                sb.append(";");
            }
            sb.append(f.toString());
        }

        byte[] bytes = sb.toString().getBytes();
        int size = bytes.length;
        int counter = files.size();

        out.writeInt(split_id);
        out.writeInt(counter);
        out.writeInt(size);
        out.write(bytes);
    }
}
