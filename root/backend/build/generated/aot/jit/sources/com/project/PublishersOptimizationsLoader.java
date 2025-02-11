package com.project;

import io.micronaut.core.async.publisher.CompletableFuturePublisher;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.async.publisher.PublishersOptimizations;
import io.micronaut.core.optim.StaticOptimizations;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.lang.Override;
import java.util.Arrays;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PublishersOptimizationsLoader implements StaticOptimizations.Loader<PublishersOptimizations> {
  @Override
  public PublishersOptimizations load() {
    return new PublishersOptimizations(Arrays.asList(Observable.class, Flux.class, CompletableFuturePublisher.class, Publishers.JustPublisher.class, Publishers.JustThrowPublisher.class, Single.class, Mono.class, Maybe.class, Completable.class, io.micronaut.core.async.subscriber.Completable.class), Arrays.asList(CompletableFuturePublisher.class, Publishers.JustPublisher.class, Publishers.JustThrowPublisher.class, Single.class, Mono.class, Maybe.class), Arrays.asList(Completable.class, io.micronaut.core.async.subscriber.Completable.class));
  }
}
