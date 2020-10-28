package net.hive.remote.storage.handler;

import org.apache.hadoop.hive.ql.metadata.DefaultStorageHandler;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.OpenCSVSerde;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.OutputFormat;

public class RSH extends DefaultStorageHandler {

    /**
     *
     * @return
     */
    @Override
    public Class<? extends InputFormat> getInputFormatClass() {
        return RSHInputFormat.class;
    }

    /**
     *
     * @return
     */
    @Override
    public Class<? extends OutputFormat> getOutputFormatClass() {
        return RSHOutputFormat.class;
    }

    /**
     *
     * @return
     */
    @Override
    public Class<? extends AbstractSerDe> getSerDeClass() {
        return OpenCSVSerde.class;
    }


}

