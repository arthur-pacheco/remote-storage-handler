package net.hive.remote.storage.handler;

import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class RSHInputFormat implements org.apache.hadoop.mapred.InputFormat {

    /**
     *
     */
    final FileManager manager = new FileManager();

    @Autowired
    @Qualifier("num_splits")
    int num_splits;

    public InputSplit[] getSplits(JobConf jobConf, int i) throws IOException {
        final List<File> files = manager.getRequiredFiles();
        final int files_per_split = (files.size() / num_splits);

        InputSplit[] splits = new RSHInputSplit[num_splits];

        int index_files = 0;
        for(int j = 0; j<num_splits; j++){
            RSHInputSplit split = (RSHInputSplit) splits[j];

            if(j < num_splits-1) {
                for (int k = 0; k < files_per_split; k++) {
                    split.addFile(files.get(index_files++));
                }
            } else if (j == num_splits-1){
                while(index_files <= files.size()-1){
                    split.addFile(files.get(index_files++));
                }
            }
        }
        return splits;
    }

    public RecordReader getRecordReader(InputSplit inputSplit, JobConf jobConf, Reporter reporter) throws IOException {
        return new RSHRecordReader(inputSplit, jobConf, reporter);
    }
}
