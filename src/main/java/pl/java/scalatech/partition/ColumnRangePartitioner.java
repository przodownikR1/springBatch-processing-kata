package pl.java.scalatech.partition;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class ColumnRangePartitioner extends JdbcTemplate implements Partitioner {
    
    @Setter
    private String column;
    @Setter
    private String table;
    @Setter
    private int gridSize;

    @Override
    public Map<String, ExecutionContext> partition(int arg0) {
        int min = queryForInt("SELECT MIN(" + column + ") from " + table);
        int max = queryForInt("SELECT MAX(" + column + ") from " + table);
        int targetSize = (max - min) / gridSize;
        log.info("Our partition size will be {}", targetSize);
        log.info("gridSize {}  partitions", gridSize);

        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {

            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);

            log.info("minValue {} ", start);
            log.info("maxValue {} ", end);
            start += targetSize;
            end += targetSize;
            number++;
        }
        log.info("We are returning  {}  partitions", result.size());
        return result;
    }

 
}
