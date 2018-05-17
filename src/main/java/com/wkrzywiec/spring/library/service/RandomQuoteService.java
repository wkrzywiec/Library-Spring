package com.wkrzywiec.spring.library.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.retrofit.RandomQuoteAPI;
import com.wkrzywiec.spring.library.retrofit.RandomQuoteResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RandomQuoteService {

	public RandomQuoteResponse getRandomResponse() {
		
		RandomQuoteResponse randomQuote = null;
		
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		Retrofit retrofit = new Retrofit.Builder()
		  .baseUrl("https://talaikis.com/")
		  .addConverterFactory(GsonConverterFactory.create())
		  .client(httpClient.build())
		  .build();
		
		RandomQuoteAPI randomQuoteAPI = retrofit.create(RandomQuoteAPI.class);
		Call<RandomQuoteResponse> callSync = randomQuoteAPI.getRandomQuote();
		
		try {
			Response<RandomQuoteResponse> response = callSync.execute();
			randomQuote = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return randomQuote;
	}
	
}
