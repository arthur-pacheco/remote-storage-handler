package net.hive.remote.storage.handler;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;


/**
 *
 */
public class RSHRecordReader implements RecordReader<LongWritable, MapWritable> {

    /**
     *
     */
    private long position;

    /**
     *
     */
    private RSHInputSplit inputSplit;

    /**
     *
     */
    private Iterator<File> files;

    /**
     *
     */
    private JobConf jobConf;

    /**
     *
     */
    private Reporter reporter;

    /**
     *
     * @param inputSplit
     * @param jobConf
     * @param reporter
     */
    public RSHRecordReader(InputSplit inputSplit, JobConf jobConf, Reporter reporter){
        this.inputSplit = (RSHInputSplit) inputSplit;
        this.files = ((RSHInputSplit) inputSplit).getFiles().iterator();
        this.jobConf = jobConf;
        this.reporter = reporter;

    }

    /**
     *
     * @return
     */
    private String[] getReadColumns(){
        return jobConf.get("hive.io.file.readcolumn.names").split(",");
    }


    /**
     *
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public boolean next(LongWritable key, MapWritable value) throws IOException {
        if(files.hasNext()){
            key.set(++position);
            File file = files.next();

            String[] columnKeys = getReadColumns();
            String[] columnValues = file.toString().split(",");

            for(int i=0; i<columnKeys.length; i++){
                final Writable columnKey = new Text(columnKeys[i]);
                final Writable columnValue = new Text(columnValues[i]);
                value.put(columnKey, columnValue);
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public LongWritable createKey() {
        return new LongWritable();
    }

    /**
     *
     * @return
     */
    @Override
    public MapWritable createValue() {
        return new MapWritable();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public long getPos() throws IOException {
        return position;
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {

    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public float getProgress() throws IOException {
        return 0;
    }
}
