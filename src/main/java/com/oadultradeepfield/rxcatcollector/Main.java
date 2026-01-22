package com.oadultradeepfield.rxcatcollector;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CatAsAService api = new CatAsAService();

        // A subject to handle user input
        PublishSubject<String> inputSubject = PublishSubject.create();

        // A subject to hold the last downloaded file name, we want the last emit item because if the observer subscribe
        // after the item emit, we want to show the last cat image file name.
        BehaviorSubject<String> historySubject = BehaviorSubject.createDefault("None");

        System.out.println("--- CAT DOWNLOADER 3000 ---");
        System.out.println("Type a tag (e.g. 'orange', 'cute', 'gif' to download a random cat image");
        System.out.println("Type 'exit' to exit the program");

        Disposable inputDisposable = inputSubject
                .filter(text -> text.length() > 2)
                .flatMap(tag -> api.getRandomCatPayload(tag)
                        .subscribeOn(Schedulers.io())
                        .onErrorResumeNext(e -> {
                            if (e instanceof CatNotFoundException) {
                                System.out.println(e.getMessage());
                            } else {
                                System.out.println("Unknown error: " + e.getMessage());
                            }
                            return Observable.empty();
                        }))
                .filter(catPayload -> catPayload.id != null)
                // We use switchMap to cancel the previous request if a new request comes in.
                .switchMap(catPayload -> api.downloadCatImage(catPayload.id)
                        .subscribeOn(Schedulers.io())
                        .map(bytes -> new CatResult(catPayload, bytes))
                        .onErrorResumeNext(e -> {
                            if (e instanceof CatNotFoundException) {
                                System.out.println(e.getMessage());
                            } else {
                                System.out.println("Unknown error: " + e.getMessage());
                            }
                            return Observable.empty();
                        }))
                .observeOn(Schedulers.computation())
                .map(result -> FileSaver.saveImage(result.catPayload.id, result.imageBytes))
                .doOnNext(historySubject::onNext)
                .observeOn(Schedulers.newThread())
                .subscribe(
                        fileName -> System.out.println(">> SUCCESS! Ready for next tag."),
                        Throwable::printStackTrace
                );

        Disposable historyDisposable = historySubject
                .subscribe(lastFile -> System.out.println("[History Log] Current last file is: " + lastFile));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            inputSubject.onNext(input);
        }

        // Dispose the disposables to avoid memory leaks.
        inputDisposable.dispose();
        historyDisposable.dispose();
    }

    /**
     * A data class to hold the result of a cat download operation.
     */
    static class CatResult {
        CatPayload catPayload;
        byte[] imageBytes;

        public CatResult(CatPayload c, byte[] b) {
            this.catPayload = c;
            this.imageBytes = b;
        }
    }
}
