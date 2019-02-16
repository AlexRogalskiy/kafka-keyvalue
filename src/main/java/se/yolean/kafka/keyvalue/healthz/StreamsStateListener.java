package se.yolean.kafka.keyvalue.healthz;

import org.apache.kafka.streams.KafkaStreams.State;
import org.apache.kafka.streams.KafkaStreams.StateListener;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Needed because transitions say more than calls to streams.state()
 */
public class StreamsStateListener implements StateListener {

  public final Logger logger = LogManager.getLogger(StreamsStateListener.class);

  private boolean hasBeenRunning = false;

  /**
   * @return true if streams seems to have been running at any time
   */
  public boolean streamsHasBeenRunning() {
    return hasBeenRunning;
  }

  @Override
  public void onChange(State newState, State oldState) {
    logger.info("Streams state change from {} to {}", oldState, newState);
    if (State.RUNNING.equals(newState) && State.REBALANCING.equals(oldState)) {
      hasBeenRunning = true;
    }
  }

}
