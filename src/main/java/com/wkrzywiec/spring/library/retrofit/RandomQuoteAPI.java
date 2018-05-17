package com.wkrzywiec.spring.library.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomQuoteAPI {

	@GET("/api/quotes/random/")
	public Call<RandomQuoteResponse> getRandomQuote();
}
