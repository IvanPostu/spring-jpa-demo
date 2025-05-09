package com.iv.demo2.kafka.stream.stream.feedback;

import com.iv.kafkademo2common.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Configuration
public class FeedbackSixStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    private Predicate<String, String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    private Predicate<String, String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var sourceStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        sourceStream.flatMap(splitWords()).split().branch(isGoodWord(), Branched.withConsumer(ks -> {
            ks.repartition(Repartitioned.as("t-commodity-feedback-six-good")).groupByKey().count().toStream()
                    .to("t-commodity-feedback-six-good-count");
            ks.groupBy((key, value) -> value).count().toStream().to("t-commodity-feedback-six-good-count-word");
        })).branch(isBadWord(), Branched.withConsumer(ks -> {
            ks.repartition(Repartitioned.as("t-commodity-feedback-six-bad")).groupByKey().count().toStream()
                    .to("t-commodity-feedback-six-bad-count");
            ks.groupBy((key, value) -> value).count().toStream().to("t-commodity-feedback-six-bad-count-word");
        }));

        return sourceStream;
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getLocation(), word)).collect(Collectors.toList());
    }

}

