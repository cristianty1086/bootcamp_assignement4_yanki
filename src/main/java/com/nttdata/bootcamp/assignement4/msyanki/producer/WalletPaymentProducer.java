package com.nttdata.bootcamp.assignement4.msyanki.producer;

import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletPaymentModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletPaymentProducer {

    private final KafkaTemplate<String, WalletPaymentModel> kafkaTemplate;

    @Value(value = "${kafka.topic.wallet.payment.name}")
    private String topic;

    public void sendMessage(WalletPaymentModel balanceModel) {

        ListenableFuture<SendResult<String, WalletPaymentModel>> future = kafkaTemplate.send(this.topic, balanceModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, WalletPaymentModel>>() {
            @Override
            public void onSuccess(SendResult<String, WalletPaymentModel> result) {
                log.info("Message {} has been sent ", balanceModel);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the walletPaymentModel {} ", balanceModel);
            }
        });
    }
}
