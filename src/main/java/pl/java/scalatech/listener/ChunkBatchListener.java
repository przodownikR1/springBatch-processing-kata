package pl.java.scalatech.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("listenerChunk")
public class ChunkBatchListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("+++ beforeChunk ....{}", context.getStepContext().getId());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("+++ afterChunk ....{}", context.getStepContext().getId());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.info("+++ afterErrorChunk ....{}", context.getStepContext().getId());
    }
}