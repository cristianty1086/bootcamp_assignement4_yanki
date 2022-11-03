package com.nttdata.bootcamp.assignement4.msyanki.producer;

import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class WalletProducer {

    private final KafkaTemplate<String, WalletModel> kafkaTemplate;

    @Value(value = "${kafka.topic.balance.name}")
    private String topic;

    public void sendMessage(WalletModel balanceModel) {

        ListenableFuture<SendResult<String, WalletModel>> future = kafkaTemplate.send(this.topic, balanceModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, WalletModel>>() {
            @Override
            public void onSuccess(SendResult<String, WalletModel> result) {
                log.info("Message {} has been sent ", balanceModel);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the balanceModel {} ", balanceModel);
            }
        });
    }
}
