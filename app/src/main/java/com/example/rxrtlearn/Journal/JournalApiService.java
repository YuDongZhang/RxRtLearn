package com.example.rxrtlearn.Journal;

import com.example.rxrtlearn.bean.Journalism;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pateo on 18-9-5.
 */

public interface JournalApiService {
    @GET("journalismApi")
    Observable<Journalism> getJournalism();
}
